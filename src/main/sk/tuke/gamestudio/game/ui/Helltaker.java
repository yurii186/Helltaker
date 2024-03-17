package main.sk.tuke.gamestudio.game.ui;

import main.sk.tuke.gamestudio.game.core.db.DBConfig;
import main.sk.tuke.gamestudio.game.core.gamecore.Display;
import main.sk.tuke.gamestudio.game.core.utils.MusicPlayer;

import java.sql.*;
import java.util.Scanner;

public class Helltaker {
    public static String name;

    public static String game = "Helltaker";

    public static MusicPlayer musicPlayer = new MusicPlayer();
    public static void startGame() {
        MainMenu mainMenu = new MainMenu();

        musicPlayer.startMusic(false);
        musicPlayer.setMusic(musicPlayer);
        mainMenu.displayMenu(0);
    }

    public String getName(){return name;}

    private static void registerUser(Scanner scanner) {
        System.out.println("Enter username:");
        String username = scanner.nextLine();

        Display display = new Display();

        try (Connection conn = DriverManager.getConnection(DBConfig.getUrl(), DBConfig.getUser(), DBConfig.getPassword())) {
            // Проверяем, существует ли уже пользователь с таким именем
            try (PreparedStatement checkStmt = conn.prepareStatement("SELECT * FROM user_inf WHERE username = ?")) {
                checkStmt.setString(1, username);
                try (ResultSet resultSet = checkStmt.executeQuery()) {
                    if (resultSet.next()) {
                        display.clearScreen();
                        System.out.println("Registration failed: Username '" + username + "' already exists.");
                        return;
                    }
                }
            }

            System.out.println("Enter password:");
            String password = scanner.nextLine();

            // Если имя пользователя уникально, выполняем операцию регистрации
            try (PreparedStatement pstmt = conn.prepareStatement("INSERT INTO user_inf(username, password) VALUES(?, ?)")) {
                pstmt.setString(1, username);
                pstmt.setString(2, password);
                int rowsAffected = pstmt.executeUpdate();
                display.clearScreen();
                if (rowsAffected > 0) {
                    System.out.println("Registered successfully.");
                } else {
                    System.out.println("Registration failed.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Registration failed: " + e.getMessage());
        }

    }

    private static void loginUser(Scanner scanner) {
        System.out.println("Enter username:");
        String username = scanner.nextLine();
        name = username;
        System.out.println("Enter password:");
        String password = scanner.nextLine();

        try (Connection conn = DriverManager.getConnection(DBConfig.getUrl(), DBConfig.getUser(), DBConfig.getPassword());
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM user_inf WHERE username = ? AND password = ?")) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                System.out.println("Login successful.");
                // Add your code here for successful login
            } else {
                Display display = new Display();
                display.clearScreen();
                System.out.println("Login failed: Invalid username or password.");
                Helltaker.userCheck();
            }
        } catch (SQLException e) {
            System.out.println("Login failed: " + e.getMessage());
        }
    }

    public static void userCheck(){

        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome! Choose an option:");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. Continue as guest");

        String choice = scanner.nextLine();
        // scanner.nextLine(); // consume newline

        switch (choice) {
            case "1":
                registerUser(scanner);
                userCheck();
                break;
            case "2":
                loginUser(scanner);
                break;
            case "3":
                // Proceed as a guest
                System.out.println("Continuing as guest...");
                name = null;
                // Add your code here to continue as a guest
                break;
            default:
                Display display = new Display();
                display.clearScreen();

                System.out.println("Invalid option.");
                userCheck();
                break;
        }
        Helltaker.startGame();
    }

}