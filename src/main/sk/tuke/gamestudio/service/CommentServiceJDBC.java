package main.sk.tuke.gamestudio.service;

import main.sk.tuke.gamestudio.entity.Comment;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class CommentServiceJDBC implements CommentService {
    public static final String URL = "jdbc:postgresql://localhost/gamestudio";
    public static final String USER = "postgres";
    public static final String PASSWORD = "postgres";
    public static final String SELECT = "SELECT game, player, comment, commentedOn FROM comment WHERE game = ? ORDER BY commentedOn DESC";
    public static final String DELETE = "DELETE FROM comment";
    public static final String INSERT = "INSERT INTO comment (game, player, comment, commentedOn) VALUES (?, ?, ?, ?)";

    @Override
    public void addComment(Comment comment) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(INSERT)
        ) {
            statement.setString(1, comment.getGame());
            statement.setString(2, comment.getPlayer());
            statement.setString(3, comment.getComment());
            statement.setTimestamp(4, new Timestamp(comment.getCommentedOn().getTime()));

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new CommentException("Problem inserting a message", e);
        }
    }

    @Override
    public List<Comment> getComments(String game) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(SELECT);
        ) {
            statement.setString(1, game);
            try (ResultSet rs = statement.executeQuery()) {
                List<Comment> comments = new ArrayList<>();
                while (rs.next()) {
                    comments.add(new Comment(rs.getString(1), rs.getString(2), rs.getString(3), rs.getTimestamp(4)));
                }
                return comments;
            }
        } catch (SQLException e) {
            throw new CommentException("Problem selecting message", e);
        }
    }
    @Override
    public void reset() {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
        ) {
            statement.executeUpdate(DELETE);
        } catch (SQLException e) {
            throw new CommentException("Problem deleting comment", e);
        }
    }
}