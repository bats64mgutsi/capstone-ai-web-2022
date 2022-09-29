package backend.controllers;

import backend.ApplicationModels.GoogleScholarAuthorProfile;
import backend.ApplicationModels.GoogleScholarPublication;
import backend.ApplicationModels.NrfAuthor;
import backend.ApplicationModels.Stats;
import backend.DatabaseModels.*;
import backend.Locator;
import backend.Tables.*;
import backend.services.GoogleScholarService;
import backend.services.HashingService;
import backend.services.UniqueIdService;
import backend.utils.FilteringUtils;
import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;

import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class NrfListController {
    final Logger logger = Logger.getLogger(NrfListController.class.getName());
    final HashingService hashingService = (HashingService) Locator.instance.get(HashingService.class);
    final GoogleScholarService googleScholarService = (GoogleScholarService) Locator.instance.get(GoogleScholarService.class);
    final UniqueIdService uniqueIdService = (UniqueIdService) Locator.instance.get(UniqueIdService.class);
    final AllAuthorsTable allAuthorsTable = (AllAuthorsTable) Locator.instance.get(AllAuthorsTable.class);
    final AuthorsTable authorsTable = (AuthorsTable) Locator.instance.get(AuthorsTable.class);
    final AuthorToSubfieldTable authorToSubfieldTable = (AuthorToSubfieldTable) Locator.instance.get(AuthorToSubfieldTable.class);
    final ContributionsTable contributionsTable = (ContributionsTable) Locator.instance.get(ContributionsTable.class);
    final PublicationsTable publicationsTable = (PublicationsTable) Locator.instance.get(PublicationsTable.class);
    final SubfieldsTable subfieldsTable = (SubfieldsTable) Locator.instance.get(SubfieldsTable.class);
    final InstitutionsTable institutionsTable = (InstitutionsTable) Locator.instance.get(InstitutionsTable.class);
    final AiKeywordsTable aiKeywordsTable = (AiKeywordsTable) Locator.instance.get(AiKeywordsTable.class);
    final KeyValuesTable keyValuesTable = (KeyValuesTable) Locator.instance.get(KeyValuesTable.class);
    final PopulatedSubfieldsController populatedSubfieldsController = (PopulatedSubfieldsController) Locator.instance.get(PopulatedSubfieldsController.class);

    public void setAuthors(List<NrfAuthor> authors, String year) throws SQLException {
        final List<String> aiKeywords = aiKeywordsTable.listAll();
        // Filter authors
        authors = authors.stream().filter(el -> {
            final List<String> subfieldsStr = new ArrayList<>();
            try {
                subfieldsStr.addAll(el.primaryResearchFields);
            } catch(Exception ignored) {}

            try {
                subfieldsStr.addAll(el.secondaryResearchFields);
            } catch (Exception ignored) {}

            try {
                subfieldsStr.addAll(el.specialisations);
            } catch(Exception ignored) {}

            final List<Subfield> subfields = subfieldsStr.stream().map(sEl -> new Subfield(sEl, sEl)).toList();
            return FilteringUtils.hasIntersectionIgnoreCase(subfields, aiKeywords);

        }).toList();

        logger.info(String.format("Loading %d authors", authors.size()));

        clearTables(year);
        final Set<Subfield> allSubFields = new HashSet<>();
        final List<GoogleScholarAuthorProfile> authorProfiles = googleScholarService.fetchProfiles(authors);

        for (int iii = 0; iii < authorProfiles.size(); iii++) {
            final NrfAuthor nrfAuthor = authors.get(iii);
            final GoogleScholarAuthorProfile authorProfile = authorProfiles.get(iii);

            final String authorId = makeAuthorId(nrfAuthor, year);
            insertAuthor(nrfAuthor, authorId, year);
            insertAuthorPublications(authorProfile.publications, authorId);
            insertAuthorSubfields(nrfAuthor, authorProfile, authorId, allSubFields, year);

            logger.log(Level.INFO, String.format("Flushed author %s %s", nrfAuthor.initials, nrfAuthor.surname));
        }

        logger.log(Level.INFO, "Filtering authors...");
        insertAllSubfields(allSubFields);
        filterAndMoveAuthors(Integer.parseInt(year));
        logger.log(Level.INFO, "System data successfully updated!");
    }

    /***
     * Moves authors from AllAuthorsTable table to the Authors table for given year.
     */
    public void filterAndMoveAuthors(int year) throws SQLException {
        authorsTable.clearAll();
        final List<Author> allAuthors = Stream.concat(allAuthorsTable.listAll().stream(), allAuthorsTable.listAllPrevYear().stream()).toList();
        final List<String> allInstitutionIds = institutionsTable.listAll().stream().map(el-> el.id).toList();
        final List<String> aiKeywords = aiKeywordsTable.listAll();

        // Now filter authors by subfields
        for(Author author: allAuthors) {
            final boolean isInPublicInstitution = allInstitutionIds.contains(author.institution);
            if(!isInPublicInstitution) continue;

            final List<AuthorToSubfield> authorToSubfields = authorToSubfieldTable.getAuthorSubfields(author.id);
            final List<Subfield> subfields = new LinkedList<>();
            for (final AuthorToSubfield authorToSubfield : authorToSubfields) {
                final Subfield subfield = subfieldsTable.getSubfield(authorToSubfield.subfieldId);
                subfields.add(subfield);
            }

            final boolean isInAi = FilteringUtils.hasIntersectionIgnoreCase(subfields, aiKeywords);
            if(!isInAi) continue;

            authorsTable.insertAuthor(author);
        }

        // Compute and save stats
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        if(year == currentYear) {
            // Compute stats only if we are uploading Nrf list for the current year.
            // The assumption is that data for previous year already exists so that stats
            // can be computed.
            final Stats stats = computeStats();
            final String statsJson = new Gson().toJson(stats);
            keyValuesTable.store(Stats.class.getName(), statsJson);
        }
    }

    private String makeAuthorId(NrfAuthor author, String year) {
        return hashingService.flatten(new ImmutableList.Builder<String>().add(author.initials).add(author.surname).add(year).build());
    }

    private String makeSubfieldId(String subFieldStr, String year) {
        return hashingService.flatten(new ImmutableList.Builder<String>().add(subFieldStr).add(year).build());
    }


    private void clearTables(String year) throws SQLException {
        authorToSubfieldTable.clearAllForYear(year);
        publicationsTable.clearAll();
        contributionsTable.clearAll();
    }

    private void insertAuthor(NrfAuthor nrfAuthor, String authorId, String year) throws SQLException {
        final String institutionId = hashingService.flatten(new ImmutableList.Builder<String>().add(nrfAuthor.institution).build());
        final Author author = new Author(authorId, nrfAuthor.surname, nrfAuthor.initials, nrfAuthor.title, institutionId, nrfAuthor.rating, year);

        allAuthorsTable.insertAuthor(author);
    }

    private void insertAuthorPublications(List<GoogleScholarPublication> googleScholarPublications, String authorId) throws SQLException {
        for (final GoogleScholarPublication googleScholarPublication : googleScholarPublications) {
            final String publicationId = uniqueIdService.uuidv4();
            final Publication publication = googleScholarPublication.publication;
            publication.id = publicationId;
            publicationsTable.insertPublication(publication);

            insertMainContribution(publicationId, authorId);
        }
    }

    private void insertMainContribution(String publicationId, String authorId) throws SQLException {
        final Contribution contribution = new Contribution(publicationId, authorId, Contribution.ContributionType.MainAuthor);
        contributionsTable.setContribution(contribution);
    }

    private void insertAuthorSubfields(NrfAuthor author, GoogleScholarAuthorProfile profile, String authorId, Set<Subfield> allSubfields, String year) throws SQLException {
        final List<String> authorSubfields = new LinkedList<>();
        authorSubfields.addAll(author.primaryResearchFields);
        authorSubfields.addAll(author.secondaryResearchFields);
        authorSubfields.addAll(author.specialisations);
        authorSubfields.addAll(profile.subFields);

        for (String subFieldStr : authorSubfields) {
            final String subFieldId = makeSubfieldId(subFieldStr, year);
            final Subfield subField = new Subfield(subFieldId, subFieldStr);
            allSubfields.add(subField);

            // Insert subfield for author
            final AuthorToSubfield authorToSubfield = new AuthorToSubfield(authorId, subFieldId);
            authorToSubfieldTable.insertAuthorToSubfield(authorToSubfield);
        }
    }

    private void insertAllSubfields(Set<Subfield> allSubfields) throws SQLException {
        for (Subfield subfield : allSubfields) {
            subfieldsTable.insertSubfield(subfield);
        }
    }

    public Stats computeStats() throws SQLException {
        final List<Author> currentYearAuthors = authorsTable.listAll();
        final List<Author> prevYearAuthors = authorsTable.listAllPrevYear();

        final List<Publication> allPublications = listPublicationsForAuthors(currentYearAuthors);
        allPublications.addAll(listPublicationsForAuthors(prevYearAuthors));

        final int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        return new Stats(
                currentYearAuthors.size(),
                prevYearAuthors.size(),
                countPublicationsUpTo(allPublications, currentYear),
                countPublicationsUpTo(allPublications, currentYear-1),

                tallyCitationsUpTo(allPublications, currentYear),
                tallyCitationsUpTo(allPublications, currentYear-1),

                countAuthorsWithRating(currentYearAuthors, "A"),
                countAuthorsWithRating(currentYearAuthors, "B"),
                countAuthorsWithRating(currentYearAuthors, "C"),
                countAuthorsWithRating(currentYearAuthors, "P"),
                countAuthorsWithRating(currentYearAuthors, "Y"),

                countAuthorsWithRating(prevYearAuthors, "A"),
                countAuthorsWithRating(prevYearAuthors, "B"),
                countAuthorsWithRating(prevYearAuthors, "C"),
                countAuthorsWithRating(prevYearAuthors, "P"),
                countAuthorsWithRating(prevYearAuthors, "Y"),
                populatedSubfieldsController.listSubfields()
        );
    }

    private List<Publication> listPublicationsForAuthors(List<Author> authors) throws SQLException {
        final List<Contribution> contributions = new LinkedList<>();
        for(final Author author: authors) {
            contributions.addAll(contributionsTable.listForAuthor(author.id));
        }

        final List<Publication> publications = new LinkedList<>();
        for(final Contribution contribution: contributions) {
            publications.add(publicationsTable.getItemWithId(contribution.publicationId));
        }

        return publications;
    }

    private int countAuthorsWithRating(List<Author> authors, String withRating) {
        return authors.stream().filter(el -> el.rating.equals(withRating)).toList().size();
    }

    private int countPublicationsUpTo(List<Publication> publications, int upToYear) {
        return publications.stream().map(el -> {
            try{
                return Integer.parseInt(el.year);
            } catch(NumberFormatException e) {
                return 0;
            }
        }).filter(yearEl -> yearEl <= upToYear).toList().size();
    }

    private int tallyCitationsUpTo(List<Publication> publications, int upToYear) {
        return publications.stream().filter(el -> {
            try {
                int year = Integer.parseInt(el.year);
                return year <= upToYear;
            } catch (NumberFormatException e) {
                return true;
            }
        }).map(el -> el.citationCount).reduce(0, Integer::sum);
    }
}
