package backend.data;
import java.util.*;

import backend.App;

import java.sql.*;
public class AuthorsTable {
    Statement stmt = App.getCon().createStatement();
    ArrayList<ArrayList<String>> outer = new ArrayList<ArrayList<String>>();
    
    static private final AuthorsTable authorsTable = null;

    
    public ArrayList<ArrayList<String>> showAuthors()
    {
        ArrayList<String> inner; 
        ResultSet rs = statement.executeQuery("SELECT * FROM authors");
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnsNumber = rsmd.getColumnCount();
        while (rs.next())
        {
            inner = new ArrayList<String>();
            for (int i =1; i<=columnsNumber;i++){
                inner.add(rs.getString(i));
            }
            outer.add(inner);
        }
        return outer;
    }
}
