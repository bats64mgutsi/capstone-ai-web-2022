package backend.Tables;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import backend.DatabaseModels.Author;

public class AuthorsTable extends SqlTable {
    final String tableName;

    public AuthorsTable(String tableName) {
        this.tableName = tableName;
    }

    public AuthorsTable() {
        this.tableName = "authors";
    }

    // adds an author object to the database
    public boolean insertAuthor(Author author) throws SQLException {
        boolean inserted = false;
        PreparedStatement stmt = db.prepareStatement(
                String.format(
                        "INSERT INTO %s (id, surname, initials, title, institution, rating, yearAdded) VALUES (?, ?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE title=?, institution=?, rating=?, yearAdded=?",
                        tableName));
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

    // changes an author that matches the given authorId with the given author
    // object
    public boolean updateAuthor(String authorId, Author author) throws SQLException {
        boolean updated = false;
        PreparedStatement stmt = db.prepareStatement(
                String.format(
                        "UPDATE %s SET surname = ?, initials = ?, title = ?, institution = ?, rating = ? WHERE authorId = ?",
                        tableName));
        stmt.setString(1, author.surname);
        stmt.setString(2, author.initials);
        stmt.setString(3, author.title);
        stmt.setString(4, author.institution);
        stmt.setString(5, author.rating);
        stmt.setString(6, authorId);

        updated = stmt.executeUpdate() > 0;
        return updated;
    }

    // removes an author that mataches the authorId given from the database
    public boolean deleteAuthor(String authorId) throws SQLException {
        boolean deleted = false;
        PreparedStatement stmt = db.prepareStatement(String.format("DELETE FROM %s WHERE authorId = ?", tableName));
        stmt.setString(1, authorId);
        deleted = stmt.executeUpdate() > 0;
        return deleted;
    }

    /**
     * Returns all authors for the current year.
     * 
     * @return
     * @throws SQLException
     */
    public List<Author> listAll() throws SQLException {
        return listAllCurrentYear();
    }

    // removes all authors from the database
    public boolean clearAll() throws SQLException {
        boolean deleted = false;
        PreparedStatement stmt = db.prepareStatement(String.format("DELETE FROM %s", tableName));
        deleted = stmt.executeUpdate() > 0;
        return deleted;
    }

    // finds and returns an author given a specific authorId
    public Author get(String authorId) throws SQLException {
        PreparedStatement stmt = db
                .prepareStatement(String.format(
                        "SELECT id, surname, initials, title, institution, rating FROM %s WHERE id = ?", tableName));
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

    /**
     * Returns current year authors from the institution with the given id.
     * 
     * @param institutionID
     * @return
     * @throws SQLException
     */
    public List<Author> getAuthors(String institutionID) throws SQLException { // gets all the authors from a specific
                                                                               // institution.
        final int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        List<Author> authors = new ArrayList<>();
        PreparedStatement stmt = db
                .prepareStatement("SELECT * FROM authors WHERE institution = ? AND id LIKE ?");
        stmt.setString(1, institutionID);
        stmt.setString(2, "%" + currentYear);
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

    // finds and returns all the authors that were added last year to the database
    public List<Author> listAllPrevYear() throws SQLException {
        int prevYear = Calendar.getInstance().get(Calendar.YEAR) - 1;
        return listAll(Integer.toString(prevYear));
    };

    // finds and returns all the authors that were added this year to the database
    private List<Author> listAllCurrentYear() throws SQLException {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        return listAll(Integer.toString(currentYear));
    };

    // finds and returns all the authors in our database

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
