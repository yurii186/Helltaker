package main.sk.tuke.gamestudio.game.helltaker.gamecore;

import main.sk.tuke.gamestudio.game.helltaker.observer.GameObserver;
import main.sk.tuke.gamestudio.game.helltaker.utils.MusicPlayer;
import main.sk.tuke.gamestudio.game.helltaker.utils.UserInterface;
import main.sk.tuke.gamestudio.game.helltaker.ui.Helltaker;
import main.sk.tuke.gamestudio.game.helltaker.utils.GameTimer;
import main.sk.tuke.gamestudio.game.helltaker.ui.MainMenu;
import main.sk.tuke.gamestudio.entity.Score;
import main.sk.tuke.gamestudio.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.*;

@Component
public class GameManager {
    private int level = 0;
    private final int maxLevel = 8;
    private final UserInterface ui;
    private final GameObserver gameObserver;
    private final ScoreService scoreService; // Используем ScoreService вместо ScoreServiceJDBC
    private final Helltaker helltaker;
    private final MusicPlayer musicPlayer;
    private final MainMenu mainMenu;

    @Autowired
    public GameManager(UserInterface ui, GameObserver gameObserver, ScoreService scoreService, MusicPlayer musicPlayer, @Lazy MainMenu mainMenu,@Lazy Helltaker helltaker) {
        this.ui = ui;
        this.gameObserver = gameObserver;
        this.scoreService = scoreService;
        this.helltaker = helltaker;
        this.musicPlayer = musicPlayer;
        this.mainMenu = mainMenu;
    }

    public void startGame() {

        GameplayProcessor gameplayProcessor = new GameplayProcessor(mainMenu, musicPlayer);
        // ConsoleOutputObserver observer = new ConsoleOutputObserver();
        gameplayProcessor.setGameObserver(gameObserver);

        //Helltaker.musicPlayer.startMusic(true);
        musicPlayer.startMusic(true);

        int score = 0;


        List<String[][]> levelsList = new ArrayList<>();
        levelsList.add(new String[6][7]);
        levelsList.add(new String[6][7]);
        levelsList.add(new String[7][8]);
        levelsList.add(new String[5][8]);
        levelsList.add(new String[7][6]);
        levelsList.add((new String[8][7]));
        levelsList.add(new String[7][6]);
        levelsList.add(new String[7][9]);

        // displayMenu(0);

        while (level < maxLevel) {
            GameTimer gameTimer = new GameTimer();
            gameTimer.startTimer();
            gameplayProcessor.game(levelsList.get(level), level, ui);
            score += (gameTimer.getScore());
            gameTimer.stopTimer();
            goToNextLevel();
        }
        // ScoreUpdater scoreUpdater = new ScoreUpdater();
        if(helltaker.getName() != null)
            scoreService.addScore(new Score(helltaker.getGame(), helltaker.getName(), score, new Date()));
           // scoreUpdater.addOrUpdateScore(score);


        ui.onGameEnd(score);

        endGame();

    }

    private void endGame() {
        // Отображение лучших игроков
       // ScoreServiceJDBC topPlayers = new ScoreServiceJDBC();
       // List<Score> topPlayersList = topPlayers.getTopScores(score.getGame());
        List<Score> topPlayersList = scoreService.getTopScores(helltaker.getGame());
        ui.displayMessage("Top 10 Players:");
        for (Score player : topPlayersList) {
            ui.displayMessage(player.toString());
        }

        // Петля выбора после окончания игры
        boolean isValidInput = false;
        while (!isValidInput) {
            ui.displayMessage("\nThanks for the game, author of the game YURII!");
            ui.displayMessage("q - Return to main menu");
            ui.displayMessage("r - Start over");
            ui.displayMessage("e - Exit the game");

            String input = ui.requestInput();

            switch (input.toLowerCase()) {
                case "q":
                    isValidInput = true;
                    // Здесь нужно вызвать метод для возвращения в главное меню
                    returnToMainMenu();
                    break;
                case "r":
                    isValidInput = true;
                    setLevel(0);
                    startGame();
                    break;
                case "e":
                    exitGame();
                    break;
                case "test":
                    isValidInput = true;
                    break;
                default:
                    ui.displayMessage("Invalid input. Please try again.");
                    break;
            }
        }
    }

    private void returnToMainMenu() {
       // MainMenu mainMenu = new MainMenu();
        mainMenu.displayMenu(0);
    }

    private void exitGame() {
        ui.displayMessage("Exiting game...");
        System.exit(0);
    }

    private void goToNextLevel() {
        this.level = level + 1;
    }

    public void setLevel(int currentLevel) {
        this.level = currentLevel;
    }
}

