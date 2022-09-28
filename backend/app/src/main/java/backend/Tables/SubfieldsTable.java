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
    
    public boolean clearAll()throws SQLException{
        boolean cleared = false;
        PreparedStatement stmt = db.prepareStatement(
                "DELETE FROM subFields");
        
        cleared = stmt.executeUpdate() > 0;
       
        return cleared;
    }

    public List<Subfield> listAll()throws SQLException{
        List<Subfield> out = new ArrayList<>();
        PreparedStatement stmt = db.prepareStatement(String.format("SELECT * FROM subfields"));
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            String id = rs.getString(1);
            String name = rs.getString(2);
 
            Subfield subfield = new Subfield(id, name);
            out.add(subfield);
        }

        return out;
    }
}
