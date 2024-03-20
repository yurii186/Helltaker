package main.sk.tuke.gamestudio.game.core.gamecore;

import main.sk.tuke.gamestudio.game.core.db.ScoreUpdater;
import main.sk.tuke.gamestudio.game.core.observer.GameObserver;
import main.sk.tuke.gamestudio.game.core.utils.UserInterface;
import main.sk.tuke.gamestudio.game.ui.Helltaker;
import main.sk.tuke.gamestudio.game.core.utils.GameTimer;
import main.sk.tuke.gamestudio.game.ui.MainMenu;
import main.sk.tuke.gamestudio.entity.Score;
import main.sk.tuke.gamestudio.service.ScoreServiceJDBC;

import java.util.Date;
import java.util.*;

public class GameManager {
    private int level = 0;
    private int maxLevel = 8;

    private final UserInterface ui;
    private final GameObserver gameObserver;

    public GameManager(UserInterface ui, GameObserver gameObserver) {
        this.ui = ui;
        this.gameObserver = gameObserver;
    }


    public void startGame() {

        GameplayProcessor gameplayProcessor = new GameplayProcessor();
        // ConsoleOutputObserver observer = new ConsoleOutputObserver();
        gameplayProcessor.setGameObserver(gameObserver);

        Helltaker.musicPlayer.startMusic(true);

        Score score = new Score(null, null, 0, null);
        score.setGame(Helltaker.game);
        Helltaker helltaker = new Helltaker();
        score.setPlayer(helltaker.getName());
        score.setPlayedOn(new Date());



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
            score.setPoints(gameTimer.getScore() + score.getPoints());
            gameTimer.stopTimer();
            goToNextLevel();
        }
        ScoreUpdater scoreUpdater = new ScoreUpdater();
        if(Helltaker.name != null)
            scoreUpdater.addOrUpdateScore(score);


        ui.onGameEnd(score.getPoints());

        endGame(score);

    }

    private void endGame(Score score) {
        // Отображение лучших игроков
        ScoreServiceJDBC topPlayers = new ScoreServiceJDBC();
        List<Score> topPlayersList = topPlayers.getTopScores(score.getGame());
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
        MainMenu mainMenu = new MainMenu();
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

