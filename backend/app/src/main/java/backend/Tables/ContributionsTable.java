package backend.Tables;

import java.sql.*;

import backend.DatabaseModels.Contribution;

public class ContributionsTable extends SqlTable {
    public void setContribution(Contribution contribution)throws SQLException{
        PreparedStatement stmt = db.prepareStatement("SELECT publicationId, contributorId, type WHERE publicationId = ?");
        stmt.setString(1, contribution.publicationId);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()){
            String publicationId = rs.getString(1);
            String contributorId = rs.getString(2);
            //type?
        }
        if (rs==null){
            PreparedStatement stmt2 = db.prepareStatement("INSERT into contributions (publicationId, contributorId, type) VALUES (?, ?, ?)");
            stmt2.setString(1, contribution.publicationId);
            stmt2.setString(2, contribution.contributorId);
            //stmt.set(1, contribution.type);
            stmt.executeUpdate();
        }
        
}
