package test.sk.tuke.gamestudio.game;
import main.sk.tuke.gamestudio.game.helltaker.gamecore.GameplayProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GameplayProcessorTest {

    private GameplayProcessor gameplayProcessor;
    private String[][] gameBoard;

    @BeforeEach
    void setUp() {
       // gameplayProcessor = new GameplayProcessor();
        gameBoard = new String[][]{
                {".", ".", "."},
                {".", ".", "."},
                {".", ".", "."}
        };
    }

    @Test
    void testMoveUp() {
        int startX = 1;
        int startY = 1;
        gameBoard[startX][startY] = "♀"; // Устанавливаем персонажа в центр игрового поля

        // Двигаем персонажа вверх
        gameplayProcessor.move(gameBoard, 0, "w");

        // Проверяем, что персонаж переместился на позицию выше
        assertEquals("♀", gameBoard[startX - 1][startY]);
        assertEquals(".", gameBoard[startX][startY]); // Проверяем, что предыдущая позиция персонажа теперь пустая
    }

    @Test
    void testMoveDown() {
        int startX = 1;
        int startY = 1;
        gameBoard[startX][startY] = "♀"; // Устанавливаем персонажа в центр игрового поля

        // Двигаем персонажа вниз
        gameplayProcessor.move(gameBoard, 0, "s");

        // Проверяем, что персонаж переместился на позицию ниже
        assertEquals("♀", gameBoard[startX + 1][startY]);
        assertEquals(".", gameBoard[startX][startY]); // Проверяем, что предыдущая позиция персонажа теперь пустая
    }

    @Test
    void testMoveLeft() {
        int startX = 1;
        int startY = 1;
        gameBoard[startX][startY] = "♀"; // Устанавливаем персонажа в центр игрового поля

        // Двигаем персонажа влево
        gameplayProcessor.move(gameBoard, 0, "a");

        // Проверяем, что персонаж переместился на позицию слева
        assertEquals("♀", gameBoard[startX][startY - 1]);
        assertEquals(".", gameBoard[startX][startY]); // Проверяем, что предыдущая позиция персонажа теперь пустая
    }

    @Test
    void testMoveRight() {
        int startX = 1;
        int startY = 1;
        gameBoard[startX][startY] = "♀"; // Устанавливаем персонажа в центр игрового поля

        // Двигаем персонажа вправо
        gameplayProcessor.move(gameBoard, 0, "d");

        // Проверяем, что персонаж переместился на позицию справа
        assertEquals("♀", gameBoard[startX][startY + 1]);
        assertEquals(".", gameBoard[startX][startY]); // Проверяем, что предыдущая позиция персонажа теперь пустая
    }

    @Test
    void testInvalidMove() {
        int startX = 1;
        int startY = 1;
        gameBoard[startX][startY] = "♀"; // Устанавливаем персонажа в центр игрового поля

        // Попытка двигаться в несуществующем направлении
        gameplayProcessor.move(gameBoard, 0, "x");

        // Проверяем, что позиция персонажа не изменилась
        assertEquals("♀", gameBoard[startX][startY]);
    }
}
