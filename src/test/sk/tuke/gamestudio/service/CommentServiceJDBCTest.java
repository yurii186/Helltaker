package test.sk.tuke.gamestudio.service;

import main.sk.tuke.gamestudio.service.CommentServiceJDBC;
import main.sk.tuke.gamestudio.entity.Comment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List; // Необходимо для использования List

import static org.junit.jupiter.api.Assertions.*;

class CommentServiceJDBCTest {
    private CommentServiceJDBC service = new CommentServiceJDBC();

    @BeforeEach
    void setUp() {
        // Очистка таблицы комментариев перед каждым тестом.
        // Это предотвратит накопление тестовых данных и обеспечит независимость тестов.
        service.reset();
    }

    @Test
    void addAndGetComments() {
        Comment comment = new Comment("testGame", "testPlayer", "Great game!", new Timestamp(new Date().getTime()));
        service.addComment(comment);

        List<Comment> comments = service.getComments("testGame"); // Явное объявление типа List
        assertFalse(comments.isEmpty(), "Comments list should not be empty.");
        assertEquals("Great game!", comments.get(0).getComment(), "Comment text should match.");
    }
}

