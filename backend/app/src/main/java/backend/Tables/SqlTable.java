package backend.Tables;

import java.sql.Connection;

<<<<<<< HEAD
public class SqlTable {
    Connection db;

=======
public abstract class SqlTable {
    Connection db;

    public SqlTable(){
        
    }
>>>>>>> master
    public SqlTable(Connection db) {
        this.db = db;
    }
}
