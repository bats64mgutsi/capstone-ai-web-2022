package backend.Tables;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import backend.DatabaseModels.Contribution;
import backend.DatabaseModels.Contribution.ContributionType;

public class ContributionsTable extends SqlTable {
    public void setContribution(Contribution contribution) throws SQLException {
        PreparedStatement stmt = db.prepareStatement(
                "INSERT INTO contributions (publicationId, contributorId, type) VALUES (?, ?, ?)");
        stmt.setString(1, contribution.publicationId);
        stmt.setString(2, contribution.contributorId);

        if (contribution.type == ContributionType.MainAuthor) {
            stmt.setString(3, "MainAuthor");
        } else {
            stmt.setString(3, "CoAuthor");
        }
        stmt.executeUpdate();
    }

    public List<Contribution> listForAuthor(String authorId) throws SQLException{
        PreparedStatement stmt = db.prepareStatement("SELECT publicationId, contributorId, type FROM contributions WHERE contributorId = ?");
        stmt.setString(1, authorId);
        ResultSet rs = stmt.executeQuery();
        List<Contribution> contributions = new ArrayList<>(); 
        while (rs.next()){
            String publicationId = rs.getString(1);
            String contributorId = rs.getString(2);
            Contribution contribution = new Contribution(publicationId, contributorId, ContributionType.MainAuthor);
            contributions.add(contribution);
        }
       
        return contributions;
    }
    
    public boolean clearAll()throws SQLException{
        boolean cleared = false;
        PreparedStatement stmt = db.prepareStatement(
                "DELETE FROM contributions");
        
        cleared = stmt.executeUpdate() > 0;
       
        return cleared;
    }
}

