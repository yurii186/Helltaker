package main.sk.tuke.gamestudio.game.ui;

import main.sk.tuke.gamestudio.game.core.utils.UserInterface;

import java.util.Scanner;

public class ConsoleUI implements UserInterface {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void displayMessage(String message) {
        System.out.println(message);
    }

    @Override
    public String requestInput() {
        return scanner.nextLine();
    }

    @Override
    public void onGameEnd(int score) {
        System.out.println("Game Over! Your score: " + score);
    }
}