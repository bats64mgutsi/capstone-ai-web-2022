package backend.data;

import backend.models.Author;
import backend.models.AuthorProfile;
import backend.models.Publication;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class AuthorsTable {

    static private AuthorsTable authorsTable = null;
    private final Connection sqlConection;

    public AuthorsTable(Connection sqlConection) {
        this.sqlConection = sqlConection;
    }

    public static AuthorsTable getInstance() throws SQLException {
        if (authorsTable == null) {
            return authorsTable = new AuthorsTable(SqlConection.getConnection());
        } else {
            return authorsTable;
        }
    }

    public List<Author> listAuthors() throws SQLException {

        LinkedList<Author> out = new LinkedList<>();

        Statement stmt = sqlConection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM authors");
        while (rs.next()) {
            String surname = rs.getString(1);
            String initials = rs.getString(2);
            String title = rs.getString(3);
            String institution = rs.getString(4);
            String rating = rs.getString(5);
            Author author = new Author(surname, initials, title, institution, rating);
            out.add(author);
        }

        return out;
    }

    private Author getAuthor(String authorId) throws SQLException {
        PreparedStatement stmt = sqlConection.prepareStatement("SELECT * FROM authors WHERE Surname=?");
        stmt.setString(1, authorId);
        ResultSet rs = stmt.executeQuery();

        rs.next();
        String surname = rs.getString(1);
        String initials = rs.getString(2);
        String title = rs.getString(3);
        String institution = rs.getString(4);
        String rating = rs.getString(5);

        Author author = new Author(surname, initials, title, institution, rating);
        return author;
    }

    private List<Publication> getAuthorPublications(String authorId) throws SQLException {
        PreparedStatement stmt = sqlConection.prepareStatement("SELECT * FROM publications WHERE authorID=?");
        stmt.setString(1, authorId);
        ResultSet rs = stmt.executeQuery();

        List<Publication> publications = new LinkedList<>();
        while (rs.next()) {
            String title = rs.getString(2);
            int numberOfCitations = rs.getInt(3);
            String externalLink = rs.getString(4);
            String year = rs.getString(5);
            Publication publication = new Publication(numberOfCitations, title, year, externalLink);
            publications.add(publication);
        }

        return publications;
    }

    private List<String> getAuthorSubfields(String authorId) throws SQLException {
        PreparedStatement stmt = sqlConection.prepareStatement("SELECT * FROM authorSubfieldsMap WHERE authorID=?");
        stmt.setString(1, authorId);
        ResultSet rs = stmt.executeQuery();

        List<String> out = new ArrayList<>();
        while (rs.next()) {
            int subfieldID = rs.getInt(2);

            stmt = sqlConection.prepareStatement("SELECT * FROM subFields WHERE ID=?");
            stmt.setInt(1, subfieldID);
            ResultSet innerRS = stmt.executeQuery();

            innerRS.next();
            out.add(innerRS.getString(2));
        }

        return out;
    }

    public AuthorProfile getProfile(String authorId) throws SQLException {
        Author author = getAuthor(authorId);
        List<String> subFields = getAuthorSubfields(authorId);
        List<Publication> publications = getAuthorPublications(authorId);

        AuthorProfile authorProfile = new AuthorProfile(author, subFields, publications);
        return authorProfile;
    }
}
