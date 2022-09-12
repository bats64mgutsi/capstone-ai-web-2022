package backend.Tables;

import java.sql.*;

import backend.DatabaseModels.AuthorToSubfield;

public class AuthorToSubfieldTable extends SqlTable{

    public boolean insertAuthorToSubfield(AuthorToSubfield entry)throws SQLException{
        boolean inserted = false;
        PreparedStatement stmt = db.prepareStatement(
                "INSERT into authorSubfieldsMap (authorID, subFieldID) VALUES (?, ?)");
        stmt.setString(1, entry.authorId);
        stmt.setString(2, entry.subfieldId);
        inserted = stmt.executeUpdate() > 0;
       
        return inserted;
    }

    public boolean clearAll()throws SQLException{
        boolean cleared = false;
        PreparedStatement stmt = db.prepareStatement(
                "DELETE * FROM authorSubfieldsMap");
        
        cleared = stmt.executeUpdate() > 0;
       
        return cleared;
    }
}
