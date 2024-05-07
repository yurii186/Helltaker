package main.sk.tuke.gamestudio.game.helltaker.ui;

import main.sk.tuke.gamestudio.game.helltaker.utils.Display;
import main.sk.tuke.gamestudio.game.helltaker.utils.MusicPlayer;
import main.sk.tuke.gamestudio.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.Scanner;

@Component
public class Helltaker {
    public String name;

    public static String game = "Helltaker";

    private final MusicPlayer musicPlayer;
    private final MainMenu mainMenu;
    private final UserService userService;
    //public static MusicPlayer musicPlayer = new MusicPlayer();

    @Autowired
    public Helltaker(MusicPlayer musicPlayer, MainMenu mainMenu, UserService userService) {
        this.musicPlayer = musicPlayer;
        this.mainMenu = mainMenu;
        this.userService = userService;
    }

    public void startGame() {
      //  MainMenu mainMenu = new MainMenu();

        musicPlayer.startMusic(false);
        musicPlayer.setMusic(musicPlayer);
        mainMenu.displayMenu(0);
    }

    public String getName(){return name;}
    private void setName(String name){this.name = name;}

    public String getGame(){return game;}

    private void registerUser(Scanner scanner) {
        System.out.println("Enter username:");
        String username = scanner.nextLine();

        Display display = new Display();

  //      try (Connection conn = DriverManager.getConnection(DBConfig.getUrl(), DBConfig.getUser(), DBConfig.getPassword())) {
            // Проверяем, существует ли уже пользователь с таким именем
//            try (PreparedStatement checkStmt = conn.prepareStatement("SELECT * FROM user_inf WHERE username = ?")) {
//                checkStmt.setString(1, username);
//                try (ResultSet resultSet = checkStmt.executeQuery()) {
//                    if (resultSet.next()) {
//                        display.clearScreen();
//                        System.out.println("Registration failed: Username '" + username + "' already exists.");
//                        return;
//                    }
//                }
//            }

            if(userService.doesUsernameExist(username) == 1){
                display.clearScreen();
                System.out.println("Registration failed: Username '" + username + "' already exists.");
                return;
            }
            else if(userService.doesUsernameExist(username) == -1){
                display.clearScreen();
                System.out.println("Registration failed: Username must be at least 3 characters.");
                return;
            }

            System.out.println("Enter password:");
            String password = scanner.nextLine();

            display.clearScreen();

            if(userService.register(username, password))
                System.out.println("Registration successful.");
            else
                System.out.println("Registration failed: password must be at least 3 characters.");
//            // Если имя пользователя уникально, выполняем операцию регистрации
//            try (PreparedStatement pstmt = conn.prepareStatement("INSERT INTO user_inf(username, password) VALUES(?, ?)")) {
//                pstmt.setString(1, username);
//                pstmt.setString(2, password);
//                int rowsAffected = pstmt.executeUpdate();
//                display.clearScreen();
//                if (rowsAffected > 0) {
//                    System.out.println("Registered successfully.");
//                } else {
//                    System.out.println("Registration failed.");
//                }
//            }
//        } catch (SQLException e) {
//            System.out.println("Registration failed: " + e.getMessage());
//        }

    }

    private void loginUser(Scanner scanner) {
        Display display = new Display();
        System.out.println("Enter username:");
        String username = scanner.nextLine();
//        if (username.isEmpty()) {
//            display.clearScreen();
//            System.out.println("Username cannot be empty.");
//            userCheck();
//        }
        display.clearScreen();
        if(userService.doesUsernameExist(username) == -1){
            System.out.println("Username must be at least 3 characters.");
            userCheck();
        }
        else if(userService.doesUsernameExist(username) == 0){
            System.out.println("Username '" + username + "' does not exist.");
            userCheck();
        }
         setName(username);
        System.out.println("Enter password:");
        String password = scanner.nextLine();
        if(userService.login(username, password))
            System.out.println("Login successful.");
        else {
            System.out.println("Login failed: Invalid username or password.");
            userCheck();
        }

//        try (Connection conn = DriverManager.getConnection(DBConfig.getUrl(), DBConfig.getUser(), DBConfig.getPassword());
//             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM user_inf WHERE username = ? AND password = ?")) {
//            pstmt.setString(1, username);
//            pstmt.setString(2, password);
//
//            ResultSet rs = pstmt.executeQuery();
//            if (rs.next()) {
//                System.out.println("Login successful.");
//                // Add your code here for successful login
//            } else {
//                display.clearScreen();
//                System.out.println("Login failed: Invalid username or password.");
//                userCheck();
//            }
//        } catch (SQLException e) {
//            System.out.println("Login failed: " + e.getMessage());
//        }
    }

    public void userCheck(){
        //ratingService.getAverageRating("Helltaker");
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
                setName(null);
                // Add your code here to continue as a guest
                break;
            default:
                Display display = new Display();
                display.clearScreen();

                System.out.println("Invalid option.");
                userCheck();
                break;
        }
        startGame();
    }
}
