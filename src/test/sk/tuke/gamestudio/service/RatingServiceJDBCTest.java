package test.sk.tuke.gamestudio.service;

import main.sk.tuke.gamestudio.service.RaitingExceptionJDBC;
import main.sk.tuke.gamestudio.entity.Rating;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class RatingServiceJDBCTest {
    private RaitingExceptionJDBC service = new RaitingExceptionJDBC();

    @BeforeEach
    void setUp() {
        // Для тестов может быть необходимо очистить данные или создать тестовые данные
        // Здесь может понадобиться метод для очистки рейтингов, если таковой существует в вашем сервисе
    }

    @Test
    void setAndGetRating() {
        Rating rating = new Rating("testGame", "testPlayer", 5, new Timestamp(new Date().getTime()));
        service.setRating(rating);

        int retrievedRating = service.getRating("testGame", "testPlayer");
        assertEquals(5, retrievedRating, "Rating should match.");
    }
}
