package main.sk.tuke.gamestudio.game.helltaker.db;

//import java.sql.*;
//public class DatabaseConnectionManager {
//    private static final String url = "jdbc:postgresql://localhost:5432/gamestudio";
//    private static final String user = "postgres";
//    private static final String password = "u3Sn!MXR";
//
//    String player = "John";
//    String game = "Chess";
//    int points = 50;
//    String playedon = "2024-03-01 10:00:00";
//    Timestamp timestamp = Timestamp.valueOf(playedon);
//
//    public void test() {
//        // SQL-запрос INSERT
//        String insertQuery = "INSERT INTO score (player, game, points, playedon) VALUES (?, ?, ?, ?)";
//
//        try (
//                Connection connection = DriverManager.getConnection(url, user, password);
//                PreparedStatement statement = connection.prepareStatement(insertQuery)) {
//
//            // Установка параметров в запросе
//            statement.setString(1, player);
//            statement.setString(2, game);
//            statement.setInt(3, points);
//            statement.setTimestamp(4, timestamp);
//
//
//            // Выполнение запроса
//            statement.executeUpdate();
//
//            System.out.println("Новая запись успешно добавлена в таблицу.");
//
//        } catch (
//                SQLException e) {
//            e.printStackTrace();
//        }
//    }
//}
public class DBConfig {
    private static final String URL = "jdbc:postgresql://localhost:5432/gamestudio";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";

    public static String getUrl() {
        return URL;
    }

    public static String getUser() {
        return USER;
    }

    public static String getPassword() {
        return PASSWORD;
    }
}
