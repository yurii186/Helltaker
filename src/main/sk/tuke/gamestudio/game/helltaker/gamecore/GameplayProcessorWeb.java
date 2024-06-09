package main.sk.tuke.gamestudio.game.helltaker.gamecore;


import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class GameplayProcessorWeb extends LevelWeb {


    private String[][] gameBoard;
    private String[][] gameMap;
    private
    int HeroX = 0;
    int HeroY = 0;
    int steps = 0;
    int animation = 0;

    boolean key = false;

    boolean hideSpike = false;

    private int savedSteps;
    private String[][] savedGameBoard; // Сохраненное состояние игрового поля
    private String[][] savedGameMap;
    private int savedHeroX; // Сохраненная позиция X героя
    private int savedHeroY; // Сохраненная позиция Y героя
    private boolean savedKey; // Сохраненное состояние ключа
    private boolean savedHideSpike; // Сохраненное состояние спайков
    private int savedAnimation;

    public void setSavedGameMap(){
        savedGameMap = Arrays.stream(gameMap)
                .map(String[]::clone)
                .toArray(String[][]::new);
    }
    public void loadSavedGameMap(){
        if (savedGameMap != null) {
            gameMap = Arrays.stream(savedGameMap)
                    .map(String[]::clone)
                    .toArray(String[][]::new);
        }
    }

    // Сохранить текущее состояние игры
    public void saveGameState() {
        savedGameBoard = Arrays.stream(gameBoard)
                .map(String[]::clone)
                .toArray(String[][]::new);
        savedHeroX = HeroX;
        savedHeroY = HeroY;
        savedKey = key;
        savedHideSpike = hideSpike;
        savedAnimation = animation;
        savedSteps = steps;
    }

    // Загрузить сохраненное состояние игры
    public void loadGameState() {
        if (savedGameBoard != null) {
            gameBoard = Arrays.stream(savedGameBoard)
                    .map(String[]::clone)
                    .toArray(String[][]::new);
        }
        HeroX = savedHeroX;
        HeroY = savedHeroY;
        key = savedKey;
        hideSpike = savedHideSpike;
        animation = savedAnimation;
        steps = savedSteps;
    }

    public synchronized int move(int steps, String input) {
        // Scanner scanner = new Scanner(System.in);
        loadGameState();
        System.out.println("To");
        for (String[] row : gameBoard) {
            for (String cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
        hideSpike = getHideSpike();
        if (hideSpike)
            ChangeSpike();
        //    System.out.println("spike = " + hideSpike);

        int x = findHeroesX();
        int y = findHeroesY();
        if (x >= 0)
            HeroX = x;
        else
            x = HeroX;
        if (y >= 0)
            HeroY = y;
        else
            y = HeroY;

        int index = 1;

        //     String input = scanner.nextLine();
        switch (input) {
            case "w":
                setAnimation(-32);
                if (x != 0 && gameBoard[x - 1][y].equals(".")) {
                    Spike(x, y, x - 1, y);
                    setAnimation(-4);
                } else if (x != 1 && x != 0 && (gameBoard[x - 1][y].equals("■") || gameBoard[x - 1][y].equals("◙") || gameBoard[x - 1][y].equals("◘") || gameBoard[x - 1][y].equals("▢"))) {
                    index = index + PushBox(x, y, x - 1, y, x - 2, y);
                } else if (x != 0 && gameBoard[x - 1][y].equals("☠")) {

                    if (x - 2 == -1 || gameBoard[x - 2][y] == null || gameBoard[x - 2][y].equals("□") || gameBoard[x - 2][y].equals("☰") || gameBoard[x - 2][y].equals("■") || gameBoard[x - 2][y].equals("☠")) {
                        gameBoard[x - 1][y] = ".";
                    } else if (gameBoard[x - 2][y].equals(".")) {
                        String enemy = gameBoard[x - 1][y];
                        String array = gameBoard[x - 2][y];
                        gameBoard[x - 2][y] = enemy;
                        gameBoard[x - 1][y] = array;
                    } else if (gameBoard[x - 2][y].equals("_")) {
                        gameBoard[x - 1][y] = ".";
                        gameBoard[x - 2][y] = "†";
                    }

                    if (gameBoard[x][y].equals("☰"))
                        index = index + displayAndUpdateSpike(x, y);
                    else
                        setMap(gameBoard);
                    if(x - 2 == -1 || !gameBoard[x - 2][y].equals("■")) {
                        setAnimation(-8);
                    }
                } else if (x != 0 && (gameBoard[x - 1][y].equals("☰") || gameBoard[x - 1][y].equals("_"))) {
                    index = index + StepSpikeX(x, y, x - 1);
                } else if (x != 0 && gameBoard[x - 1][y].equals("☒")) {
                    KeyLock(x, y, x - 1, y);
                } else if (x != 0 && gameBoard[x - 1][y].equals("⚷")) {
                    Key(x, y, x - 1, y);
                } else {
                    if (hideSpike)
                        ChangeSpike();
       //             display(gameBoard);
                    setMap(gameBoard);
                    return steps;
                }
                break;
            case "s":
                setAnimation(-31);
                if (x != gameBoard.length && x != gameBoard.length - 1 && gameBoard[x + 1][y].equals(".")) {
                    Spike(x, y, x + 1, y);
                    setAnimation(-3);
                    //   System.out.println("ENTER");
                }
                //◘
                else if (x != gameBoard.length && x != (gameBoard.length - 1) && x + 2 != gameBoard.length && (gameBoard[x + 1][y].equals("■") || gameBoard[x + 1][y].equals("◙") || gameBoard[x + 1][y].equals("◘") || gameBoard[x + 1][y].equals("▢"))) {
                    index = index + PushBox(x, y, x + 1, y, x + 2, y);
                } else if (x != gameBoard.length && x != (gameBoard.length - 1) && gameBoard[x + 1][y].equals("☠")) {
                    if (x + 2 == gameBoard.length || gameBoard[x + 2][y] == null || gameBoard[x + 2][y].equals("□") || gameBoard[x + 2][y].equals("☰") || gameBoard[x + 2][y].equals("■") || gameBoard[x + 2][y].equals("☠")) {
                        gameBoard[x + 1][y] = ".";
                    } else if (x + 2 != gameBoard.length && gameBoard[x + 2][y].equals(".")) {
                        String enemy = gameBoard[x + 1][y];
                        String array = gameBoard[x + 2][y];
                        gameBoard[x + 2][y] = enemy;
                        gameBoard[x + 1][y] = array;
                    } else if (gameBoard[x + 2][y].equals("_")) {
                        gameBoard[x + 1][y] = ".";
                        gameBoard[x + 2][y] = "†";
                    }

                    if (gameBoard[x][y].equals("☰"))
                        index = index + displayAndUpdateSpike(x, y);
                    else
                        setMap(gameBoard);

                    setAnimation(-7);
                } else if (x != gameBoard.length && x != gameBoard.length - 1 && (gameBoard[x + 1][y].equals("☰") || gameBoard[x + 1][y].equals("_"))) {
                    index = index + StepSpikeX(x, y, x + 1);
                } else if (x != gameBoard.length && x != gameBoard.length - 1 && gameBoard[x + 1][y].equals("☒")) {
                    KeyLock(x, y, x + 1, y);
                } else if (x != gameBoard.length && x != gameBoard.length - 1 && gameBoard[x + 1][y].equals("⚷")) {
                    Key(x, y, x + 1, y);
                } else {
                    if (hideSpike)
                        ChangeSpike();
                //    display(gameBoard);
                    setMap(gameBoard);
                    return steps;
                }
                //     System.out.println("X = " + x + "Y = " + y);
                break;
            case "a":
                setAnimation(-29);
                if (y != 0 && gameBoard[x][y - 1].equals(".")) {
                    Spike(x, y, x, y - 1);
                    setAnimation(-1);
                } else if (y != 1 && y != 0 && (gameBoard[x][y - 1].equals("■") || gameBoard[x][y - 1].equals("◙") || gameBoard[x][y - 1].equals("◘"))) {
                    index = index + PushBox(x, y, x, y - 1, x, y - 2);

                } else if (y != 1 && y != 0 && gameBoard[x][y - 1].equals("☠")) {
                    String enemy = gameBoard[x][y - 1];
                    String array = gameBoard[x][y - 2];
                    if (gameBoard[x][y - 2] == null || gameBoard[x][y - 2].equals("□") || gameBoard[x][y - 2].equals("☰") || gameBoard[x][y - 2].equals("■") || gameBoard[x][y - 2].equals("☠")) {
                        gameBoard[x][y - 1] = ".";
                    } else if (gameBoard[x][y - 2].equals(".")) {
                        gameBoard[x][y - 2] = enemy;
                        gameBoard[x][y - 1] = array;
                    } else if (gameBoard[x][y + 2].equals("_")) {
                        gameBoard[x][y + 1] = ".";
                        gameBoard[x][y + 2] = "†";
                    }

                    if (gameBoard[x][y].equals("☰"))
                        index = index + displayAndUpdateSpike(x, y);
                    else
                        setMap(gameBoard);
                    setAnimation(-5);
                } else if (y != 0 && (gameBoard[x][y - 1].equals("☰") || gameBoard[x][y - 1].equals("_"))) {
                    index = index + StepSpikeY(x, y, y - 1);
                } else if (y != 0 && gameBoard[x][y - 1].equals("☒")) {
                    KeyLock(x, y, x, y - 1);
                } else if (y != 0 && gameBoard[x][y - 1].equals("⚷")) {
                    Key(x, y, x, y - 1);
                } else {
                    if (hideSpike)
                        ChangeSpike();
             //       display(gameBoard);
                    setMap(gameBoard);
                    return steps;
                }
                // x--;
                break;
            case "d":
                setAnimation(-30);
                if (y != gameBoard[0].length && y != (gameBoard[0].length - 1) && gameBoard[x][y + 1].equals(".")) {
                    Spike(x, y, x, y + 1);
                    setAnimation(-2);
                } else if (y != gameBoard[0].length && y != (gameBoard[0].length - 1) && y + 2 != gameBoard[0].length && (gameBoard[x][y + 1].equals("■") || gameBoard[x][y + 1].equals("◙") || gameBoard[x][y + 1].equals("◘"))) {
                    index = index + PushBox(x, y, x, y + 1, x, y + 2);
                } else if (y != gameBoard[0].length && y != (gameBoard[0].length - 1) && gameBoard[x][y + 1].equals("☠")) {

                    if (y + 2 != gameBoard[0].length && gameBoard[x][y + 2] == null || gameBoard[x][y + 2].equals("□") || gameBoard[x][y + 2].equals("☰") || gameBoard[x][y + 2].equals("■") || gameBoard[x][y + 2].equals("☠")) {
                        gameBoard[x][y + 1] = ".";
                    } else if (gameBoard[x][y + 2].equals(".")) {
                        String enemy = gameBoard[x][y + 1];
                        String array = gameBoard[x][y + 2];
                        gameBoard[x][y + 2] = enemy;
                        gameBoard[x][y + 1] = array;
                    } else if (gameBoard[x][y + 2].equals("_")) {
                        gameBoard[x][y + 1] = ".";
                        gameBoard[x][y + 2] = "†";
                    }

                    if (gameBoard[x][y].equals("☰"))
                        index = index + displayAndUpdateSpike(x, y);
                    else
                        setMap(gameBoard);
                    setAnimation(-6);
                } else if (y != gameBoard[0].length && y != gameBoard[0].length - 1 && (gameBoard[x][y + 1].equals("☰") || gameBoard[x][y + 1].equals("_"))) {
                    index = index + StepSpikeY(x, y, y + 1);
                } else if (y != gameBoard[0].length && y != gameBoard[0].length - 1 && gameBoard[x][y + 1].equals("☒")) {
                    KeyLock(x, y, x, y + 1);
                } else if (y != gameBoard[0].length && y != gameBoard[0].length - 1 && gameBoard[x][y + 1].equals("⚷")) {
                    Key(x, y, x, y + 1);
                } else {
                    if (gameBoard[x][y].equals("_")) {
                        String hero = "♀";
                        gameBoard[x][y] = hero;
           //             display(gameBoard);
                        setMap(gameBoard);
                        gameBoard[x][y] = "_";
                    } else if (gameBoard[x][y].equals("☰")) {
                        String hero = "♀";
                        gameBoard[x][y] = hero;
            //            display(gameBoard);
                        setMap(gameBoard);
                        gameBoard[x][y] = "_";
                        index++;
                    } else {
                        if (hideSpike)
                            ChangeSpike();
           //             display(gameBoard);
                        setMap(gameBoard);
                        return steps;
                    }
                }
                // x++;
                break;
            case "r":
                return -34;
            case "q":
                return -35;
            case "n":
                setAnimation(-33);
                return -33;
            case "e":
                return -36;
            default:
                if (hideSpike)
                    ChangeSpike();
       //         display(gameBoard);
                setMap(gameBoard);
                index = 0;
                setAnimation(0);
                // System.out.println("Error command!");
        }
        return Math.max(steps - index, -1);
    }

    public int findHeroesX() {
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[i].length; j++) {
                if (gameBoard[i][j].equals("♀")) {
                    //  System.out.println("Найден символ 'O' в позиции (" + i + ", " + j + ")");
                    return i;
                }
            }
        }
        return -1;
    }

    public int findHeroesY() {
        for (String[] strings : gameBoard) {
            for (int j = 0; j < strings.length; j++) {
                if (strings[j].equals("♀")) {
                    // System.out.println("Найден символ 'O' в позиции (" + i + ", " + j + ")");
                    return j;
                }
            }
        }
        return -1;
    }

    private int displayAndUpdateSpike(int x, int y) {
        String spike = gameBoard[x][y];
        gameBoard[x][y] = "♀";
    //    display(gameBoard);
        setMap(gameBoard);
        gameBoard[x][y] = spike;
        return 1;
    }


    private void ChangeSpike() {
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[i].length; j++) {
                switch (gameBoard[i][j]) {
                    case "_":
                    case "†":
                        gameBoard[i][j] = "☰";
                        break;
                    case "☰":
                        gameBoard[i][j] = "_";
                        break;
                    case "◘":
                        gameBoard[i][j] = ("◙");
                        break;
                    case "◙":
                        gameBoard[i][j] = ("◘");
                        break;
                }
            }
        }
    }

    private int StepSpikeY(int x, int y, int y1) {
        return stepSpike(x, y, x, y1, 0);
    }

    private int StepSpikeX(int x, int y, int x1) {
        return stepSpike(x, y, x1, y, 1);
    }

    private int stepSpike(int x, int y, int newX, int newY, int axis) {
        String spike = gameBoard[newX][newY];
        String hero;
        if(y > newY)
            setAnimation(-1);
        else if(y < newY)
            setAnimation(-2);
        else if(x < newX)
            setAnimation(-3);
        else if(x > newX)
            setAnimation(-4);
        if (!gameBoard[x][y].equals("☰") && !gameBoard[x][y].equals("_")) {
            hero = gameBoard[x][y];
            if (axis == 1) {
                gameBoard[newX][y] = hero;
                HeroX = newX;
            } else {
                gameBoard[x][newY] = hero;
                HeroY = newY;
            }
            gameBoard[x][y] = ".";
       //     display(gameBoard);
            setMap(gameBoard);
        } else {
            hero = "♀";
            if (axis == 1) {
                gameBoard[newX][y] = hero;
                HeroX = newX;
            } else {
                gameBoard[x][newY] = hero;
                HeroY = newY;
            }
       //     display(gameBoard);
            setMap(gameBoard);
        }
        gameBoard[newX][newY] = spike;
        if (spike.equals("_"))
            return 0;
        return 1;
    }


    private void Spike(int x, int y, int x1, int y1) {
        if (!gameBoard[x][y].equals("☰") && !gameBoard[x][y].equals("_")) {
            String hero = gameBoard[x][y];
            String array = gameBoard[x1][y1];
            gameBoard[x1][y1] = hero;
            gameBoard[x][y] = array;
        } else {
            gameBoard[x1][y1] = "♀";
        }
        //y++;
      //  display(gameBoard);
        setMap(gameBoard);
    }

    private void KeyLock(int x, int y, int x1, int y1) {
        if (key) {
            if(y > y1)
                setAnimation(-1);
            else if(y < y1)
                setAnimation(-2);
            else if(x < x1)
                setAnimation(-3);
            else if(x > x1)
                setAnimation(-4);
            String hero = gameBoard[x][y];
            String array = ".";
            gameBoard[x1][y1] = hero;
            gameBoard[x][y] = array;
            key = false;
        }
        else {
            if (y > y1)
                setAnimation(-17);
            else if (y < y1)
                setAnimation(-18);
            else if (x < x1)
                setAnimation(-19);
            else if (x > x1)
                setAnimation(-20);
        }
      //  display(gameBoard);
        setMap(gameBoard);
    }

    private void Key(int x, int y, int x1, int y1) {
        if(y > y1)
            setAnimation(-1);
        else if(y < y1)
            setAnimation(-2);
        else if(x < x1)
            setAnimation(-3);
        else if(x > x1)
            setAnimation(-4);
        if (gameBoard[x][y].equals("☰") || gameBoard[x][y].equals("_")) {
            gameBoard[x1][y1] = "♀";
        } else {
            String hero = gameBoard[x][y];
            String array = ".";
            gameBoard[x1][y1] = hero;
            gameBoard[x][y] = array;
        }
        key = true;
      //  display(gameBoard);
        setMap(gameBoard);
    }

    private int PushBox(int x, int y, int x1, int y1, int x2, int y2) {
        if(y > y1)
            setAnimation(-17);
        else if(y < y1)
            setAnimation(-18);
        else if(x < x1)
            setAnimation(-19);
        else if(x > x1)
            setAnimation(-20);
        if (!gameBoard[x][y].equals("☰") && !gameBoard[x][y].equals("_")) {
            String box = gameBoard[x1][y1];
            String array = gameBoard[x2][y2];
            switch (gameBoard[x2][y2]) {
                case ".":
                    if(y > y1)
                        setAnimation(-13);
                    else if(y < y1)
                        setAnimation(-14);
                    else if(x < x1)
                        setAnimation(-15);
                    else if(x > x1)
                        setAnimation(-16);

                    switch (gameBoard[x1][y1]) {
                        case "◙":
                            if(y > y1)
                                setAnimation(-21);
                            else if(y < y1)
                                setAnimation(-22);
                            else if(x < x1)
                                setAnimation(-23);
                            else if(x > x1)
                                setAnimation(-24);
                            gameBoard[x2][y2] = "■";
                            gameBoard[x1][y1] = "☰";
                            break;
                        case "◘":
                            gameBoard[x2][y2] = "■";
                            gameBoard[x1][y1] = "_";
                            break;
                        case "▢":
                            gameBoard[x2][y2] = "■";
                            gameBoard[x1][y1] = "⚷";
                            break;
                        default:
                            gameBoard[x2][y2] = box;
                            gameBoard[x1][y1] = array;
                            break;
                    }
                    break;
                case "☰":
                    if(y > y1)
                        setAnimation(-13);
                    else if(y < y1)
                        setAnimation(-14);
                    else if(x < x1)
                        setAnimation(-15);
                    else if(x > x1)
                        setAnimation(-16);
                    if (gameBoard[x1][y1].equals("◙")) {
                        if(y > y1)
                            setAnimation(-21);
                        else if(y < y1)
                            setAnimation(-22);
                        else if(x < x1)
                            setAnimation(-23);
                        else if(x > x1)
                            setAnimation(-24);
                        gameBoard[x1][y1] = array;
                    }
                    else
                        gameBoard[x1][y1] = ".";
                    gameBoard[x2][y2] = "◙";
                    break;
                case "_":
                    if(y > y1)
                        setAnimation(-21);
                    else if(y < y1)
                        setAnimation(-22);
                    else if(x < x1)
                        setAnimation(-23);
                    else if(x > x1)
                        setAnimation(-24);
                    if (gameBoard[x1][y1].equals("◘"))
                        gameBoard[x1][y1] = array;
                    else
                        gameBoard[x1][y1] = ".";
                    gameBoard[x2][y2] = "◘";
                    break;
                //
                case "⚷":
                    gameBoard[x1][y1] = ".";
                    gameBoard[x2][y2] = "▢";
                    break;
            }
          //  display(gameBoard);
            setMap(gameBoard);
            return 0;
        } else {
            //    i++;
            String spikeOrHideSpike = gameBoard[x][y];
            gameBoard[x][y] = "♀";
            String box = gameBoard[x1][y1];
            String array = gameBoard[x2][y2];
            //     if (gameBoard[x + 2][y].equals(".")) {
            if (gameBoard[x1][y1].equals("◙")) {
                if (gameBoard[x2][y2].equals(spikeOrHideSpike)) {
                    if(y > y1)
                        setAnimation(-21);
                    else if(y < y1)
                        setAnimation(-22);
                    else if(x < x1)
                        setAnimation(-23);
                    else if(x > x1)
                        setAnimation(-24);
                    gameBoard[x1][y1] = spikeOrHideSpike;
                    gameBoard[x2][y2] = box;
                } else if (gameBoard[x2][y2].equals(".")) {
                    if(y > y1)
                        setAnimation(-21);
                    else if(y < y1)
                        setAnimation(-22);
                    else if(x < x1)
                        setAnimation(-23);
                    else if(x > x1)
                        setAnimation(-24);
                    gameBoard[x2][y2] = "■";
                    gameBoard[x1][y1] = spikeOrHideSpike;
                }
            } else if (gameBoard[x1][y1].equals("◘")) {
                if (gameBoard[x2][y2].equals(spikeOrHideSpike)) {
                    gameBoard[x1][y1] = spikeOrHideSpike;
                    gameBoard[x2][y2] = box;
                } else if (gameBoard[x2][y2].equals(".")) {
                    gameBoard[x2][y2] = "■";
                    gameBoard[x1][y1] = spikeOrHideSpike;
                }
            } else {
                if (gameBoard[x2][y2].equals(spikeOrHideSpike)) {
                    if(y > y1)
                        setAnimation(-13);
                    else if(y < y1)
                        setAnimation(-14);
                    else if(x < x1)
                        setAnimation(-15);
                    else if(x > x1)
                        setAnimation(-16);
                    if (spikeOrHideSpike.equals("_"))
                        gameBoard[x2][y2] = "◘";
                    else
                        gameBoard[x2][y2] = "◙";
                    gameBoard[x1][y1] = ".";
                } else if (gameBoard[x2][y2].equals(".")) {
                    if(y > y1)
                        setAnimation(-13);
                    else if(y < y1)
                        setAnimation(-14);
                    else if(x < x1)
                        setAnimation(-15);
                    else if(x > x1)
                        setAnimation(-16);
                    gameBoard[x2][y2] = box;
                    gameBoard[x1][y1] = array;
                }
            }

            //   }
           // display(gameBoard);
            setMap(gameBoard);
            gameBoard[x][y] = spikeOrHideSpike;
            if (spikeOrHideSpike.equals("_"))
                return 0;
            return 1;
        }
    }

    public String[][] getGameBoard() {
        gameBoard = new String[6][7];
        steps = displayGameBoardLevel(gameBoard, 0);
        saveGameState();
        return gameBoard;
    }

    public String[][] getLevel1() {
        gameBoard = new String[6][7];
        steps = displayGameBoardLevel(gameBoard, 1);
        saveGameState();
        return gameBoard;
    }

    public String[][] getLevel2() {
        gameBoard = new String[7][8];
        steps = displayGameBoardLevel(gameBoard, 2);
        saveGameState();
        return gameBoard;
    }

    public String[][] getLevel3() {
        gameBoard = new String[5][8];
        steps = displayGameBoardLevel(gameBoard, 3);
        saveGameState();
        return gameBoard;
    }

    public String[][] getLevel4() {
        gameBoard = new String[7][6];
        steps = displayGameBoardLevel(gameBoard, 4);
        saveGameState();
        return gameBoard;
    }

    public String[][] getLevel5() {
        gameBoard = new String[8][7];
        steps = displayGameBoardLevel(gameBoard, 5);
        saveGameState();
        return gameBoard;
    }

    public String[][] getLevel6() {
        gameBoard = new String[7][6];
        steps = displayGameBoardLevel(gameBoard, 6);
        saveGameState();
        return gameBoard;
    }

    public String[][] getLevel7() {
        gameBoard = new String[7][9];
        steps = displayGameBoardLevel(gameBoard, 7);
        saveGameState();
        return gameBoard;
    }

    @Override
    public boolean getHideSpike() {
        return super.getHideSpike();
    }

    public int getSteps() {
        loadGameState();
        return steps;
    }
    public  void setMap(String[][] map) {
        gameMap = map;
        setSavedGameMap();
    }

    public  String[][] getMap() {
        loadSavedGameMap();
        return gameMap;
    }


    public int getAnimation(){
        return animation;
    }

    public void setAnimation(int animation) {
        this.animation = animation;
    }

    public int getHeroX() {
        return HeroX;
    }

    public int getHeroY() {
        return HeroY;
    }


    public int game(int level, String input) {

       // gameBoard = gameBoard1;
        // loadGameState();
        key = false;


            //observer.onStepsUpdated(steps);

//                if(findHeroesX() == -1){
//                    String x = gameBoard[HeroX][HeroY];
//                    gameBoard[HeroX][HeroY] = "♀";
//                    //display(gameBoard);
//                    setMap(gameBoard);
//                    gameBoard[HeroX][HeroY] = x;
//                }

              //  observer.onStepsUpdated(steps);
        steps = move(steps, input);
//                if(findHeroesY() == -1){
//                    gameBoard[HeroX][HeroY] = "♀";
//                    setMap(gameBoard);
//                }
        if (steps == -34 || steps == -1) {
            steps = displayGameBoardLevel(gameBoard, level);
            setMap(gameBoard);
            setAnimation(-34);
        }
        if (checkEnd()) {
            steps = -33;
        }
        saveGameState();
//            if ((steps = move(steps, input)) == -2) {
//                //Helltaker.musicPlayer.getMusic().stopMusic();
//                //MainMenu mainMenu = new MainMenu();
//                //Helltaker.musicPlayer.startMusic(false);
//                return -2; выход в главное меню
//            } else if (steps == -3) {
//                return -3; следующий уровнь
//            }
//            else if(steps == -5){
//                return -5; выход
//            }
        return steps;
    }

    public String[][] getArray(){
        return this.gameBoard;
    }

    private boolean checkDirection(int x, int y, int dx, int dy) {
        int newX = x + dx;
        int newY = y + dy;

        // Проверяем, находится ли новая позиция в пределах игрового поля
        if (newX >= 0 && newX < gameBoard.length && newY >= 0 && newY < gameBoard[0].length) {
            // Проверяем символ в новой позиции
            return gameBoard[newX][newY].equals("❤");
        }

        return false;
    }

    private boolean checkEnd() {
        int x = findHeroesX();
        int y = findHeroesY();

        // Проверяем символы слева, справа, сверху и снизу от текущей позиции
        return checkDirection( x, y, -1, 0) || // Слева
                checkDirection(x, y, 1, 0) ||  // Справа
                checkDirection(x, y, 0, -1) || // Сверху
                checkDirection(x, y, 0, 1);   // Снизу
    }
}
