package main.sk.tuke.gamestudio.service;

import main.sk.tuke.gamestudio.entity.Rating;
import main.sk.tuke.gamestudio.entity.Score;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;

@Transactional
public class ScoreServiceJPA implements ScoreService {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addScore(Score newScore) {
        // Пытаемся получить существующий счет для данного игрока и игры
        Score existingScore = entityManager.createQuery("SELECT r FROM Score r WHERE r.game = :game AND r.player = :player", Score.class)
                .setParameter("game", newScore.getGame())
                .setParameter("player", newScore.getPlayer())
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null);

        // Если счет существует и новый счет выше, то обновляем его
        if (existingScore != null && newScore.getPoints() > existingScore.getPoints()) {
            existingScore.setPoints(newScore.getPoints());
            existingScore.setPlayedOn(newScore.getPlayedOn());
            existingScore.setTime(newScore.getTime());
            entityManager.merge(existingScore); // Обновляем существующий счет
        } else if (existingScore == null) {
            entityManager.persist(newScore); // Сохраняем новый счет, если такого игрока нет
        }
    }

    @Override
    public List<Score> getTopScores(String game) {
        return entityManager.createQuery("SELECT s FROM Score s WHERE s.game = :game ORDER BY s.points DESC", Score.class)
                .setParameter("game", game)
                .setMaxResults(10)
                .getResultList();
    }

    @Override
    public void reset() {
        entityManager.createNativeQuery("delete from score").executeUpdate();
    }
}
