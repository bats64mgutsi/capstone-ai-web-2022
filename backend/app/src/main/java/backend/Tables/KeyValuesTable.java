package backend.Tables;

import com.mysql.cj.util.Base64Decoder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

public class KeyValuesTable extends SqlTable {
    public boolean store(String key, String value) throws SQLException {
        boolean inserted = false;
        PreparedStatement stmt = db.prepareStatement("INSERT INTO keyValues (sKey, sValue) VALUES (?, ?) ON DUPLICATE KEY UPDATE sKey=?, sValue=?");
        stmt.setString(1, key);
        stmt.setString(2, value);

        stmt.setString(3, key);
        stmt.setString(4, value);

        inserted = stmt.executeUpdate() > 0;
        return inserted;
    }

    public String get(String key) throws SQLException {
        PreparedStatement stmt = db.prepareStatement("SELECT sValue FROM keyValues WHERE sKey = ?");
        stmt.setString(1, key);
        ResultSet rs = stmt.executeQuery();
        rs.next();
        return rs.getString(1);
    }
}
