package backend.Tables;

import backend.Locator;

import java.sql.Connection;

//this class solely exists to establish a connection to the database which is extended to all the table classes.
public abstract class SqlTable {
    final Connection db = (Connection) Locator.instance.get(Connection.class);
}
