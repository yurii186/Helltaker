package test.sk.tuke.gamestudio.service;

import main.sk.tuke.gamestudio.service.ScoreException;
import main.sk.tuke.gamestudio.service.ScoreServiceJDBC;
import main.sk.tuke.gamestudio.entity.Score;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ScoreServiceJDBCTest {
    private ScoreServiceJDBC service;

    @BeforeEach
    void setUp() {
        service = new ScoreServiceJDBC();
        // Очистите таблицу перед каждым тестом
        service.reset();
    }

    @AfterEach
    void tearDown() {
        // Очистите таблицу после выполнения каждого теста
        service.reset();
    }

    @Test
    void addAndGetTopScores() {
        Score score1 = new Score("testGame", "testPlayer1", 123, new Timestamp(new Date().getTime()));
        Score score2 = new Score("testGame", "testPlayer2", 456, new Timestamp(new Date().getTime()));
        service.addScore(score1);
        service.addScore(score2);

        List<Score> scores = service.getTopScores("testGame");
        assertFalse(scores.isEmpty(), "Score list should not be empty.");
        assertEquals(2, scores.size(), "Score list should contain two entries.");
        assertEquals("testPlayer2", scores.get(0).getPlayer(), "The first player should have the highest score.");
        assertEquals(456, scores.get(0).getPoints(), "The score should match.");
    }

    @Test
    void resetScores() {
        Score score = new Score("testGame", "testPlayer", 123, new Timestamp(new Date().getTime()));
        service.addScore(score);

        // Проверяем, что очки были добавлены
        List<Score> scores = service.getTopScores("testGame");
        assertFalse(scores.isEmpty(), "Score list should not be empty before reset.");

        // Очищаем таблицу
        service.reset();

        // Проверяем, что таблица пуста
        scores = service.getTopScores("testGame");
        assertTrue(scores.isEmpty(), "Score list should be empty after reset.");
    }
}

