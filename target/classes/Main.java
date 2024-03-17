package main.sk.tuke.gamestudio;

import main.sk.tuke.gamestudio.game.core.gamecore.Display;
import main.sk.tuke.gamestudio.game.ui.Helltaker;

public class Main {
    public static void main(String[] args) {
        Display display = new Display();
        display.clearScreen();

        Helltaker.userCheck();

    }
}