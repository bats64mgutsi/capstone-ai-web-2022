package backend.Tables;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import backend.DatabaseModels.Publication;

public class PublicationsTable extends SqlTable {

    public boolean insertPublication(Publication publication) throws SQLException {
        boolean inserted = false;
        PreparedStatement stmt = db.prepareStatement(
                "INSERT INTO publications (id, title, citationCount, externalLink, year) VALUES (?, ?, ?, ?, ?)");
        stmt.setString(1, publication.id);
        stmt.setString(2, publication.title);
        stmt.setInt(3, publication.citationCount);
        stmt.setString(4, publication.externalLink);
        stmt.setString(5, publication.year);

        inserted = stmt.executeUpdate() > 0;
        return inserted;

    }

    public List<Publication> listAll() throws SQLException{
        List<Publication> publications = new ArrayList<>();
        Statement stmt = db.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM publications");
        while (rs.next()) {
                    String id = rs.getString(1);
                    String title = rs.getString(2);
                    int citationCount = rs.getInt(3);
                    String externalLink = rs.getString(4);
                    String year = rs.getString(5);
                    Publication publication = new Publication(id, citationCount, title, year, externalLink);
                    publications.add(publication);
                }
                return publications;
        }
        


    public int noOfPublications() throws SQLException {
        int publications = 0;
        Statement stmt = db.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM publications");
        while (rs.next()) {
            publications++;
        }

        return publications;
    }

    public boolean deletePublication(String publicationId) throws SQLException {
        boolean deleted = false;
        PreparedStatement stmt = db.prepareStatement("DELETE FROM publications WHERE ID = ?");
        stmt.setString(1, publicationId);
        deleted = stmt.executeUpdate() > 0;
        return deleted;
    }

    public Publication getItemWithId(String publicationId) throws SQLException {
        PreparedStatement stmt = db
                .prepareStatement("SELECT id, title, citationCount, externalLink, year FROM publications WHERE id = ?");
        stmt.setString(1, publicationId);
        ResultSet rs = stmt.executeQuery();
        rs.next();
        String id = rs.getString(1);
        String title = rs.getString(2);
        int citationCount = rs.getInt(3);
        String externalLink = rs.getString(4);
        String year = rs.getString(5);
        Publication publication = new Publication(id, citationCount, title, year, externalLink);
        return publication;
    }

    public boolean clearAll() throws SQLException {
        boolean deleted = false;
        PreparedStatement stmt = db.prepareStatement("DELETE FROM publications");
        deleted = stmt.executeUpdate() > 0;
        return deleted;
    }
}
