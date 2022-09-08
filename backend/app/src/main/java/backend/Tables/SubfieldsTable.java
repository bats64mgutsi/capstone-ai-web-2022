package backend.Tables;

import java.sql.*;

import backend.DatabaseModels.Subfield;

public class SubfieldsTable extends SqlTable {
    public boolean insertSubfield(Subfield subfield) throws SQLException{
        boolean inserted = false;
        PreparedStatement stmt = db.prepareStatement(
                "INSERT into subFields (subField) VALUES (?)");
        stmt.setString(1, subfield.name);
        inserted = stmt.executeUpdate() > 0;
       
        return inserted;
    }

    public boolean clearAll()throws SQLException{
        boolean cleared = false;
        PreparedStatement stmt = db.prepareStatement(
                "DELETE * FROM subFields");
        
        cleared = stmt.executeUpdate() > 0;
       
        return cleared;
    }
}
