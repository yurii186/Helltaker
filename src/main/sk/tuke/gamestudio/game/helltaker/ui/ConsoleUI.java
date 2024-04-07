package main.sk.tuke.gamestudio.game.helltaker.ui;

import main.sk.tuke.gamestudio.game.helltaker.utils.UserInterface;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
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