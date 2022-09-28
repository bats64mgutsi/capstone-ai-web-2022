package backend.Tables;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

import backend.DatabaseModels.Admin;

public class AdminsTable extends SqlTable {
    // returns an admin that matches the given username and password from the
    // database.
    public Admin getAdmin(String username, String hashedPassword) throws SQLException {
        PreparedStatement stmt = db.prepareStatement("SELECT id FROM admins WHERE username = ? AND hashedPassword = ?");
        stmt.setString(1, username);
        stmt.setString(2, hashedPassword);
        ResultSet rs = stmt.executeQuery();
        rs.next();
        int id = rs.getInt(1);
        Admin admin = new Admin(id, username, hashedPassword);
        return admin;
    }

    // returns all the admins from the database
    public List<Admin> listAll() throws SQLException {
        PreparedStatement stmt = db.prepareStatement("SELECT * FROM admins");
        ResultSet rs = stmt.executeQuery();

        final List<Admin> out = new LinkedList<>();
        while (rs.next()) {
            final int id = rs.getInt(1);
            final String username = rs.getString(2);
            final String hashedPassword = rs.getString(3);

            out.add(new Admin(id, username, hashedPassword));
        }

        return out;
    }
}
