package backend.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlConection {
    static public Connection sqlCon = null;

    public static Connection getConnection() throws SQLException {
        if (sqlCon == null) {
            return sqlCon = DriverManager.getConnection("jdbc:mysql://localhost/aiweb", "root", "root");
        } else {
            return sqlCon;
        }
    }

}
