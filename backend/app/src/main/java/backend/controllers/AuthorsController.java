package backend.controllers;

import backend.ApplicationModels.AuthorProfile;
import backend.DatabaseModels.*;
import backend.Locator;
import backend.ApplicationModels.PopulatedAuthor;
import backend.Tables.*;

import java.sql.SQLException;
import java.util.*;

public class AuthorsController {
    // creates local instance of the table classes used.
    final AuthorsTable authorsTable = (AuthorsTable) Locator.instance.get(AuthorsTable.class);
    final ContributionsTable contributionsTable = (ContributionsTable) Locator.instance.get(ContributionsTable.class);
    final AuthorToSubfieldTable authorToSubfieldTable = (AuthorToSubfieldTable) Locator.instance
            .get(AuthorToSubfieldTable.class);
    final SubfieldsTable subfieldsTable = (SubfieldsTable) Locator.instance.get(SubfieldsTable.class);
    final PublicationsTable publicationsTable = (PublicationsTable) Locator.instance.get(PublicationsTable.class);
    final InstitutionsTable institutionsTable = (InstitutionsTable) Locator.instance.get(InstitutionsTable.class);

    // returns a list of populated authors (authors that have an institution object
    // and a list of their subfields as attributes)
    public List<PopulatedAuthor> listAuthors() throws SQLException {
        final List<PopulatedAuthor> immutableAuthors = authorsTable.listAll().stream().map(author -> {
            String institutionId = author.institution;
            Institution institution = null;
            try {
                institution = institutionsTable.getInstitution(institutionId);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            String id = author.id;
            String surname = author.surname;
            String initials = author.initials;
            String title = author.title;
            String rating = author.rating;

            try {
                final List<String> subfields = getAuthorSubfields(author.id).stream().map(el -> el.name).toList();
                return new PopulatedAuthor(id, surname, initials, title, institution, rating, subfields);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }).toList();

        final List<PopulatedAuthor> mutableAuthors = new LinkedList<>();
        mutableAuthors.addAll(immutableAuthors);
        mutableAuthors.sort(Comparator.comparing(el -> el.rating));
        return mutableAuthors;
    }
//returns the author profile belonging to the given authorID
    public AuthorProfile getProfile(String authorId) throws SQLException {
        final Author author = authorsTable.get(authorId);

        final List<Contribution> authorContributions = contributionsTable.listForAuthor(authorId);
        final List<Publication> publications = new LinkedList<>();
        for (final Contribution contribution : authorContributions) {
            final Publication publication = publicationsTable.getItemWithId(contribution.publicationId);
            publications.add(publication);
        }

        final List<String> subfields = getAuthorSubfields(authorId).stream().map(el -> el.name).toList();
        final int citationCount = publications.stream().map(el -> el.citationCount).reduce(0, Integer::sum);
        final Institution institution = institutionsTable.getInstitution(author.institution);
        return new AuthorProfile(author, subfields, publications, citationCount, institution);
    }
    //returns all the subfields belonging to the given authorID using the authrodToSubfield mapping table
    private List<Subfield> getAuthorSubfields(String authorId) throws SQLException {
        final List<AuthorToSubfield> authorToSubfields = authorToSubfieldTable.getAuthorSubfields(authorId);
        final List<Subfield> subfields = new LinkedList<>();
        for (final AuthorToSubfield authorToSubfield : authorToSubfields) {
            final Subfield subfield = subfieldsTable.getSubfield(authorToSubfield.subfieldId);
            subfields.add(subfield);
        }

        return subfields;
    }
}
