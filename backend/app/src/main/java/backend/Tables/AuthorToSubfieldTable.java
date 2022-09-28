package backend.Tables;

import java.sql.*;
import java.util.*;


import backend.DatabaseModels.AuthorToSubfield;

public class AuthorToSubfieldTable extends SqlTable {

    public boolean insertAuthorToSubfield(AuthorToSubfield entry) throws SQLException {
        boolean inserted = false;
        PreparedStatement stmt = db.prepareStatement("INSERT into authorToSubfieldsMap (authorID, subFieldID) SELECT ?, ? WHERE NOT EXISTS (SELECT * FROM authorToSubfieldsMap WHERE authorID=? AND subFieldID=?)");
        stmt.setString(1, entry.authorId);
        stmt.setString(2, entry.subfieldId);

        stmt.setString(3, entry.authorId);
        stmt.setString(4, entry.subfieldId);
        inserted = stmt.executeUpdate() > 0;

        return inserted;
    }
 //finds all the author to subfield objects that match the given authorId
    public List<AuthorToSubfield> getAuthorSubfields(String authorId) throws SQLException {
        PreparedStatement stmt = db.prepareStatement("SELECT subFieldId FROM authorToSubfieldsMap WHERE authorId = ?");
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
    //finds all the author to subfield objects that match the given subfieldId
    public List<AuthorToSubfield> getAuthorSubfieldsBySubfield(String subfieldId) throws SQLException {
        PreparedStatement stmt = db.prepareStatement("SELECT authorId FROM authorToSubfieldsMap WHERE subfieldId = ?");
        stmt.setString(1, subfieldId);
        ResultSet rs = stmt.executeQuery();
        List<AuthorToSubfield> authorToSubfields = new ArrayList<>();

        while (rs.next()) {
            String authorId = rs.getString(1);
            AuthorToSubfield authorToSubfield = new AuthorToSubfield(authorId, subfieldId);
            authorToSubfields.add(authorToSubfield);
        }
        return authorToSubfields;
    }
 //removes all the author to subfield objects from the database
    public boolean clearAllForYear(String year) throws SQLException {
        boolean cleared = false;
        PreparedStatement stmt = db.prepareStatement("DELETE FROM authorToSubfieldsMap WHERE subfieldId LIKE ?");
        stmt.setString(1, "%" + year);
        cleared = stmt.executeUpdate() > 0;

        return cleared;
    }

}
