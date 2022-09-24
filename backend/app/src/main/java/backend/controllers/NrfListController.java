package backend.controllers;

import backend.ApplicationModels.GoogleScholarAuthorProfile;
import backend.ApplicationModels.GoogleScholarPublication;
import backend.ApplicationModels.NrfAuthor;
import backend.DatabaseModels.*;
import backend.Locator;
import backend.Tables.*;
import backend.services.GoogleScholarService;
import backend.services.HashingService;
import backend.services.UniqueIdService;
import backend.utils.FilteringUtils;
import com.google.common.collect.ImmutableList;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    public void setAuthors(List<NrfAuthor> authors, String year) throws SQLException {
        clearTables();
        final Set<Subfield> allSubFields = new HashSet<>();
        final List<GoogleScholarAuthorProfile> authorProfiles = googleScholarService.fetchProfiles(authors);

        for (int iii = 0; iii < authorProfiles.size(); iii++) {
            final NrfAuthor nrfAuthor = authors.get(iii);
            final GoogleScholarAuthorProfile authorProfile = authorProfiles.get(iii);

            final String authorId = makeAuthorId(nrfAuthor, year);
            insertAuthor(nrfAuthor, authorId, year);
            insertAuthorPublications(authorProfile.publications, authorId);
            insertAuthorSubfields(nrfAuthor, authorProfile, authorId, allSubFields);

            logger.log(Level.INFO, String.format("Flushed author %s %s", nrfAuthor.initials, nrfAuthor.surname));
        }

        logger.log(Level.INFO, "Filtering authors...");
        insertAllSubfields(allSubFields);
        filterAndMoveAuthors();
        logger.log(Level.INFO, "System data successfully updated!");
    }

    /***
     * Moves authors from AllAuthorsTable table to the Authors table.
     */
    public void filterAndMoveAuthors() throws SQLException {
        authorsTable.clearAll();
        final List<Author> allAuthors = allAuthorsTable.listAll();
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
    }

    private String makeAuthorId(NrfAuthor author, String year) {
        return hashingService.flatten(new ImmutableList.Builder<String>().add(author.initials).add(author.surname).add(year).build());
    }


    private void clearTables() throws SQLException {
        authorToSubfieldTable.clearAll();
        publicationsTable.clearAll();
        contributionsTable.clearAll();
        subfieldsTable.clearAll();
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

    private void insertAuthorSubfields(NrfAuthor author, GoogleScholarAuthorProfile profile, String authorId, Set<Subfield> allSubfields) throws SQLException {
        final List<String> authorSubfields = new LinkedList<>();
        authorSubfields.addAll(author.primaryResearchFields);
        authorSubfields.addAll(author.secondaryResearchFields);
        authorSubfields.addAll(author.specialisations);
        authorSubfields.addAll(profile.subFields);

        for (String subFieldStr : authorSubfields) {
            final String subFieldId = hashingService.flatten(new ImmutableList.Builder<String>().add(subFieldStr).build());
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
}
