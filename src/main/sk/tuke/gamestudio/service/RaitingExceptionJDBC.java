package main.sk.tuke.gamestudio.service;

import main.sk.tuke.gamestudio.game.core.db.DBConfig;
import main.sk.tuke.gamestudio.entity.Rating;

import java.sql.*;

public class RaitingExceptionJDBC implements RatingService {
    public static final String URL = "jdbc:postgresql://localhost/gamestudio";
    public static final String USER = "postgres";
    public static final String PASSWORD = "postgres";
    public static final String INSERT = "INSERT INTO rating (player, game, rating, ratedon) VALUES (?, ?, ?, ?)";
    public static final String UPDATE = "UPDATE rating SET rating = ? , ratedon = ? WHERE game = ? AND player = ?";
    public static final String SELECT = "SELECT player, rating FROM rating WHERE game = ?";

    @Override
    public void setRating(Rating rating) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(INSERT)
        ) {
            statement.setString(1, rating.getPlayer());
            statement.setString(2, rating.getGame());
            statement.setInt(3, rating.getRating());
            statement.setTimestamp(4, new Timestamp(rating.getRatedOn().getTime()));

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RatingException("Problem setting rating", e);
        }
    }

    @Override
    public void updateRating(Rating rating) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(UPDATE)
        ) {
            statement.setInt(1, rating.getRating());
            statement.setTimestamp(2, new Timestamp(rating.getRatedOn().getTime()));
            statement.setString(3, rating.getGame());
            statement.setString(4, rating.getPlayer());

            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Rating updated successfully.");
            } else {
                System.out.println("Failed to update rating.");
            }

        } catch (SQLException e) {
            throw new RatingException("Problem updating rating", e);
        }
    }

    @Override
    public int getAverageRating(String game) {
        double averageRating = 0.0;
        int count = 0;

        try (Connection connection = DriverManager.getConnection(DBConfig.getUrl(), DBConfig.getUser(), DBConfig.getPassword())) {
            String sql = "SELECT rating FROM rating";

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                averageRating += resultSet.getDouble("rating");
                count++;
            }

            averageRating /= count;

        } catch (SQLException e) {
            throw new RatingException("Problem calculating average rating", e);
        }
        return (int) Math.round(averageRating);
    }

    @Override
    public int getRating(String game, String player) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(SELECT)
        ) {
            statement.setString(1, game);  // Устанавливаем параметр запроса

            ResultSet resultSet = statement.executeQuery();

            int rating = -1; // Изначально устанавливаем рейтинг как -1
            while (resultSet.next()) {
                String existingPlayer = resultSet.getString("player");
                if (player.equals(existingPlayer)) {
                    rating = resultSet.getInt("rating"); // Получаем рейтинг игрока
                    break;
                }
            }

            return rating; // Возвращаем рейтинг или -1, если игрок не найден
        } catch (SQLException e) {
            throw new RatingException("Problem getting rating", e);
        }
    }

}