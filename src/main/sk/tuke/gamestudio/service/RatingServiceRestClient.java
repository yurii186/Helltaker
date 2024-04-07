package main.sk.tuke.gamestudio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import main.sk.tuke.gamestudio.entity.Rating;

public class RatingServiceRestClient implements RatingService {
    private final String url = "http://localhost:8080/api/rating";

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void setRating(Rating rating) {
        restTemplate.postForEntity(url, rating, Rating.class);
    }

    @Override
    public int getAverageRating(String gameName) {
        String avgUrl = url + "/average/" + gameName;
        return restTemplate.getForObject(avgUrl, Integer.class);
    }

    @Override
    public int getRating(String gameName, String playerName) {
        String ratingUrl = url + "/" + gameName + "/" + playerName;
        return restTemplate.getForObject(ratingUrl, Integer.class);
    }
}
