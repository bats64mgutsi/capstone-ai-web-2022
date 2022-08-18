package backend.controllers;

import backend.data.AuthorsTable;
import backend.models.Author;
import backend.models.AuthorProfile;
import backend.models.Publication;
import java.sql.SQLException;
import java.util.*;

abstract public class AuthorsController {
    public static List<Author> listAuthors() throws SQLException {
        return AuthorsTable.getInstance().listAuthors();
    }

    public static AuthorProfile getProfile(String authorId) throws SQLException {
        Author author = AuthorsTable.getInstance().getAuthor(authorId);
        List<String> subFields = AuthorsTable.getInstance().getAuthorSubfields(authorId);
        List<Publication> publications = AuthorsTable.getInstance().getAuthorPublications(authorId);

        AuthorProfile authorProfile = new AuthorProfile(author, subFields, publications);
        return authorProfile;
    }
}
