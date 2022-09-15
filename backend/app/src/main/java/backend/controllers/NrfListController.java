package backend.controllers;

import backend.ApplicationModels.GoogleScholarPublication;
import backend.ApplicationModels.NrfAuthor;
import backend.DatabaseModels.*;
import backend.Locator;
import backend.Tables.*;
import backend.services.GoogleScholarService;
import backend.services.HashingService;
import backend.services.UniqueIdService;
import com.google.common.collect.ImmutableList;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class NrfListController {
    final HashingService hashingService = (HashingService) Locator.instance.get(HashingService.class);
    final GoogleScholarService googleScholarService = (GoogleScholarService) Locator.instance.get(GoogleScholarService.class);
    final UniqueIdService uniqueIdService = (UniqueIdService) Locator.instance.get(UniqueIdService.class);
    final AuthorsTable authorsTable = (AuthorsTable) Locator.instance.get(AuthorsTable.class);
    final AuthorToSubfieldTable authorToSubfieldTable = (AuthorToSubfieldTable) Locator.instance.get(AuthorToSubfieldTable.class);
    final ContributionsTable contributionsTable = (ContributionsTable) Locator.instance.get(ContributionsTable.class);
    final PublicationsTable publicationsTable = (PublicationsTable) Locator.instance.get(PublicationsTable.class);
    final SubfieldsTable subfieldsTable = (SubfieldsTable) Locator.instance.get(SubfieldsTable.class);

    public void setAuthors(List<NrfAuthor> authors) throws SQLException {
        clearTables();
        final Set<Subfield> allSubFields = new HashSet<>();

        for(NrfAuthor nrfAuthor: authors) {
            final String authorId = hashingService.flatten(new ImmutableList.Builder<String>().add(nrfAuthor.initials).add(nrfAuthor.surname).build());
            insertAuthor(nrfAuthor, authorId);
            insertAuthorPublications(nrfAuthor, authorId);
            insertAuthorSubfields(nrfAuthor, authorId, allSubFields);
        }

        insertAllSubfields(allSubFields);
    }

    private void clearTables() throws SQLException {
        authorToSubfieldTable.clearAll();
        authorsTable.clearAll();
        publicationsTable.clearAll();
        contributionsTable.clearAll();
        subfieldsTable.clearAll();
    }

    private void insertAuthor(NrfAuthor nrfAuthor, String authorId) throws SQLException {
        final String institutionId = hashingService.flatten(new ImmutableList.Builder().add(nrfAuthor.institution).build());
        final Author author = new Author(
                authorId,
                nrfAuthor.surname,
                nrfAuthor.initials,
                nrfAuthor.title,
                institutionId,
                nrfAuthor.rating
        );

        authorsTable.insertAuthor(author);
    }

    private void insertAuthorPublications(NrfAuthor author, String authorId) throws SQLException {
        final List<GoogleScholarPublication> googleScholarPublications = googleScholarService.listPublications(author.initials, author.surname, author.initials);
        for(final GoogleScholarPublication googleScholarPublication: googleScholarPublications) {
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

    private void insertAuthorSubfields(NrfAuthor author, String authorId, Set<Subfield> allSubfields) throws SQLException {
        for(String subFieldStr: Stream.concat(author.primaryResearchFields.stream(), author.secondaryResearchFields.stream()).toList()) {
            final String subFieldId = hashingService.flatten(new ImmutableList.Builder<String>().add(subFieldStr).build());
            final Subfield subField = new Subfield(subFieldId, subFieldStr);
            allSubfields.add(subField);

            // Insert subfield for author
            final AuthorToSubfield authorToSubfield = new AuthorToSubfield(authorId, subFieldId);
            authorToSubfieldTable.insertAuthorToSubfield(authorToSubfield);
        }
    }

    private void insertAllSubfields(Set<Subfield> allSubfields) throws SQLException {
        for(Subfield subfield: allSubfields) {
            subfieldsTable.insertSubfield(subfield);
        }
    }
}
