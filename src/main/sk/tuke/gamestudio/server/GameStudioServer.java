package main.sk.tuke.gamestudio.server;

import main.sk.tuke.gamestudio.service.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
@EntityScan("main.sk.tuke.gamestudio.entity")
public class GameStudioServer {
    public static void main(String[] args) {
        SpringApplication.run(GameStudioServer.class, args);
    }

    @Bean
    public ScoreService scoreService() {
        return new ScoreServiceJPA();
    }
    @Bean
    public CommentService commentService() {
        return new CommentServiceJPA();
    }
    @Bean
    public RatingService ratingService() {
        return new RatingServiceJPA();
    }
}