package backend.Tables;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import backend.DatabaseModels.Subfield;

public class SubfieldsTable extends SqlTable {
   
    public boolean insertSubfield(Subfield subfield) throws SQLException{
        boolean inserted = false;
        PreparedStatement stmt = db.prepareStatement(
                "INSERT into subFields (id, name) VALUES (?, ?) ON DUPLICATE KEY UPDATE name=?");
        stmt.setString(1, subfield.id);
        stmt.setString(2, subfield.name);
        stmt.setString(3, subfield.name);

        inserted = stmt.executeUpdate() > 0;
       
        return inserted;
    }
    public Subfield getSubfield(String subfieldId) throws SQLException{
        PreparedStatement stmt = db.prepareStatement("SELECT name FROM subfields WHERE id = ?");
        stmt.setString(1, subfieldId);
        ResultSet rs = stmt.executeQuery();
        rs.next();
        String name = rs.getString(1); 
        Subfield subfield = new Subfield(subfieldId, name);
        return subfield;   
    }
    // public List<Subfield> getAuthorSubfields(String authorId) throws SQLException {
    //     PreparedStatement stmt = db.prepareStatement("SELECT subFieldID FROM authorSubfieldsMap WHERE authorId = ?");
    //     List<Subfield> subfields = new ArrayList<>();
    //     stmt.setString(1, authorId);
    //     ResultSet rs = stmt.executeQuery();
    //     while (rs.next()) {
    //         String id = rs.getString(1);
    //         String subFieldID = rs.getString(2);

    //         PreparedStatement stmt2 = db.prepareStatement("SELECT name FROM subFields WHERE id  = ?");
    //         stmt2.setString(1, subFieldID);
    //         ResultSet rs2 = stmt2.executeQuery();
    //         rs2.next();
    //         String name = rs.getString(1);
    //         Subfield subfield = new Subfield(id, name);
    //         subfields.add(subfield);
    //     }
    //     return subfields;

    // }
    public boolean clearAll()throws SQLException{
        boolean cleared = false;
        PreparedStatement stmt = db.prepareStatement(
                "DELETE FROM subFields");
        
        cleared = stmt.executeUpdate() > 0;
       
        return cleared;
    }
}
