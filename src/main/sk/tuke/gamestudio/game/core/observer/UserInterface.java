package main.sk.tuke.gamestudio.game.core.observer;

public interface UserInterface {
    void displayMessage(String message);
    String requestInput();
    void onGameEnd(int score);
}