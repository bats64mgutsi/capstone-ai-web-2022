package backend.Tables;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

import backend.DatabaseModels.Institution;

public class InstitutionsTable extends SqlTable {
    public List<Institution> listAll() throws SQLException, SQLException {
        LinkedList<Institution> out = new LinkedList<>();

        Statement stmt = db.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM institutions");
        while (rs.next()) {
            String id = rs.getString(1);
            String name = rs.getString(2);
            Double latitude = rs.getDouble(3);
            Double longitude = rs.getDouble(4);
            Institution institution = new Institution(id, name, latitude, longitude);
            out.add(institution);
        }

        return out;
    }

    public Institution getInstitution(String institutionId) throws SQLException {
        PreparedStatement stmt = db
                .prepareStatement("SELECT id, name, latitude, longitude FROM institutions WHERE id = ?");
        stmt.setString(1, institutionId);
        ResultSet rs = stmt.executeQuery();
        rs.next();
        String id = rs.getString(1);
        String name = rs.getString(2);
        double latitude = rs.getDouble(3);
        double longitude = rs.getDouble(4);
        Institution institution = new Institution(id, name, latitude, longitude);
        return institution;
    }

}
