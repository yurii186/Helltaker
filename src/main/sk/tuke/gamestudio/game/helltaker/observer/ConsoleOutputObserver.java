package main.sk.tuke.gamestudio.game.helltaker.observer;

import org.springframework.stereotype.Component;

@Component
public class ConsoleOutputObserver implements GameObserver {
    @Override
    public void onStepsUpdated(int steps) {
        System.out.println(" " + steps);
    }
}