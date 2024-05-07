package main.sk.tuke.gamestudio.server;

import main.sk.tuke.gamestudio.game.helltaker.gamecore.GameplayProcessorWeb;
import main.sk.tuke.gamestudio.service.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
@EntityScan({"main.sk.tuke.gamestudio.entity", "main.sk.tuke.gamestudio.game"})
//@ComponentScan("main.sk.tuke.gamestudio.game.helltaker.db")
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
    @Bean
    public UserService userService() {
        return new UserServiceJPA();
    }
    @Bean
    public GameplayProcessorWeb gameplayProcessorWeb() {
        return new GameplayProcessorWeb();
    }

}