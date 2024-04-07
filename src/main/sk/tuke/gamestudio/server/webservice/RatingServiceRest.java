package main.sk.tuke.gamestudio.server.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import main.sk.tuke.gamestudio.entity.Rating;
import main.sk.tuke.gamestudio.service.RatingService;

@RestController
@RequestMapping("/api/rating")
public class RatingServiceRest {

    @Autowired
    private RatingService ratingService;

    // Установить рейтинг
    @PostMapping
    public void setRating(@RequestBody Rating rating) {
        ratingService.setRating(rating);
    }

    // Получить средний рейтинг
    @GetMapping("/average/{game}")
    public int getAverageRating(@PathVariable String game) {
        return ratingService.getAverageRating(game);
    }

    // Получить рейтинг конкретного пользователя
    @GetMapping("/{game}/{player}")
    public int getRating(@PathVariable String game, @PathVariable String player) {
        return ratingService.getRating(game, player);
    }
}
