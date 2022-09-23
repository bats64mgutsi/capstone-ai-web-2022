package backend.Tables;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import backend.DatabaseModels.Author;
import backend.DatabaseModels.Publication;
import backend.DatabaseModels.Subfield;

public class AuthorsTable extends SqlTable {
    public boolean insertAuthor(Author author) throws SQLException {
        boolean inserted = false;
        PreparedStatement stmt = db.prepareStatement(
                "INSERT INTO authors (id, surname, initials, title, institution, rating) VALUES (?, ?, ?, ?, ?, ?)");
        stmt.setString(1, author.id);
        stmt.setString(2, author.surname);
        stmt.setString(3, author.initials);
        stmt.setString(4, author.title);
        stmt.setString(5, author.institution);
        stmt.setString(6, author.rating);
        inserted = stmt.executeUpdate() > 0;
        return inserted;

    }

    public boolean updateAuthor(String authorId, Author author) throws SQLException {
        boolean updated = false;
        PreparedStatement stmt = db.prepareStatement(
                "UPDATE authors SET surname = ?, initials = ?, title = ?, institution = ?, rating = ? WHERE authorId = ?");
        stmt.setString(1, author.surname);
        stmt.setString(2, author.initials);
        stmt.setString(3, author.title);
        stmt.setString(4, author.institution);
        stmt.setString(5, author.rating);
        stmt.setString(6, authorId);

        updated = stmt.executeUpdate() > 0;
        return updated;
    }

    public boolean deleteAuthor(String authorId) throws SQLException {
        boolean deleted = false;
        PreparedStatement stmt = db.prepareStatement("DELETE FROM authors WHERE authorId = ?");
        stmt.setString(1, authorId);
        deleted = stmt.executeUpdate() > 0;
        return deleted;
    }

    public List<Author> listAll() throws SQLException {
        LinkedList<Author> out = new LinkedList<>();

        Statement stmt = db.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM authors");
        while (rs.next()) {
            String id = rs.getString(1);
            String surname = rs.getString(2);
            String initials = rs.getString(3);
            String title = rs.getString(4);
            String institution = rs.getString(5);
            String rating = rs.getString(6);
            Author author = new Author(id, surname, initials, title, institution, rating);
            out.add(author);
        }

        return out;
    }

    public boolean clearAll() throws SQLException {
        boolean deleted = false;
        PreparedStatement stmt = db.prepareStatement("DELETE FROM authors");
        deleted = stmt.executeUpdate() > 0;
        return deleted;
    }

    public Author get(String authorId) throws SQLException {
        PreparedStatement stmt = db
                .prepareStatement("SELECT id, surname, initials, title, institution, rating FROM authors WHERE id = ?");
        stmt.setString(1, authorId);
        ResultSet rs = stmt.executeQuery();
        rs.next();
        String id = rs.getString(1);
        String surname = rs.getString(2);
        String initials = rs.getString(3);
        String title = rs.getString(4);
        String institution = rs.getString(5);
        String rating = rs.getString(6);

        Author author = new Author(id, surname, initials, title, institution, rating);

        return author;

    }
    public List<Author> getAuthors (String institutionID) throws SQLException{ //gets all the authors from a specific institution.
        List<Author> authors = new ArrayList<>();
        PreparedStatement stmt = db
                .prepareStatement("SELECT * FROM authors WHERE institution = ?");
        stmt.setString(1, institutionID);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            String id = rs.getString(1);
            String surname = rs.getString(2);
            String initials = rs.getString(3);
            String title = rs.getString(4);
            String institution = rs.getString(5);
            String rating = rs.getString(6);
            Author author = new Author(id, surname, initials, title, institution, rating);
            authors.add(author);
        }
        return authors;
    }

    
}
