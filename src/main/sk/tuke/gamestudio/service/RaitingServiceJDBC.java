package main.sk.tuke.gamestudio.service;

import main.sk.tuke.gamestudio.entity.Rating;
import org.springframework.stereotype.Component;

import java.sql.*;

@Component
public class RaitingServiceJDBC implements RatingService {
    public static final String URL = "jdbc:postgresql://localhost/gamestudio";
    public static final String USER = "postgres";
    public static final String PASSWORD = "postgres";
    private static final String INSERT_OR_UPDATE =
            "WITH upsert AS (UPDATE rating SET rating = ?, ratedon = ? WHERE game = ? AND player = ? RETURNING *) " +
                    "INSERT INTO rating (player, game, rating, ratedon) " +
                    "SELECT ?, ?, ?, ? WHERE NOT EXISTS (SELECT * FROM upsert)";
    public static final String SELECT = "SELECT player, rating FROM rating WHERE game = ?";

    @Override
    public void setRating(Rating rating) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(INSERT_OR_UPDATE)
        ) {
            statement.setInt(1, rating.getRating());
            statement.setTimestamp(2, new Timestamp(rating.getRatedOn().getTime()));
            statement.setString(3, rating.getGame());
            statement.setString(4, rating.getPlayer());
            // Повторяем значения для INSERT части
            statement.setString(5, rating.getPlayer());
            statement.setString(6, rating.getGame());
            statement.setInt(7, rating.getRating());
            statement.setTimestamp(8, new Timestamp(rating.getRatedOn().getTime()));

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Problem setting or updating rating", e);
        }
    }

    @Override
    public int getAverageRating(String game) {
        double averageRating = 0.0;
        int count = 0;

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
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