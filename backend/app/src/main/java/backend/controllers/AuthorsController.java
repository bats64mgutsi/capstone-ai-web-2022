package backend.controllers;

import backend.ApplicationModels.AuthorProfile;
import backend.DatabaseModels.*;
import backend.Locator;
import backend.ApplicationModels.PopulatedAuthor;
import backend.Tables.*;

import java.sql.SQLException;
import java.util.*;

public class AuthorsController {
    final AuthorsTable authorsTable = (AuthorsTable) Locator.instance.get(AuthorsTable.class);
    final ContributionsTable contributionsTable = (ContributionsTable) Locator.instance.get(ContributionsTable.class);
    final AuthorToSubfieldTable authorToSubfieldTable = (AuthorToSubfieldTable) Locator.instance.get(AuthorToSubfieldTable.class);
    final SubfieldsTable subfieldsTable = (SubfieldsTable) Locator.instance.get(SubfieldsTable.class);
    final PublicationsTable publicationsTable = (PublicationsTable) Locator.instance.get(PublicationsTable.class);
    final InstitutionsTable institutionsTable = (InstitutionsTable) Locator.instance.get(InstitutionsTable.class);
   
    public List<PopulatedAuthor> listAuthors() throws SQLException {
         return authorsTable.listAll().stream().map(author -> {
            String institutionId = author.institution;
            Institution institution=null;
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
    
        }

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
        return new AuthorProfile(author, subfields, publications, citationCount);
    }

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
