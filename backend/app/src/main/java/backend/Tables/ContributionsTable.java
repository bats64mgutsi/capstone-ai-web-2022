package backend.Tables;

import java.sql.*;

import backend.DatabaseModels.Contribution;

public class ContributionsTable extends SqlTable {
    public void setContribution(Contribution contribution) throws SQLException {
        
            PreparedStatement stmt = db.prepareStatement(
                    "INSERT into contributions (publicationId, contributorId, type) VALUES (?, ?, ?)");
                    stmt.setString(1, contribution.publicationId);
                    stmt.setString(2, contribution.contributorId);
            // stmt.set(1, contribution.type);
            stmt.executeUpdate();
        }

    public void clearAll() {
    }
}

