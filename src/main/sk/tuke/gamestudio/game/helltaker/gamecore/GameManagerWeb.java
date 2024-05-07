//package main.sk.tuke.gamestudio.game.helltaker.gamecore;
//
//import main.sk.tuke.gamestudio.game.helltaker.utils.GameTimer;
//import main.sk.tuke.gamestudio.entity.Score;
//import main.sk.tuke.gamestudio.service.ScoreService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.Date;
//import java.util.*;
//
//@Component
//public class GameManagerWeb {
//    private int level = 0;
//    private final int maxLevel = 8;
//    private final ScoreService scoreService; // Используем ScoreService вместо ScoreServiceJDBC
//
//    @Autowired
//    public GameManagerWeb(ScoreService scoreService) {
//        this.scoreService = scoreService;
//    }
//
//    public void startGameWeb() {
//
//        GameplayProcessorWeb gameplayProcessor;
//
//        gameplayProcessor = new GameplayProcessorWeb();
//
//        int score = 0;
//
//        List<String[][]> levelsList = new ArrayList<>();
//        levelsList.add(new String[6][7]);
//        levelsList.add(new String[6][7]);
//        levelsList.add(new String[7][8]);
//        levelsList.add(new String[5][8]);
//        levelsList.add(new String[7][6]);
//        levelsList.add((new String[8][7]));
//        levelsList.add(new String[7][6]);
//        levelsList.add(new String[7][9]);
//
//        // displayMenu(0);
//
//        while (level < maxLevel) {
//            GameTimer gameTimer = new GameTimer();
//            gameTimer.startTimer();
//            gameplayProcessor.game(levelsList.get(level), level, ui);
//            score += (gameTimer.getScore());
//            gameTimer.stopTimer();
//            goToNextLevel();
//        }
//        // ScoreUpdater scoreUpdater = new ScoreUpdater();
//        if(helltaker.getName() != null)
//            scoreService.addScore(new Score(helltaker.getGame(), helltaker.getName(), score, new Date()));
//        // scoreUpdater.addOrUpdateScore(score);
//
//
//    }
//
//    private void goToNextLevel() {
//        this.level = level + 1;
//    }
//
//    public void setLevel(int currentLevel) {
//        this.level = currentLevel;
//    }
//}
//
