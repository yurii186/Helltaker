package main.sk.tuke.gamestudio.service;

import main.sk.tuke.gamestudio.entity.Rating;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

@Transactional
public class RatingServiceJPA implements RatingService {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void setRating(Rating newRating) {
        // Используем один запрос для поиска существующего рейтинга или возвращаем null, если его нет
        Rating existingRating = entityManager.createQuery("SELECT r FROM Rating r WHERE r.game = :game AND r.player = :player", Rating.class)
                .setParameter("game", newRating.getGame())
                .setParameter("player", newRating.getPlayer())
                .getResultStream()
                .findFirst()
                .orElse(null);

        // Если рейтинг существует, обновляем его
        if (existingRating != null) {
            existingRating.setRating(newRating.getRating());
            existingRating.setRatedOn(new Date()); // Устанавливаем текущую дату и время
        } else {
            // Если рейтинга нет, создаем новый
            entityManager.persist(newRating);
        }
    }


    @Override
    public int getAverageRating(String game) {
        try {
            Double averageRating = entityManager.createQuery("SELECT AVG(r.rating) FROM Rating r WHERE r.game = :game", Double.class)
                    .setParameter("game", game)
                    .getSingleResult();


            // Возвращаем средний рейтинг, округленный до ближайшего целого
            return averageRating != null ? averageRating.intValue() : 0;
        } catch (NoResultException e) {
            // Если рейтингов нет, возвращаем 0
            return 0;
        }
    }

    @Override
    public int getRating(String game, String player) {
        List<Rating> ratings = entityManager.createQuery("select r from Rating r where r.game = :game and r.player = :player", Rating.class)
                .setParameter("game", game)
                .setParameter("player", player)
                .getResultList();

        // Возвращаем рейтинг, если он найден, иначе возвращаем -1
        return ratings.isEmpty() ? -1 : ratings.get(0).getRating();
    }

}
