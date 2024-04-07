package test.sk.tuke.gamestudio.game;

import main.sk.tuke.gamestudio.game.helltaker.gamecore.Level;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LevelTest {

    @Test
    public void testDisplayGameBoardLevel() {
        Level level = new Level();
        String[][] gameBoard = new String[8][9];
        int result = level.displayGameBoardLevel(gameBoard, 0);
        assertEquals(23, result);

        result = level.displayGameBoardLevel(gameBoard, 1);
        assertEquals(24, result);

        result = level.displayGameBoardLevel(gameBoard, 2);
        assertEquals(32, result);

        result = level.displayGameBoardLevel(gameBoard, 3);
        assertEquals(23, result);

        result = level.displayGameBoardLevel(gameBoard, 4);
        assertEquals(23, result);

        result = level.displayGameBoardLevel(gameBoard, 5);
        assertEquals(43, result);

        result = level.displayGameBoardLevel(gameBoard, 6);
        assertEquals(32, result);

        result = level.displayGameBoardLevel(gameBoard, 7);
        assertEquals(33, result);
    }

    @Test
    public void testGetHideSpike() {
        Level level = new Level();
        assertFalse(level.getHideSpike());
    }
}
