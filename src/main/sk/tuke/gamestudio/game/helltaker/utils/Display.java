package main.sk.tuke.gamestudio.game.helltaker.utils;

//package main.sk.tuke.gamestudio.entity;
public class Display {

    public void display(String[][] gameBoard) {
        clearScreen();

        for (String[] row : gameBoard) {
            for (String cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }

    public void clearScreen() {
        try {
            String operatingSystem = System.getProperty("os.name");
            ProcessBuilder pb;

            if (operatingSystem.contains("Windows")) {
                pb = new ProcessBuilder("cmd", "/c", "cls");
            } else {
                pb = new ProcessBuilder("clear");
            }

            Process startProcess = pb.inheritIO().start();
            startProcess.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
