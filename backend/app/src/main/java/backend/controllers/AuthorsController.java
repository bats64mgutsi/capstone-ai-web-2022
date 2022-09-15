package backend.controllers;

import backend.DatabaseModels.Author;
import backend.DatabaseModels.AuthorProfile;
import backend.DatabaseModels.Publication;
import backend.Locator;
import backend.Tables.AuthorsTable;

import java.sql.SQLException;
import java.util.*;

public class AuthorsController {
    final AuthorsTable authorsTable = (AuthorsTable) Locator.instance.get(AuthorsTable.class);

    public List<Author> listAuthors() throws SQLException {
        return authorsTable.listAll();
    }

    public AuthorProfile getProfile(String authorId) throws SQLException {
        Author author = authorsTable.get(authorId);
        List<String> subFields = authorsTable.getAuthorSubfields(authorId);
        List<Publication> publications = authorsTable.getAuthorPublications(authorId);

        AuthorProfile authorProfile = new AuthorProfile(author, subFields, publications);
        return authorProfile;
    }
}
