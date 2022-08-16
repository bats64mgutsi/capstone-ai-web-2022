package backend.data;

import java.util.*;

import backend.App;
import backend.models.Author;
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

    static private AuthorsTable authorsTable = null;

    private static AuthorsTable getInstance() throws SQLException {
        if (authorsTable == null) {
            return authorsTable = new AuthorsTable(SqlConection.getConnection());
        } else {
            return authorsTable;
        }
    }
}
