package backend.Tables;

import java.sql.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AiKeywordsTable extends SqlTable {
    public boolean insertKeyword(String keyword) throws SQLException {
        boolean inserted = false;
        PreparedStatement stmt = db.prepareStatement(
                "INSERT INTO keywords (keyword) VALUES (?)");
        stmt.setString(1, keyword);
        inserted = stmt.executeUpdate() > 0;
        return inserted;

    }

    public List<String> listAll() throws SQLException {
        List<String> keywords = new ArrayList<>();
        Statement stmt = db.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM aiKeywords");
        while (rs.next()) {
            String keyword = rs.getString(1);
            keywords.add(keyword);
        }

        return keywords;
    }

    public boolean clearAll() throws SQLException {
        boolean deleted = false;
        PreparedStatement stmt = db.prepareStatement("DELETE * FROM aiKeywords");
        deleted = stmt.executeUpdate() > 0;
        return deleted;
    }
}
