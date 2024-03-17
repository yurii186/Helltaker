package main.sk.tuke.gamestudio.game.core.db;

import main.sk.tuke.gamestudio.entity.Score;
import main.sk.tuke.gamestudio.service.ScoreServiceJDBC;

import java.sql.*;

public class ScoreUpdater {
    public void addOrUpdateScore(Score score) {
        final String SELECT_EXISTING_SCORE = "SELECT points FROM score WHERE player = ? AND game = ?";
        final String INSERT_SCORE = "INSERT INTO score (player, game, points, playedon) VALUES (?, ?, ?, ?)";
        final String UPDATE_SCORE = "UPDATE score SET points = ?, playedon = ? WHERE player = ? AND game = ?";

        try (Connection connection = DriverManager.getConnection(DBConfig.getUrl(), DBConfig.getUser(), DBConfig.getPassword())) {
            // Check if there's already a score for this player and game
            try (PreparedStatement selectStmt = connection.prepareStatement(SELECT_EXISTING_SCORE)) {
                selectStmt.setString(1, score.getPlayer());
                selectStmt.setString(2, score.getGame());
                ResultSet rs = selectStmt.executeQuery();

                if (rs.next()) {
                    int existingPoints = rs.getInt(1);
                    if (score.getPoints() > existingPoints) {
                        // If the new score is higher, update the existing record
                        try (PreparedStatement updateStmt = connection.prepareStatement(UPDATE_SCORE)) {
                            updateStmt.setInt(1, score.getPoints());
                            updateStmt.setTimestamp(2, new Timestamp(score.getPlayedOn().getTime()));
                            updateStmt.setString(3, score.getPlayer());
                            updateStmt.setString(4, score.getGame());
                            updateStmt.executeUpdate();
                        }
                    }
                    // If the new score is lower or equal, do nothing
                } else {
                    // If the record wasn't found, insert a new one
                    ScoreServiceJDBC scoreServiceJDBC = new ScoreServiceJDBC();
                    scoreServiceJDBC.addScore(score);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}