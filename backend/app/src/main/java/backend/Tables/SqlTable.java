package backend.Tables;

import java.sql.Connection;

public abstract class SqlTable {
    Connection db;

    public SqlTable(){
        
    }
    public SqlTable(Connection db) {
        this.db = db;
    }
}
