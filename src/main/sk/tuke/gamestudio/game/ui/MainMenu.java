package main.sk.tuke.gamestudio.game.ui;

import main.sk.tuke.gamestudio.game.core.gamecore.GameManager;
import main.sk.tuke.gamestudio.entity.Comment;
import main.sk.tuke.gamestudio.game.core.gamecore.Display;
import main.sk.tuke.gamestudio.entity.Rating;
import main.sk.tuke.gamestudio.game.core.observer.ConsoleOutputObserver;
import main.sk.tuke.gamestudio.game.core.observer.GameObserver;
import main.sk.tuke.gamestudio.game.core.observer.UserInterface;
import main.sk.tuke.gamestudio.game.core.utils.MusicPlayer;
import main.sk.tuke.gamestudio.service.CommentServiceJDBC;
import main.sk.tuke.gamestudio.service.RaitingExceptionJDBC;

import java.io.IOException;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class MainMenu {
    Display display = new Display();
    UserInterface ui = new ConsoleUI();
    GameObserver observer = new ConsoleOutputObserver();
    GameManager gameScene = new GameManager(ui, observer);
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
                        System.out.println("> " + i + ". Login menu");
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

            RaitingExceptionJDBC raitingExceptionJDBC = new RaitingExceptionJDBC();

            System.out.println("                  " + raitingExceptionJDBC.getAverageRating(Helltaker.game));

            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("w")) {
                currentOption = (currentOption + 5) % 6; // Moving upwards, considering wrap-around
            } else if (input.equalsIgnoreCase("s")) {
                currentOption = (currentOption + 1) % 6; // Moving downwards, considering wrap-around
            } else if (input.isEmpty()) {
                if (currentOption == 0) {
                    gameScene.setLevel(0);
                    Helltaker.musicPlayer.stopMusic();
                    gameScene.startGame();
                } else if (currentOption == 1) {
                    isRunning = false;
                    LevelSelector(Helltaker.musicPlayer);
                } else if (currentOption == 2) {
                    isRunning = false;
                    Comment comment = new Comment(null, null, null, null);

                    comment.setGame(Helltaker.game);
                    comment.setPlayer(Helltaker.name);
                    comment.setCommentedOn(new Date());

                    showComments(comment);
                } else if (currentOption == 3) {
                    isRunning = false;
                    changeRating();
                    Helltaker.musicPlayer.stopMusic();
                    displayMenu(0);
                } else if (currentOption == 4) {
                    isRunning = false;
                    Helltaker.userCheck();
                } else if (currentOption == 5) {
                    isRunning = false;
                    System.exit(-1);
                }
            }
        }
    }

    private void LevelSelector(MusicPlayer musicPlayer) {

        display.clearScreen();

        boolean isRunning = true;
        while (isRunning) {
            Scanner scanner = new Scanner(System.in);

            System.out.println("I II III IV V VI VII VIII");

            String choice = scanner.nextLine();
            // Обрабатываем выбор пользователя
            switch (choice) {
                case "1":
                    gameScene.setLevel(0);
                    musicPlayer.getMusic().stopMusic();
                    gameScene.startGame();
                case "2":
                    gameScene.setLevel(1);
                    musicPlayer.getMusic().stopMusic();
                    gameScene.startGame();
                case "3":
                    gameScene.setLevel(2);
                    musicPlayer.getMusic().stopMusic();
                    gameScene.startGame();
                case "4":
                    gameScene.setLevel(3);
                    musicPlayer.getMusic().stopMusic();
                    gameScene.startGame();
                case "5":
                    gameScene.setLevel(4);
                    musicPlayer.getMusic().stopMusic();
                    gameScene.startGame();
                case "6":
                    gameScene.setLevel(5);
                    musicPlayer.getMusic().stopMusic();
                    gameScene.startGame();
                case "7":
                    gameScene.setLevel(6);
                    musicPlayer.getMusic().stopMusic();
                    gameScene.startGame();
                case "8":
                    gameScene.setLevel(7);
                    musicPlayer.getMusic().stopMusic();
                    gameScene.startGame();
                case "q":
                    // musicPlayer.getMusic().stopMusic();
                    displayMenu(0);
                    isRunning = false;
                default:
                    display.clearScreen();
                    System.out.println("Incorrect choice. Please select a level number from 1 to 8. (q = Exit for menu)");
            }
        }
    }

    private void showComments(Comment comment) {
        display.clearScreen();
        CommentServiceJDBC commentServiceJDBC = new CommentServiceJDBC();

        if (Helltaker.name == null) {
            System.out.println("To leave a review you need to register");
        }

        CommentServiceJDBC commentService = new CommentServiceJDBC();


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
                    if (Helltaker.name != null) {
                        System.out.println("Enter your comment:");

                        comment.setComment(scanner.nextLine());

                        commentServiceJDBC.addComment(comment);
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

    private void changeRating() {

        if (Helltaker.name != null) {

            RaitingExceptionJDBC raitingExceptionJDBC = new RaitingExceptionJDBC();

            int playerExists = raitingExceptionJDBC.getRating(Helltaker.game, Helltaker.name);

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

            Rating rating = new Rating(null, null, 0, null);

            rating.setGame(Helltaker.game);
            rating.setPlayer(Helltaker.name);
            rating.setRating(ratings);
            rating.setRatedOn(new Date());

            if (playerExists == -1) {

                raitingExceptionJDBC.setRating(rating);

                System.out.println("To exit to the main menu, press any key");

                try {
                    System.in.read();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            } else {

                raitingExceptionJDBC.updateRating(rating);

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