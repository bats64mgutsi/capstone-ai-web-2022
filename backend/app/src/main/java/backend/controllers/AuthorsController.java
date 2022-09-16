package backend.controllers;

import backend.DatabaseModels.*;
import backend.Locator;
import backend.Tables.*;

import java.sql.SQLException;
import java.util.*;

public class AuthorsController {
    final AuthorsTable authorsTable = (AuthorsTable) Locator.instance.get(AuthorsTable.class);
    final ContributionsTable contributionsTable = (ContributionsTable) Locator.instance.get(ContributionsTable.class);
    final AuthorToSubfieldTable authorToSubfieldTable = (AuthorToSubfieldTable) Locator.instance.get(AuthorToSubfieldTable.class);
    final SubfieldsTable subfieldsTable = (SubfieldsTable) Locator.instance.get(SubfieldsTable.class);
    final PublicationsTable publicationsTable = (PublicationsTable) Locator.instance.get(PublicationsTable.class);

    public List<Author> listAuthors() throws SQLException {
        return authorsTable.listAll();
    }

    public AuthorProfile getProfile(String authorId) throws SQLException {
        final Author author = authorsTable.get(authorId);

        final List<Contribution> authorContributions = contributionsTable.listForAuthor(authorId);
        final List<Publication> publications = new LinkedList<>();
        for (final Contribution contribution : authorContributions) {
            final Publication publication = publicationsTable.getItemWithId(contribution.publicationId);
            publications.add(publication);
        }

        final List<AuthorToSubfield> authorToSubfields = authorToSubfieldTable.getAuthorSubfields(authorId);
        final List<String> subfields = new LinkedList<>();
        for (final AuthorToSubfield authorToSubfield : authorToSubfields) {
            final Subfield subfield = subfieldsTable.getSubfield(authorToSubfield.subfieldId);
            subfields.add(subfield.name);
        }

        return new AuthorProfile(author, subfields, publications);
    }
}
