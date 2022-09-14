package backend.Tables;

import java.sql.*;

import backend.DatabaseModels.Admin;

public class AdminsTable extends SqlTable {

    public Admin getAdmin(String username, String hashedPassword) throws SQLException {
        PreparedStatement stmt = db.prepareStatement("SELECT username, hashedPassword WHERE username = ?");
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();
        String Username = rs.getString(1);
        String HashedPassword = rs.getString(2);

        Admin admin = new Admin(Username, HashedPassword);
        return admin;
    }
}
