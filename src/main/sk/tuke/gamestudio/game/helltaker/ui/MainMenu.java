//package main.sk.tuke.gamestudio.game.helltaker.ui;
//
//import main.sk.tuke.gamestudio.game.helltaker.gamecore.GameManager;
//import main.sk.tuke.gamestudio.entity.Comment;
//import main.sk.tuke.gamestudio.game.helltaker.utils.Display;
//import main.sk.tuke.gamestudio.entity.Rating;
//import main.sk.tuke.gamestudio.game.helltaker.observer.ConsoleOutputObserver;
//import main.sk.tuke.gamestudio.game.helltaker.observer.GameObserver;
//import main.sk.tuke.gamestudio.game.helltaker.utils.UserInterface;
//import main.sk.tuke.gamestudio.game.helltaker.utils.MusicPlayer;
//import main.sk.tuke.gamestudio.service.CommentService;
//import main.sk.tuke.gamestudio.service.CommentServiceJDBC;
//import main.sk.tuke.gamestudio.service.RaitingExceptionJDBC;
//import main.sk.tuke.gamestudio.service.RatingService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.Date;
//import java.util.List;
//import java.util.Scanner;
//
//@Component
//public class MainMenu {
//
//    private final RatingService ratingService;
//    private final CommentService commentService;
//    private final GameManager gameScene;
//    private final MusicPlayer musicPlayer;
//
//    @Autowired
//    public MainMenu(RatingService ratingService, CommentService commentService, GameManager gameScene, MusicPlayer musicPlayer) {
//        this.ratingService = ratingService;
//        this.commentService = commentService;
//        this.gameScene = gameScene;
//        this.musicPlayer = musicPlayer;
//    }
//
//
//    Display display = new Display();
//    UserInterface ui = new ConsoleUI();
//    GameObserver observer = new ConsoleOutputObserver();
//    GameManager gameScene = new GameManager(ui, observer);
//    public void displayMenu(int currentOption) {
//
//        boolean isRunning = true;
//        while (isRunning) {
//            display.clearScreen();
//
//            System.out.print("\n* Balzebub, The Great Fly *\n");
//            for (int i = 0; i <= 5; i++) {
//                if (i == currentOption) {
//                    if (i == 0) {
//                        System.out.println("> " + i + ". NEW GAME");
//                    } else if (i == 1) {
//                        System.out.println("> " + i + ". CHAPTER SELECT");
//                    } else if (i == 2) {
//                        System.out.println("> " + i + ". REVIEWS");
//                    } else if (i == 3) {
//                        System.out.println("> " + i + ". RATE THE GAME");
//                    } else if (i == 4) {
//                        System.out.println("> " + i + ". LOGIN MENU");
//                    } else {
//                        System.out.println("> " + i + ". EXIT");
//                    }
//                } else {
//                    if (i == 0) {
//                        System.out.println("  " + i + ". NEW GAME");
//                    } else if (i == 1) {
//                        System.out.println("  " + i + ". CHAPTER SELECT");
//                    } else if (i == 2) {
//                        System.out.println("  " + i + ". REVIEWS");
//                    } else if (i == 3) {
//                        System.out.println("  " + i + ". RATE THE GAME");
//                    } else if (i == 4) {
//                        System.out.println("  " + i + ". LOGIN MENU");
//                    } else {
//                        System.out.println("  " + i + ". EXIT");
//                    }
//                }
//            }
//
//            RaitingExceptionJDBC raitingExceptionJDBC = new RaitingExceptionJDBC();
//
//            System.out.println("                  " + raitingExceptionJDBC.getAverageRating(Helltaker.game));
//
//            Scanner scanner = new Scanner(System.in);
//            String input = scanner.nextLine();
//
//            if (input.equalsIgnoreCase("w")) {
//                currentOption = (currentOption + 5) % 6; // Moving upwards, considering wrap-around
//            } else if (input.equalsIgnoreCase("s")) {
//                currentOption = (currentOption + 1) % 6; // Moving downwards, considering wrap-around
//            } else if (input.isEmpty()) {
//                if (currentOption == 0) {
//                    gameScene.setLevel(0);
//                    Helltaker.musicPlayer.stopMusic();
//                    gameScene.startGame();
//                } else if (currentOption == 1) {
//                    isRunning = false;
//                    LevelSelector(Helltaker.musicPlayer);
//                } else if (currentOption == 2) {
//                    isRunning = false;
//                    Comment comment = new Comment(null, null, null, null);
//
//                    comment.setGame(Helltaker.game);
//                    comment.setPlayer(Helltaker.name);
//                    comment.setCommentedOn(new Date());
//
//                    showComments(comment);
//                } else if (currentOption == 3) {
//                    isRunning = false;
//                    changeRating();
//                    Helltaker.musicPlayer.stopMusic();
//                    displayMenu(0);
//                } else if (currentOption == 4) {
//                    display.clearScreen();
//                    isRunning = false;
//                    Helltaker.userCheck();
//                } else if (currentOption == 5) {
//                    isRunning = false;
//                    System.exit(-1);
//                }
//            }
//        }
//    }
//
//    private void LevelSelector(MusicPlayer musicPlayer) {
//
//        display.clearScreen();
//
//        boolean isRunning = true;
//        while (isRunning) {
//            Scanner scanner = new Scanner(System.in);
//
//            System.out.println("I II III IV V VI VII VIII");
//
//            String choice = scanner.nextLine();
//            // Обрабатываем выбор пользователя
//            switch (choice) {
//                case "1":
//                    gameScene.setLevel(0);
//                    musicPlayer.getMusic().stopMusic();
//                    gameScene.startGame();
//                case "2":
//                    gameScene.setLevel(1);
//                    musicPlayer.getMusic().stopMusic();
//                    gameScene.startGame();
//                case "3":
//                    gameScene.setLevel(2);
//                    musicPlayer.getMusic().stopMusic();
//                    gameScene.startGame();
//                case "4":
//                    gameScene.setLevel(3);
//                    musicPlayer.getMusic().stopMusic();
//                    gameScene.startGame();
//                case "5":
//                    gameScene.setLevel(4);
//                    musicPlayer.getMusic().stopMusic();
//                    gameScene.startGame();
//                case "6":
//                    gameScene.setLevel(5);
//                    musicPlayer.getMusic().stopMusic();
//                    gameScene.startGame();
//                case "7":
//                    gameScene.setLevel(6);
//                    musicPlayer.getMusic().stopMusic();
//                    gameScene.startGame();
//                case "8":
//                    gameScene.setLevel(7);
//                    musicPlayer.getMusic().stopMusic();
//                    gameScene.startGame();
//                case "q":
//                    // musicPlayer.getMusic().stopMusic();
//                    displayMenu(0);
//                    isRunning = false;
//                default:
//                    display.clearScreen();
//                    System.out.println("Incorrect choice. Please select a level number from 1 to 8. (q = Exit for menu)");
//            }
//        }
//    }
//
//    private void showComments(Comment comment) {
//        display.clearScreen();
//        CommentServiceJDBC commentServiceJDBC = new CommentServiceJDBC();
//
//        if (Helltaker.name == null) {
//            System.out.println("To leave a review you need to register");
//        }
//
//        CommentServiceJDBC commentService = new CommentServiceJDBC();
//
//
//        List<Comment> allComments = commentService.getComments(comment.getGame());
//
//        for (Comment comments : allComments) {
//            System.out.println(comments);
//        }
//
//        Scanner scanner = new Scanner(System.in);
//
//        System.out.println("Use a to leave a comment or q to go to the main menu");
//
//        String choice = scanner.nextLine();
//
//        boolean isRunning = true;
//        do {
//            switch (choice) {
//                case "a":
//                    if (Helltaker.name != null) {
//                        System.out.println("Enter your comment:");
//
//                        comment.setComment(scanner.nextLine());
//
//                        commentServiceJDBC.addComment(comment);
//                    }
//
//                    showComments(comment);
//                    isRunning = false;
//                    break;
//                case "q":
//                    displayMenu(0);
//                    isRunning = false;
//                    break;
//                default:
//                    showComments(comment);
//                    System.out.println("unknown command, use a to write a comment or q to exit to the main menu");
//            }
//        } while (isRunning);
//
//    }
//
//    public void changeRating() {
//        if (Helltaker.getName() == null) {
//            System.out.println("To leave a rating you need to register.");
//            return; // Возвращаемся в главное меню, если пользователь не зарегистрирован.
//        }
//
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Enter rating (1-5):");
//        int ratingValue = 0;
//
//        while (scanner.hasNext()) {
//            if (scanner.hasNextInt()) {
//                ratingValue = scanner.nextInt();
//                if (ratingValue >= 1 && ratingValue <= 5) break; // Выходим из цикла, если ввод корректен.
//                else System.out.println("Invalid input. Please enter a number between 1 and 5.");
//            } else {
//                scanner.next(); // Игнорируем некорректный ввод.
//                System.out.println("Invalid input. Please enter a number between 1 and 5.");
//            }
//        }
//
//        // Создаем и устанавливаем новый рейтинг.
//        Rating newRating = new Rating(Helltaker.getGame(), Helltaker.getName(), ratingValue, new Date());
//        ratingService.setRating(newRating);
//
//        System.out.println("Rating updated successfully. Press any key to return to the main menu.");
//
//        try {
//            System.in.read(); // Ждем нажатия клавиши пользователем, прежде чем возвращаться в главное меню.
//        } catch (Exception e) {
//            System.err.println("Error reading input from console.");
//        }
//    }
//}






package main.sk.tuke.gamestudio.game.helltaker.ui;

import main.sk.tuke.gamestudio.entity.Comment;
import main.sk.tuke.gamestudio.entity.Rating;
import main.sk.tuke.gamestudio.game.helltaker.utils.Display;
import main.sk.tuke.gamestudio.game.helltaker.gamecore.GameManager;
import main.sk.tuke.gamestudio.game.helltaker.utils.MusicPlayer;
import main.sk.tuke.gamestudio.service.CommentService;
import main.sk.tuke.gamestudio.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

@Component
public class MainMenu {

    private final RatingService ratingService;
    private final CommentService commentService;
    private final GameManager gameScene;
    private final MusicPlayer musicPlayer;
    private final Display display;
    private final Helltaker helltaker;

    @Autowired
    public MainMenu(RatingService ratingService, CommentService commentService, GameManager gameScene, @Lazy MusicPlayer musicPlayer, Display display, @Lazy Helltaker helltaker) {
        this.ratingService = ratingService;
        this.commentService = commentService;
        this.gameScene = gameScene;
        this.musicPlayer = musicPlayer;
        this.display = display;
        this.helltaker = helltaker;
    }


    public void displayMenu(int currentOption) {
        boolean isRunning = true;
        while (isRunning) {
            display.clearScreen();

            System.out.print("\n* Balzebub, The Great Fly *\n");
            for (int i = 0; i <= 5; i++) {
                if (i == currentOption) {
                    if (i == 0) {
                        System.out.println("> " + i + ". NEW GAME");
                    } else if (i == 1) {
                        System.out.println("> " + i + ". CHAPTER SELECT");
                    } else if (i == 2) {
                        System.out.println("> " + i + ". REVIEWS");
                    } else if (i == 3) {
                        System.out.println("> " + i + ". RATE THE GAME");
                    } else if (i == 4) {
                        System.out.println("> " + i + ". LOGIN MENU");
                    } else {
                        System.out.println("> " + i + ". EXIT");
                    }
                } else {
                    if (i == 0) {
                        System.out.println("  " + i + ". NEW GAME");
                    } else if (i == 1) {
                        System.out.println("  " + i + ". CHAPTER SELECT");
                    } else if (i == 2) {
                        System.out.println("  " + i + ". REVIEWS");
                    } else if (i == 3) {
                        System.out.println("  " + i + ". RATE THE GAME");
                    } else if (i == 4) {
                        System.out.println("  " + i + ". LOGIN MENU");
                    } else {
                        System.out.println("  " + i + ". EXIT");
                    }
                }
            }

            int averageRating = ratingService.getAverageRating("Helltaker");//ratingService.getAverageRating("Helltaker");
            System.out.println("Average rating: " + averageRating);
            // RaitingExceptionJDBC raitingExceptionJDBC = new RaitingExceptionJDBC();

            //
            //  System.out.println("                  " + raitingExceptionJDBC.getAverageRating(Helltaker.game));

            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("w")) {
                currentOption = (currentOption + 5) % 6; // Moving upwards, considering wrap-around
            } else if (input.equalsIgnoreCase("s")) {
                currentOption = (currentOption + 1) % 6; // Moving downwards, considering wrap-around
            } else if (input.isEmpty()) {
                if (currentOption == 0) {
                    gameScene.setLevel(0);
                    musicPlayer.stopMusic();
                    gameScene.startGame();
                } else if (currentOption == 1) {
                    isRunning = false;
                    levelSelector();//LevelSelector(Helltaker.musicPlayer);
                } else if (currentOption == 2) {
                    isRunning = false;
//                    Comment comment = new Comment(null, null, null, null);
//
//                    comment.setGame(Helltaker.game);
//                    comment.setPlayer(helltaker.getName());
//                    comment.setCommentedOn(new Date());

                     showComments(new Comment(helltaker.getGame(), helltaker.getName(), null,new Date()));
                } else if (currentOption == 3) {
                    isRunning = false;
                    changeRating();
                    displayMenu(0);
                } else if (currentOption == 4) {
                    musicPlayer.stopMusic();
                    display.clearScreen();
                    isRunning = false;
                    helltaker.userCheck();
                } else if (currentOption == 5) {
                    isRunning = false;
                    System.exit(-1);
                }
            }
        }
    }

    private void levelSelector() {
        display.clearScreen();
        System.out.println("Select a level (1-8) or 'q' to return to the main menu:");
        System.out.println("I II III IV V VI VII VIII");

        Scanner scanner = new Scanner(System.in);
        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1":
                selectAndStartLevel(0);
                break;
            case "2":
                selectAndStartLevel(1);
                break;
            case "3":
                selectAndStartLevel(2);
                break;
            case "4":
                selectAndStartLevel(3);
                break;
            case "5":
                selectAndStartLevel(4);
                break;
            case "6":
                selectAndStartLevel(5);
                break;
            case "7":
                selectAndStartLevel(6);
                break;
            case "8":
                selectAndStartLevel(7);
                break;
            case "q":
                System.out.println("Returning to main menu...");
                displayMenu(0); // Предполагается, что у вас есть метод displayMenu для возврата в главное меню.
                break;
            default:
                System.out.println("Incorrect choice. Please select a level number from 1 to 8. (q = Exit for menu)");
                levelSelector(); // Рекурсивный вызов для повторного ввода
                break;
        }
    }

    private void selectAndStartLevel(int level) {
        musicPlayer.stopMusic();
        gameScene.setLevel(level);
        gameScene.startGame();
    }

        private void showComments(Comment comment) {
        display.clearScreen();
        //CommentServiceJDBC commentServiceJDBC = new CommentServiceJDBC();

        if (helltaker.getName() == null) {
            System.out.println("To leave a review you need to register");
        }

        //CommentServiceJDBC commentService = new CommentServiceJDBC();


        List<Comment> allComments = commentService.getComments(comment.getGame());

        for (Comment comments : allComments) {
            System.out.println(comments);
        }

        Scanner scanner = new Scanner(System.in);

        System.out.println("Use a to leave a comment or q to go to the main menu");

        String choice = scanner.nextLine();

        boolean isRunning = true;
        do {
            switch (choice) {
                case "a":
                    if (helltaker.getName() != null) {
                        System.out.println("Enter your comment:");

                        comment.setComment(scanner.nextLine());

                        commentService.addComment(comment);
                    }

                    showComments(comment);
                    isRunning = false;
                    break;
                case "q":
                    displayMenu(0);
                    isRunning = false;
                    break;
                default:
                    showComments(comment);
                    System.out.println("unknown command, use a to write a comment or q to exit to the main menu");
            }
        } while (isRunning);

    }

//    private void showComments() {
//        List<Comment> comments = commentService.getComments("Helltaker");
//        for (Comment comment : comments) {
//            System.out.println(comment.toString());
//        }
//    }

    //    private void changeRating() {
//        if (helltaker.getName() != null) {
//            System.out.println("Enter rating (1-5):");
//            Scanner scanner = new Scanner(System.in);
//            int rating = scanner.nextInt();
//            Rating newRating = new Rating(helltaker.getGame(), helltaker.getName(), rating, new Date());
//            ratingService.setRating(newRating);
//        }
//    }
    private void changeRating() {

        if (helltaker.getName() != null) {

            //RaitingExceptionJDBC raitingExceptionJDBC = new RaitingExceptionJDBC();

            //int playerExists = raitingExceptionJDBC.getRating(Helltaker.game, Helltaker.name);
            int playerExists = ratingService.getRating(helltaker.getGame(), helltaker.name);

            Scanner scanner = new Scanner(System.in);

            int ratings = 0;
            boolean validInput = false;

            while (!validInput) {
                try {
                    System.out.println("Enter rating 1-5:");
                    ratings = scanner.nextInt();
                    if (ratings >= 1 && ratings <= 5) {
                        validInput = true;
                    } else {
                        display.clearScreen();
                        System.out.println("Invalid input. Please enter a number between 1 and 5.");
                    }
                } catch (InputMismatchException e) {
                    display.clearScreen();
                    System.out.println("Invalid input. Please enter a number between 1 and 5.");
                    scanner.next(); // Clear the scanner buffer
                }
            }

            if (playerExists == -1) {

                System.out.println("game ="+ helltaker.getGame() + "name =" + helltaker.getName() + " rating =" + ratings);

                ratingService.setRating(new Rating(helltaker.getGame(), helltaker.getName(), ratings, new Date()));

                System.out.println("To exit to the main menu, press any key");

                try {
                    System.in.read();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            } else {

                ratingService.setRating(new Rating(helltaker.getGame(), helltaker.getName(), ratings, new Date()));

                System.out.println("To exit to the main menu, press any key");

                try {
                    System.in.read();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        } else {
            System.out.println("To leave a review you need to register");
            System.out.println("To exit to the main menu, press any key");

            try {
                System.in.read();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}
