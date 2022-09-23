package backend.Tables;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import backend.DatabaseModels.Author;
import backend.DatabaseModels.Publication;
import backend.DatabaseModels.Subfield;

public class AuthorsTable extends SqlTable {
    final String tableName;

    public AuthorsTable(String tableName) {
        this.tableName = tableName;
    }

    public AuthorsTable() {
        this.tableName = "authors";
    }

    public boolean insertAuthor(Author author) throws SQLException {
        boolean inserted = false;
        PreparedStatement stmt = db.prepareStatement(
                String.format("INSERT INTO %s (id, surname, initials, title, institution, rating, yearAdded) VALUES (?, ?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE title=?, institution=?, rating=?, yearAdded=?", tableName));
        stmt.setString(1, author.id);
        stmt.setString(2, author.surname);
        stmt.setString(3, author.initials);
        stmt.setString(4, author.title);
        stmt.setString(5, author.institution);
        stmt.setString(6, author.rating);
        stmt.setString(7, author.yearAdded);

        stmt.setString(8, author.title);
        stmt.setString(9, author.institution);
        stmt.setString(10, author.rating);
        stmt.setString(11, author.yearAdded);

        inserted = stmt.executeUpdate() > 0;
        return inserted;

    }

    public boolean updateAuthor(String authorId, Author author) throws SQLException {
        boolean updated = false;
        PreparedStatement stmt = db.prepareStatement(
                String.format("UPDATE %s SET surname = ?, initials = ?, title = ?, institution = ?, rating = ? WHERE authorId = ?", tableName));
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
        PreparedStatement stmt = db.prepareStatement(String.format("DELETE FROM %s WHERE authorId = ?", tableName));
        stmt.setString(1, authorId);
        deleted = stmt.executeUpdate() > 0;
        return deleted;
    }

    public List<Author> listAll() throws SQLException {
        return listAllCurrentYear();
    }

    public boolean clearAll() throws SQLException {
        boolean deleted = false;
        PreparedStatement stmt = db.prepareStatement(String.format("DELETE FROM %s", tableName));
        deleted = stmt.executeUpdate() > 0;
        return deleted;
    }

    public Author get(String authorId) throws SQLException {
        PreparedStatement stmt = db
                .prepareStatement(String.format("SELECT id, surname, initials, title, institution, rating FROM %s WHERE id = ?", tableName));
        stmt.setString(1, authorId);
        ResultSet rs = stmt.executeQuery();
        rs.next();
        String id = rs.getString(1);
        String surname = rs.getString(2);
        String initials = rs.getString(3);
        String title = rs.getString(4);
        String institution = rs.getString(5);
        String rating = rs.getString(6);
        String yearAdded = rs.getString(6);

        return new Author(id, surname, initials, title, institution, rating, yearAdded);
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
            String yearAdded = rs.getString(7);
            Author author = new Author(id, surname, initials, title, institution, rating, yearAdded);
            authors.add(author);
        }

        return authors;
    }

    public List<Author> listAllPrevYear() throws SQLException {
        int prevYear = Calendar.getInstance().get(Calendar.YEAR)-1;
        return listAll(Integer.toString(prevYear));
    };

    private List<Author> listAllCurrentYear() throws SQLException {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        return listAll(Integer.toString(currentYear));
    };

    private List<Author> listAll(String year) throws SQLException {
        LinkedList<Author> out = new LinkedList<>();

        PreparedStatement stmt = db.prepareStatement(String.format("SELECT * FROM %s WHERE yearAdded=?", tableName));
        stmt.setString(1, year);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            String id = rs.getString(1);
            String surname = rs.getString(2);
            String initials = rs.getString(3);
            String title = rs.getString(4);
            String institution = rs.getString(5);
            String rating = rs.getString(6);
            String yearAdded = rs.getString(7);
            Author author = new Author(id, surname, initials, title, institution, rating, yearAdded);
            out.add(author);
        }

        return out;
    }
}
