package main.sk.tuke.gamestudio.server.webservice.game;

import main.sk.tuke.gamestudio.game.helltaker.gamecore.GameplayProcessorWeb;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/game")
public class GameController {

    private final GameplayProcessorWeb gameplayProcessorWeb;

    @Autowired
    public GameController(GameplayProcessorWeb gameplayProcessorWeb) {
        this.gameplayProcessorWeb = gameplayProcessorWeb;
    }

    @GetMapping("/start")
    public ResponseEntity<String[][]> getGameBoard() {
        String[][] gameBoard = gameplayProcessorWeb.getGameBoard();
        return ResponseEntity.ok(gameBoard); // Возвращает игровое поле клиенту
    }

    @PostMapping("/move")
    public ResponseEntity<Integer> play(@RequestBody GameMoveRequest request) {
        int steps = gameplayProcessorWeb.game(request.getLevel(), request.getInput());
        return ResponseEntity.ok(steps);
    }

    @GetMapping("/map")
    public ResponseEntity<String[][]> getMap() {
        String[][] map = gameplayProcessorWeb.getMap();
        return ResponseEntity.ok(map); // Возвращает карту игрового мира клиенту
    }

    @GetMapping("/animation")
    public ResponseEntity<Integer> getAnimation() {
        int animation = gameplayProcessorWeb.getAnimation();
        return ResponseEntity.ok(animation); // Возвращает карту игрового мира клиенту
    }

    @GetMapping("/heroX")
    public ResponseEntity<Integer> getHeroX() {
        int heroX = gameplayProcessorWeb.getHeroX();
        return ResponseEntity.ok(heroX); // Возвращает карту игрового мира клиенту
    }

    @GetMapping("/heroY")
    public ResponseEntity<Integer> getHeroY() {
        int heroY = gameplayProcessorWeb.getHeroY();
        return ResponseEntity.ok(heroY); // Возвращает карту игрового мира клиенту
    }

    @GetMapping("/steps")
    public ResponseEntity<Integer> getSteps() {
        int steps = gameplayProcessorWeb.getSteps();
        return ResponseEntity.ok(steps); // Возвращает карту игрового мира клиенту
    }

    @GetMapping("/level1")
    public ResponseEntity<String[][]> getLevel1() {
        String[][] gameBoard = gameplayProcessorWeb.getLevel1();
        return ResponseEntity.ok(gameBoard); // Возвращает игровое поле клиенту
    }

    @GetMapping("/level2")
    public ResponseEntity<String[][]> getLevel2() {
        String[][] gameBoard = gameplayProcessorWeb.getLevel2();
        return ResponseEntity.ok(gameBoard); // Возвращает игровое поле клиенту
    }

    @GetMapping("/level3")
    public ResponseEntity<String[][]> getLevel3() {
        String[][] gameBoard = gameplayProcessorWeb.getLevel3();
        return ResponseEntity.ok(gameBoard); // Возвращает игровое поле клиенту
    }

    @GetMapping("/level4")
    public ResponseEntity<String[][]> getLevel4() {
        String[][] gameBoard = gameplayProcessorWeb.getLevel4();
        return ResponseEntity.ok(gameBoard); // Возвращает игровое поле клиенту
    }

    @GetMapping("/level5")
    public ResponseEntity<String[][]> getLevel5() {
        String[][] gameBoard = gameplayProcessorWeb.getLevel5();
        return ResponseEntity.ok(gameBoard); // Возвращает игровое поле клиенту
    }

    @GetMapping("/level6")
    public ResponseEntity<String[][]> getLevel6() {
        String[][] gameBoard = gameplayProcessorWeb.getLevel6();
        return ResponseEntity.ok(gameBoard); // Возвращает игровое поле клиенту
    }

    @GetMapping("/level7")
    public ResponseEntity<String[][]> getLevel7() {
        String[][] gameBoard = gameplayProcessorWeb.getLevel7();
        return ResponseEntity.ok(gameBoard); // Возвращает игровое поле клиенту
    }

    @GetMapping("/hidden")
    public ResponseEntity<Boolean> getHiddenSpike() {
        Boolean hidden = gameplayProcessorWeb.getHideSpike();
        return ResponseEntity.ok(hidden);
    }
}

// Вспомогательный класс для принятия данных запроса
class GameMoveRequest {
    private String[][] gameBoard;
    private int level;
    private String input;

    // Getters and Setters
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }
}
