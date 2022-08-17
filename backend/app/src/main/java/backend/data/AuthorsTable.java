package backend.data;

import java.util.*;

import backend.models.*;

import java.sql.*;

public class AuthorsTable {

    private final Connection sqlConection;

    public AuthorsTable(Connection sqlConection) {
        this.sqlConection = sqlConection;
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
        Statement stmt = sqlConection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM authors WHERE Surname = " + authorId);
        rs.first();
        String surname = rs.getString(1);
        String initials = rs.getString(2);
        String title = rs.getString(3);
        String institution = rs.getString(4);
        String rating = rs.getString(5);
        Author author = new Author(surname, initials, title, institution, rating);
        return author;
    }

    private List<Publication> getAuthorPublications(String authorId) throws SQLException {
        Statement stmt = sqlConection.createStatement();
        List<Publication> publications = new LinkedList<>();
        ResultSet rs = stmt.executeQuery("SELECT * FROM publications WHERE authorID = " + authorId);
        while (rs.next()) {
            int numberOfCitations = rs.getInt(3);
            String title = rs.getString(2);
            String year = rs.getString(5);
            String externalLink = rs.getString(4);
            Publication publication = new Publication(numberOfCitations, title, year, externalLink);
            publications.add(publication);
        }
        return publications;

    }

    private List<String> getAuthorSubfields(String authorId) throws SQLException {
        Statement stmt = sqlConection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM authorSubfieldsMap WHERE authorID=" + authorId);
        List<String> out = new ArrayList<>();

        while (rs.next()) {
            int subfieldID = rs.getInt(2);

            // Get Sub field as a string
            ResultSet rs2 = stmt.executeQuery("SELECT * FROM subFields WHERE ID=" + subfieldID);
            rs2.first();
            out.add(rs2.getString(2));
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

    static private AuthorsTable authorsTable = null;

    public static AuthorsTable getInstance() throws SQLException {
        if (authorsTable == null) {
            return authorsTable = new AuthorsTable(SqlConection.getConnection());
        } else {
            return authorsTable;
        }
    }
}
