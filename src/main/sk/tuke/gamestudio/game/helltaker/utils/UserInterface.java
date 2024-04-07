package main.sk.tuke.gamestudio.game.helltaker.utils;

public interface UserInterface {
    void displayMessage(String message);
    String requestInput();
    void onGameEnd(int score);
}