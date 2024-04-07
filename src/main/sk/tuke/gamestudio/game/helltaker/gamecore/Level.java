package main.sk.tuke.gamestudio.game.helltaker.gamecore;

import main.sk.tuke.gamestudio.game.helltaker.utils.Display;

import java.util.Arrays;

public class Level extends Display {

    boolean hideSpike = false;
    public int displayGameBoardLevel(String[][] gameBoard, int level) {
        // Инициализация игрового поля (здесь просто заполним точками)
        for (String[] strings : gameBoard) {
            Arrays.fill(strings, ".");
        }

        if (level == 0) {
            this.hideSpike = false;
            // Помещение двух объектов в одну клетку
            gameBoard[0][5] = "♀";

            gameBoard[5][6] = "❤";

            gameBoard[0][6] = "□";
            gameBoard[1][6] = "□";
            gameBoard[2][6] = "□";
            gameBoard[0][0] = "□";
            gameBoard[0][1] = "□";
            gameBoard[0][2] = "□";
            gameBoard[0][3] = "□";
            gameBoard[1][0] = "□";
            gameBoard[2][0] = "□";
            gameBoard[3][2] = "□";
            gameBoard[3][3] = "□";
            gameBoard[3][4] = "□";
            gameBoard[3][5] = "□";
            gameBoard[3][6] = "□";
            gameBoard[2][5] = "□";
            gameBoard[4][6] = "□";

            gameBoard[5][1] = "■";
            gameBoard[4][4] = "■";
            gameBoard[5][3] = "■";
            gameBoard[4][1] = "■";

            gameBoard[2][2] = "☠";
            gameBoard[2][4] = "☠";
            gameBoard[1][3] = "☠";
            display(gameBoard);
            return 23;
        }
        else if(level == 1) {
            this.hideSpike = false;

            gameBoard[1][1] = "☠";
            gameBoard[4][5] = "☠";
            gameBoard[5][6] = "☠";

            gameBoard[5][4] = "❤";

            gameBoard[2][1] = "☰";
            gameBoard[1][4] = "☰";
            gameBoard[1][3] = "☰";
            gameBoard[3][5] = "☰";

            gameBoard[2][6] = "■";

            gameBoard[2][4] = "◙";
            gameBoard[2][5] = "◙";

            gameBoard[4][0] = "♀";

            gameBoard[0][0] = "□";
            gameBoard[1][0] = "□";
            gameBoard[5][0] = "□";
            gameBoard[5][1] = "□";
            gameBoard[5][2] = "□";
            gameBoard[5][3] = "□";
            gameBoard[4][3] = "□";
            gameBoard[3][3] = "□";
            gameBoard[2][3] = "□";
            gameBoard[4][2] = "□";
            gameBoard[3][2] = "□";
            gameBoard[2][2] = "□";
            gameBoard[1][2] = "□";
            gameBoard[0][5] = "□";
            gameBoard[0][6] = "□";
            display(gameBoard);
            return 24;
        }
        else if(level == 2){
            this.hideSpike = false;

            gameBoard[5][0] = "⚷";

            gameBoard[1][6] = "☒";

            gameBoard[2][7] = "♀";

            gameBoard[2][4] = "☰";
            gameBoard[2][3] = "☰";
            gameBoard[3][4] = "☰";
            gameBoard[3][2] = "☰";
            gameBoard[5][2] = "☰";
            gameBoard[5][4] = "☰";
            gameBoard[4][5] = "☰";
            gameBoard[4][6] = "☰";

            gameBoard[4][4] = "☠";
            gameBoard[6][5] = "☠";

            gameBoard[1][7] = "□";
            gameBoard[0][7] = "□";
            gameBoard[4][7] = "□";
            gameBoard[5][7] = "□";
            gameBoard[6][7] = "□";
            gameBoard[4][0] = "□";
            gameBoard[3][0] = "□";
            gameBoard[2][0] = "□";
            gameBoard[1][0] = "□";
            gameBoard[0][0] = "□";
            gameBoard[4][1] = "□";
            gameBoard[3][1] = "□";
            gameBoard[2][1] = "□";
            gameBoard[1][1] = "□";
            gameBoard[0][1] = "□";
            gameBoard[1][5] = "□";
            gameBoard[1][4] = "□";
            gameBoard[1][3] = "□";
            gameBoard[1][2] = "□";
            gameBoard[0][2] = "□";
            gameBoard[3][3] = "□";
            gameBoard[3][5] = "□";
            gameBoard[5][3] = "□";
            gameBoard[5][5] = "□";
            gameBoard[5][1] = "□";

            gameBoard[0][3] = "❤";
            gameBoard[0][4] = "❤";
            gameBoard[0][5] = "❤";
            display(gameBoard);
            return 32;
        }
        else if(level == 3){
            this.hideSpike = false;

            gameBoard[0][0] = "♀";

            gameBoard[0][2] = "⚷";

            gameBoard[1][5] = "☒";

            gameBoard[1][2] = "☰";

            gameBoard[1][3] = "◙";

            gameBoard[2][7] = "❤";

            gameBoard[1][1] = "■";
            gameBoard[2][0] = "■";
            gameBoard[3][1] = "■";
            gameBoard[2][2] = "■";
            gameBoard[0][4] = "■";
            gameBoard[2][4] = "■";
            gameBoard[2][5] = "■";
            gameBoard[3][5] = "■";
            gameBoard[3][6] = "■";
            gameBoard[3][3] = "■";
            gameBoard[4][2] = "■";
            gameBoard[4][4] = "■";

            gameBoard[0][1] = "□";
            gameBoard[0][5] = "□";
            gameBoard[1][7] = "□";
            gameBoard[0][6] = "□";
            gameBoard[0][7] = "□";
            gameBoard[4][7] = "□";
            gameBoard[4][0] = "□";
            display(gameBoard);
            return 23;
        }
        else if(level == 4){
            this.hideSpike = true;

            gameBoard[2][0] = "♀";

            gameBoard[4][0] = "☠";

            gameBoard[1][3] = "☒";

            gameBoard[6][5] = "⚷";

            gameBoard[0][4] = "❤";

            gameBoard[5][0] = "_";
            gameBoard[5][2] = "_";
            gameBoard[5][5] = "_";
            gameBoard[3][5] = "_";
            gameBoard[3][3] = "_";
            gameBoard[2][2] = "_";

            gameBoard[4][2] = "■";
            gameBoard[4][3] = "■";
            gameBoard[4][4] = "■";
            gameBoard[4][5] = "■";
            gameBoard[1][4] = "■";
            gameBoard[2][4] = "■";

            gameBoard[6][4] = "□";
            gameBoard[6][3] = "□";
            gameBoard[6][2] = "□";
            gameBoard[6][1] = "□";
            gameBoard[6][0] = "□";
            gameBoard[0][0] = "□";
            gameBoard[1][0] = "□";
            gameBoard[4][1] = "□";
            gameBoard[3][1] = "□";
            gameBoard[2][1] = "□";
            gameBoard[1][1] = "□";
            gameBoard[0][1] = "□";
            gameBoard[0][5] = "□";
            display(gameBoard);
            return 23;
        }
        else if(level == 5){
            this.hideSpike = true;

            gameBoard[0][2] = "♀";

            gameBoard[2][3] = "⚷";

            gameBoard[6][4] = "☒";

            gameBoard[3][2] = "◘";

            gameBoard[3][1] = "_";

            gameBoard[7][5] = "❤";

            gameBoard[4][1] = "☠";
            gameBoard[5][5] = "☠";

            gameBoard[1][1] = "■";
            gameBoard[1][2] = "■";
            gameBoard[1][3] = "■";
            gameBoard[4][3] = "■";
            gameBoard[5][3] = "■";
            gameBoard[4][4] = "■";
            gameBoard[6][5] = "■";

            gameBoard[0][0] = "□";
            gameBoard[1][0] = "□";
            gameBoard[3][0] = "□";
            gameBoard[4][0] = "□";
            gameBoard[5][0] = "□";
            gameBoard[6][0] = "□";
            gameBoard[7][0] = "□";
            gameBoard[6][1] = "□";
            gameBoard[7][1] = "□";
            gameBoard[0][4] = "□";
            gameBoard[0][5] = "□";
            gameBoard[0][6] = "□";
            gameBoard[1][4] = "□";
            gameBoard[1][5] = "□";
            gameBoard[1][6] = "□";
            gameBoard[2][4] = "□";
            gameBoard[2][5] = "□";
            gameBoard[2][6] = "□";
            gameBoard[3][5] = "□";
            gameBoard[3][6] = "□";
            gameBoard[4][2] = "□";
            gameBoard[5][6] = "□";
            gameBoard[7][6] = "□";
            gameBoard[6][2] = "□";
            gameBoard[7][2] = "□";
            gameBoard[6][3] = "□";
            gameBoard[7][3] = "□";

            display(gameBoard);
            return 43;
        }
        else if(level == 6){
            this.hideSpike = true;

            gameBoard[4][5] = "♀";

            gameBoard[2][1] = "⚷";

            gameBoard[1][4] = "☒";

            gameBoard[0][3] = "❤";

            gameBoard[3][0] = "☠";
            gameBoard[3][3] = "☠";
            gameBoard[4][2] = "☠";

            gameBoard[5][3] = "_";
            gameBoard[6][2] = "_";
            gameBoard[6][0] = "_";

            gameBoard[5][0] = "☰";
            gameBoard[6][1] = "☰";
            gameBoard[6][3] = "☰";

            gameBoard[3][1] = "■";
            gameBoard[3][4] = "■";
            gameBoard[2][3] = "■";
            gameBoard[2][4] = "■";
            gameBoard[2][5] = "■";

            gameBoard[0][0] = "□";
            gameBoard[0][1] = "□";
            gameBoard[0][2] = "□";
            gameBoard[1][0] = "□";
            gameBoard[1][1] = "□";
            gameBoard[1][2] = "□";
            gameBoard[2][2] = "□";
            gameBoard[5][5] = "□";
            gameBoard[6][5] = "□";
            gameBoard[0][5] = "□";
            gameBoard[4][1] = "□";
            gameBoard[5][1] = "□";
            gameBoard[5][2] = "□";

            display(gameBoard);
            return 32;
        }
        else if(level == 7){
            this.hideSpike = false;

            gameBoard[6][1] = "♀";

            gameBoard[4][8] = "⚷";

            gameBoard[2][4] = "☒";

            gameBoard[0][4] = "❤";

            gameBoard[2][3] = "■";
            gameBoard[2][5] = "■";
            gameBoard[3][3] = "■";
            gameBoard[4][3] = "■";
            gameBoard[5][3] = "■";
            gameBoard[6][3] = "■";
            gameBoard[4][4] = "■";
            gameBoard[4][5] = "■";
            gameBoard[5][6] = "■";
            gameBoard[5][7] = "■";
            gameBoard[6][6] = "■";
            gameBoard[5][1] = "■";
            gameBoard[5][2] = "■";
            gameBoard[3][1] = "■";
            gameBoard[4][0] = "■";

            gameBoard[6][0] = "□";
            gameBoard[6][8] = "□";
            gameBoard[3][0] = "□";
            gameBoard[3][2] = "□";
            gameBoard[3][8] = "□";
            gameBoard[3][6] = "□";
            gameBoard[2][1] = "□";
            gameBoard[2][7] = "□";
            gameBoard[2][0] = "□";
            gameBoard[2][2] = "□";
            gameBoard[2][6] = "□";
            gameBoard[2][8] = "□";
            gameBoard[0][0] = "□";
            gameBoard[0][1] = "□";
            gameBoard[0][2] = "□";
            gameBoard[0][3] = "□";
            gameBoard[0][5] = "□";
            gameBoard[0][6] = "□";
            gameBoard[0][7] = "□";
            gameBoard[0][8] = "□";
            gameBoard[1][0] = "□";
            gameBoard[1][1] = "□";
            gameBoard[1][2] = "□";
            gameBoard[1][6] = "□";
            gameBoard[1][7] = "□";
            gameBoard[1][8] = "□";

            display(gameBoard);
            return 33;
        }
        return -1;
    }

    public boolean getHideSpike(){
        return this.hideSpike;
    }
}