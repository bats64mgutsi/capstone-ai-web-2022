package backend.Tables;

import java.sql.*;
import java.util.*;
import backend.DatabaseModels.Subfield;

import backend.DatabaseModels.AuthorToSubfield;

public class AuthorToSubfieldTable extends SqlTable {

    public boolean insertAuthorToSubfield(AuthorToSubfield entry) throws SQLException {
        boolean inserted = false;
        PreparedStatement stmt = db.prepareStatement(
                "INSERT into authorSubfieldsMap (authorID, subFieldID) VALUES (?, ?)");
        stmt.setString(1, entry.authorId);
        stmt.setString(2, entry.subfieldId);
        inserted = stmt.executeUpdate() > 0;

        return inserted;
    }

    public List<AuthorToSubfield> getAuthorSubfields(String authorId) throws SQLException {
        PreparedStatement stmt = db.prepareStatement("SELECT subFieldId FROM authorSubfieldsMap WHERE authorId = ?");
        List<Subfield> subfields = new ArrayList<>();
        stmt.setString(1, authorId);
        ResultSet rs = stmt.executeQuery();
        List<AuthorToSubfield> authorToSubfields = new ArrayList<>();

        while (rs.next()) {
            String subFieldID = rs.getString(1);
            AuthorToSubfield authorToSubfield = new AuthorToSubfield(authorId, subFieldID);
            authorToSubfields.add(authorToSubfield);
        }
        return authorToSubfields;
    }

    public boolean clearAll() throws SQLException {
        boolean cleared = false;
        PreparedStatement stmt = db.prepareStatement(
                "DELETE * FROM authorSubfieldsMap");

        cleared = stmt.executeUpdate() > 0;

        return cleared;
    }
}
