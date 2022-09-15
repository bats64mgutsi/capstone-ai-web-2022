package backend.Tables;

import backend.Locator;

import java.sql.Connection;

public abstract class SqlTable {
    final Connection db = (Connection) Locator.instance.get(Connection.class);
}
