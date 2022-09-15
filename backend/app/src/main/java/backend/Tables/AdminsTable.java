package backend.Tables;

import java.sql.*;

import backend.DatabaseModels.Admin;

public class AdminsTable extends SqlTable {

    public Admin getAdmin(String username, String hashedPassword) throws SQLException {
        PreparedStatement stmt = db.prepareStatement("SELECT id WHERE username = ? AND hashedPassword = ?");
        stmt.setString(1, username);
        stmt.setString(2, hashedPassword);
        ResultSet rs = stmt.executeQuery();
        rs.next();
        int id = rs.getInt(1);
        Admin admin = new Admin(id, username, hashedPassword);
        return admin;
    }
}
