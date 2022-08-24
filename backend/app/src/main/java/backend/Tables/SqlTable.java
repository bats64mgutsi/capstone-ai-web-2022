package backend.Tables;

import java.sql.Connection;

public class SqlTable {
    Connection db;

    public SqlTable(Connection db) {
        this.db = db;
    }
}
