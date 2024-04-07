package main.sk.tuke.gamestudio.game;

import main.sk.tuke.gamestudio.game.helltaker.utils.Display;
import main.sk.tuke.gamestudio.game.helltaker.gamecore.GameManager;
import main.sk.tuke.gamestudio.game.helltaker.utils.MusicPlayer;
import main.sk.tuke.gamestudio.game.helltaker.ui.MainMenu;
import main.sk.tuke.gamestudio.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import main.sk.tuke.gamestudio.game.helltaker.ui.Helltaker;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.*;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication(scanBasePackages = "main.sk.tuke.gamestudio") // Эта аннотация указывает, что это Spring Boot приложение
@Configuration
@EntityScan("main.sk.tuke.gamestudio.entity")
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX,
        pattern = "main.sk.tuke.gamestudio.server.*"))

public class Main {
    public static void main(String[] args) {
        // Запускаем Spring приложение и получаем контекст
//        ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);

        // Получаем бин Helltaker и вызываем метод userCheck
      //  Helltaker helltaker = context.getBean(Helltaker.class);
      //  helltaker.userCheck();
        //SpringApplication.run(Main.class);
        new SpringApplicationBuilder(Main.class).web(WebApplicationType.NONE).run(args);
    }
    @Bean
    public CommandLineRunner runner(Helltaker helltaker) {
        return args -> helltaker.userCheck();
    }
    @Bean
    public CommentService commentService() {
        //return new CommentServiceJPA();
        return new CommentServiceRestClient();
    }
    @Bean
    public RatingService ratingService(){
        //return new RatingServiceJPA();
        return new RatingServiceRestClient();
    }
    @Bean
    public ScoreService scoreService() {
        //return new ScoreServiceJPA();
        return new ScoreServiceRestClient();
    }
    @Bean
    public Helltaker helltaker(MusicPlayer musicPlayer, MainMenu mainMenu){
        return new Helltaker(musicPlayer, mainMenu);
    }
    @Bean
    public MainMenu mainMenu(RatingService ratingService, CommentService commentService, GameManager gameScene, @Lazy MusicPlayer musicPlayer, Display display, @Lazy Helltaker helltaker){
        return new MainMenu(ratingService, commentService, gameScene, musicPlayer, display, helltaker);
    }
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
