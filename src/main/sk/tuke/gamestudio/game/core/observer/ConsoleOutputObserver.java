package main.sk.tuke.gamestudio.game.core.observer;

public class ConsoleOutputObserver implements GameObserver {
    @Override
    public void onStepsUpdated(int steps) {
        System.out.println(" " + steps);
    }
}