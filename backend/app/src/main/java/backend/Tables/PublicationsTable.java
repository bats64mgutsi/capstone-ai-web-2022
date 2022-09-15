package backend.Tables;

import java.sql.*;

import backend.DatabaseModels.Publication;

public class PublicationsTable extends SqlTable {

    public boolean insertPublication(Publication publication) throws SQLException{
        boolean inserted = false;
        PreparedStatement stmt = db.prepareStatement(
                "INSERT into publications (ID, title, citationCount, externalLink, publicationDate) VALUES (?, ?, ?, ?");
        stmt.setString(1, publication.id);
        stmt.setString(2, publication.title);
        stmt.setInt(3, publication.citationCount);
        stmt.setString(4, publication.externalLink);
        stmt.setString(5, publication.year);

        inserted = stmt.executeUpdate() > 0;
        return inserted;

    }
    

    public boolean deletePublication(String publicationId) throws SQLException{
        boolean deleted = false;
        PreparedStatement stmt = db.prepareStatement("DELETE FROM publications WHERE ID = ?");
        stmt.setString(1, publicationId);
        deleted = stmt.executeUpdate() > 0;
        return deleted;
    }

    public Publication getItemWithId(String publicationId) throws SQLException{
        PreparedStatement stmt = db.prepareStatement("SELECT ID, title, citationCount, externalLink, publicationDate WHERE ID = ?");
        stmt.setString(1, publicationId);
        ResultSet rs = stmt.executeQuery();
        String id = rs.getString(1);
        String title = rs.getString(2);
        int citationCount = rs.getInt(3);
        String externalLink = rs.getString(4);
        String year = rs.getString(5);
        Publication publication = new Publication(id, citationCount, title, year, externalLink);
        return publication;
    }

    public Publication getAuthorPublications(String authorId) throws SQLException{
        PreparedStatement stmt = db.prepareStatement("SELECT ID, title, citationCount, externalLink, publicationDate WHERE ID = ?");
        stmt.setString(1, publicationId);
        ResultSet rs = stmt.executeQuery();
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
        PreparedStatement stmt = db.prepareStatement("DELETE * FROM publications");
        deleted = stmt.executeUpdate() > 0;
        return deleted;
    }
}
