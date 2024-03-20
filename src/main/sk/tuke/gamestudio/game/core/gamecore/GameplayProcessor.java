package main.sk.tuke.gamestudio.game.core.gamecore;

import main.sk.tuke.gamestudio.game.core.observer.GameObserver;
import main.sk.tuke.gamestudio.game.core.utils.UserInterface;
import main.sk.tuke.gamestudio.game.ui.Helltaker;
import main.sk.tuke.gamestudio.game.ui.MainMenu;

public class GameplayProcessor extends Level {
    int HeroX = 0;
    int HeroY = 0;

    boolean key = false;

    boolean hideSpike = false;
    private GameObserver observer;

    public void setGameObserver(GameObserver observer) {
        this.observer = observer;
    }

    public int move(String[][] gameBoard, int steps, String input) {
        // Scanner scanner = new Scanner(System.in);

        hideSpike = getHideSpike();
        if (hideSpike)
            ChangeSpike(gameBoard);
        //    System.out.println("spike = " + hideSpike);

        int x = findHeroesX(gameBoard);
        int y = findHeroesY(gameBoard);
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
                if (x != 0 && gameBoard[x - 1][y].equals(".")) {
                    Spike(gameBoard, x, y, x - 1, y);
                } else if (x != 1 && x != 0 && (gameBoard[x - 1][y].equals("■") || gameBoard[x - 1][y].equals("◙") || gameBoard[x - 1][y].equals("◘") || gameBoard[x - 1][y].equals("▢"))) {
                    index = index + PushBox(gameBoard, x, y, x - 1, y, x - 2, y);
                } else if (x != 0 && gameBoard[x - 1][y].equals("☠")) {

                    if (x - 2 == -1 || gameBoard[x - 2][y] == null || gameBoard[x - 2][y].equals("□") || gameBoard[x - 2][y].equals("☰") || gameBoard[x - 2][y].equals("■")) {
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
                        index = index + displayAndUpdateSpike(gameBoard, x, y);
                    else
                        display(gameBoard);
                } else if (x != 0 && (gameBoard[x - 1][y].equals("☰") || gameBoard[x - 1][y].equals("_"))) {
                    index = index + StepSpikeX(gameBoard, x, y, x - 1);
                } else if (x != 0 && gameBoard[x - 1][y].equals("☒")) {
                    KeyLock(gameBoard, x, y, x - 1, y);
                } else if (x != 0 && gameBoard[x - 1][y].equals("⚷")) {
                    Key(gameBoard, x, y, x - 1, y);
                } else {
                    if (hideSpike)
                        ChangeSpike(gameBoard);
                    display(gameBoard);
                    return steps;
                }
                break;
            case "s":
                if (x != gameBoard.length && x != gameBoard.length - 1 && gameBoard[x + 1][y].equals(".")) {
                    Spike(gameBoard, x, y, x + 1, y);
                    //   System.out.println("ENTER");
                }
                //◘
                else if (x != gameBoard.length && x != (gameBoard.length - 1) && x + 2 != gameBoard.length && (gameBoard[x + 1][y].equals("■") || gameBoard[x + 1][y].equals("◙") || gameBoard[x + 1][y].equals("◘") || gameBoard[x + 1][y].equals("▢"))) {
                    index = index + PushBox(gameBoard, x, y, x + 1, y, x + 2, y);
                } else if (x != gameBoard.length && x != (gameBoard.length - 1) && gameBoard[x + 1][y].equals("☠")) {
                    if (x + 2 == gameBoard.length || gameBoard[x + 2][y] == null || gameBoard[x + 2][y].equals("□") || gameBoard[x + 2][y].equals("☰") || gameBoard[x + 2][y].equals("■")) {
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
                        index = index + displayAndUpdateSpike(gameBoard, x, y);
                    else
                        display(gameBoard);

                } else if (x != gameBoard.length && x != gameBoard.length - 1 && (gameBoard[x + 1][y].equals("☰") || gameBoard[x + 1][y].equals("_"))) {
                    index = index + StepSpikeX(gameBoard, x, y, x + 1);
                } else if (x != gameBoard.length && x != gameBoard.length - 1 && gameBoard[x + 1][y].equals("☒")) {
                    KeyLock(gameBoard, x, y, x + 1, y);
                } else if (x != gameBoard.length && x != gameBoard.length - 1 && gameBoard[x + 1][y].equals("⚷")) {
                    Key(gameBoard, x, y, x + 1, y);
                } else {
                    if (hideSpike)
                        ChangeSpike(gameBoard);
                    display(gameBoard);
                    return steps;
                }
                //     System.out.println("X = " + x + "Y = " + y);
                break;
            case "a":
                if (y != 0 && gameBoard[x][y - 1].equals(".")) {
                    Spike(gameBoard, x, y, x, y - 1);
                } else if (y != 1 && y != 0 && (gameBoard[x][y - 1].equals("■") || gameBoard[x][y - 1].equals("◙") || gameBoard[x][y - 1].equals("◘"))) {
                    index = index + PushBox(gameBoard, x, y, x, y - 1, x, y - 2);

                } else if (y != 1 && y != 0 && gameBoard[x][y - 1].equals("☠")) {
                    String enemy = gameBoard[x][y - 1];
                    String array = gameBoard[x][y - 2];
                    if (gameBoard[x][y - 2] == null || gameBoard[x][y - 2].equals("□") || gameBoard[x][y - 2].equals("☰") || gameBoard[x][y - 2].equals("■")) {
                        gameBoard[x][y - 1] = ".";
                    } else if (gameBoard[x][y - 2].equals(".")) {
                        gameBoard[x][y - 2] = enemy;
                        gameBoard[x][y - 1] = array;
                    } else if (gameBoard[x][y + 2].equals("_")) {
                        gameBoard[x][y + 1] = ".";
                        gameBoard[x][y + 2] = "†";
                    }

                    if (gameBoard[x][y].equals("☰"))
                        index = index + displayAndUpdateSpike(gameBoard, x, y);
                    else
                        display(gameBoard);

                } else if (y != 0 && (gameBoard[x][y - 1].equals("☰") || gameBoard[x][y - 1].equals("_"))) {
                    index = index + StepSpikeY(gameBoard, x, y, y - 1);
                } else if (y != 0 && gameBoard[x][y - 1].equals("☒")) {
                    KeyLock(gameBoard, x, y, x, y - 1);
                } else if (y != 0 && gameBoard[x][y - 1].equals("⚷")) {
                    Key(gameBoard, x, y, x, y - 1);
                } else {
                    if (hideSpike)
                        ChangeSpike(gameBoard);
                    display(gameBoard);
                    return steps;
                }
                // x--;
                break;
            case "d":
                if (y != gameBoard[0].length && y != (gameBoard[0].length - 1) && gameBoard[x][y + 1].equals(".")) {
                    Spike(gameBoard, x, y, x, y + 1);
                } else if (y != gameBoard[0].length && y != (gameBoard[0].length - 1) && y + 2 != gameBoard[0].length && (gameBoard[x][y + 1].equals("■") || gameBoard[x][y + 1].equals("◙") || gameBoard[x][y + 1].equals("◘"))) {
                    index = index + PushBox(gameBoard, x, y, x, y + 1, x, y + 2);
                } else if (y != gameBoard[0].length && y != (gameBoard[0].length - 1) && gameBoard[x][y + 1].equals("☠")) {

                    if (y + 2 != gameBoard[0].length && gameBoard[x][y + 2] == null || gameBoard[x][y + 2].equals("□") || gameBoard[x][y + 2].equals("☰") || gameBoard[x][y + 2].equals("■")) {
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
                        index = index + displayAndUpdateSpike(gameBoard, x, y);
                    else
                        display(gameBoard);

                } else if (y != gameBoard[0].length && y != gameBoard[0].length - 1 && (gameBoard[x][y + 1].equals("☰") || gameBoard[x][y + 1].equals("_"))) {
                    index = index + StepSpikeY(gameBoard, x, y, y + 1);
                } else if (y != gameBoard[0].length && y != gameBoard[0].length - 1 && gameBoard[x][y + 1].equals("☒")) {
                    KeyLock(gameBoard, x, y, x, y + 1);
                } else if (y != gameBoard[0].length && y != gameBoard[0].length - 1 && gameBoard[x][y + 1].equals("⚷")) {
                    Key(gameBoard, x, y, x, y + 1);
                } else {
                    if (gameBoard[x][y].equals("_")) {
                        String hero = "♀";
                        gameBoard[x][y] = hero;
                        display(gameBoard);
                        gameBoard[x][y] = "_";
                    } else if (gameBoard[x][y].equals("☰")) {
                        String hero = "♀";
                        gameBoard[x][y] = hero;
                        display(gameBoard);
                        gameBoard[x][y] = "_";
                        index++;
                    } else {
                        if (hideSpike)
                            ChangeSpike(gameBoard);
                        display(gameBoard);
                        return steps;
                    }
                }
                // x++;
                break;
            case "r":
                return -1;
            case "q":
                return -2;
            case "n":
                return -3;
            case "e":
                System.exit(0);
            default:
                if (hideSpike)
                    ChangeSpike(gameBoard);
                display(gameBoard);
                index = 0;
                // System.out.println("Error command!");
        }
        return Math.max(steps - index, -1);
    }

    public int findHeroesX(String[][] gameBoard) {
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

    public int findHeroesY(String[][] gameBoard) {
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

    private int displayAndUpdateSpike(String[][] gameBoard, int x, int y) {
        String spike = gameBoard[x][y];
        gameBoard[x][y] = "♀";
        display(gameBoard);
        gameBoard[x][y] = spike;
        return 1;
    }


    private void ChangeSpike(String[][] gameBoard) {
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

    private int StepSpikeY(String[][] gameBoard, int x, int y, int y1) {
        return stepSpike(gameBoard, x, y, x, y1, 0);
    }

    private int StepSpikeX(String[][] gameBoard, int x, int y, int x1) {
        return stepSpike(gameBoard, x, y, x1, y, 1);
    }

    private int stepSpike(String[][] gameBoard, int x, int y, int newX, int newY, int axis) {
        String spike = gameBoard[newX][newY];
        String hero;
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
            display(gameBoard);
        } else {
            hero = "♀";
            if (axis == 1) {
                gameBoard[newX][y] = hero;
                HeroX = newX;
            } else {
                gameBoard[x][newY] = hero;
                HeroY = newY;
            }
            display(gameBoard);
        }
        gameBoard[newX][newY] = spike;
        if (spike.equals("_"))
            return 0;
        return 1;
    }


    private void Spike(String[][] gameBoard, int x, int y, int x1, int y1) {
        if (!gameBoard[x][y].equals("☰") && !gameBoard[x][y].equals("_")) {
            String hero = gameBoard[x][y];
            String array = gameBoard[x1][y1];
            gameBoard[x1][y1] = hero;
            gameBoard[x][y] = array;
        } else {
            gameBoard[x1][y1] = "♀";
        }
        //y++;
        display(gameBoard);
    }

    private void KeyLock(String[][] gameBoard, int x, int y, int x1, int y1) {
        if (key) {
            String hero = gameBoard[x][y];
            String array = ".";
            gameBoard[x1][y1] = hero;
            gameBoard[x][y] = array;
            key = false;
        }
        display(gameBoard);
    }

    private void Key(String[][] gameBoard, int x, int y, int x1, int y1) {
        if (gameBoard[x][y].equals("☰") || gameBoard[x][y].equals("_")) {
            gameBoard[x1][y1] = "♀";
        } else {
            String hero = gameBoard[x][y];
            String array = ".";
            gameBoard[x1][y1] = hero;
            gameBoard[x][y] = array;
        }
        key = true;
        display(gameBoard);
    }

    private int PushBox(String[][] gameBoard, int x, int y, int x1, int y1, int x2, int y2) {
        if (!gameBoard[x][y].equals("☰") && !gameBoard[x][y].equals("_")) {
            String box = gameBoard[x1][y1];
            String array = gameBoard[x2][y2];
            switch (gameBoard[x2][y2]) {
                case ".":

                    switch (gameBoard[x1][y1]) {
                        case "◙":
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
                    if (gameBoard[x1][y1].equals("◙"))
                        gameBoard[x1][y1] = array;
                    else
                        gameBoard[x1][y1] = ".";
                    gameBoard[x2][y2] = "◙";
                    break;
                case "_":
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
            display(gameBoard);
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
                    gameBoard[x1][y1] = spikeOrHideSpike;
                    gameBoard[x2][y2] = box;
                } else if (gameBoard[x2][y2].equals(".")) {
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
                    if (spikeOrHideSpike.equals("_"))
                        gameBoard[x2][y2] = "◘";
                    else
                        gameBoard[x2][y2] = "◙";
                    gameBoard[x1][y1] = ".";
                } else if (gameBoard[x2][y2].equals(".")) {
                    gameBoard[x2][y2] = box;
                    gameBoard[x1][y1] = array;
                }
            }

            //   }
            display(gameBoard);
            gameBoard[x][y] = spikeOrHideSpike;
            if (spikeOrHideSpike.equals("_"))
                return 0;
            return 1;
        }
    }
    private boolean isValidInput(String input) {
        return input.equals("w") || input.equals("d") || input.equals("a") || input.equals("s") || input.equals("q") || input.equals("n") || input.equals("e") || input.equals("r");
    }
    public void game(String[][] gameBoard, int level, UserInterface ui) {
        key = false;
        int steps = displayGameBoardLevel(gameBoard, level);
        boolean isRunning = true;
        while (isRunning) {

            if (steps == -1)
                steps = displayGameBoardLevel(gameBoard, level);

            if (checkEnd(gameBoard)) {
                break;
            }

            observer.onStepsUpdated(steps);

            String input;
            do {
                input = ui.requestInput();
                if(findHeroesX(gameBoard) == -1){
                    String x = gameBoard[HeroX][HeroY];
                    gameBoard[HeroX][HeroY] = "♀";
                    display(gameBoard);
                    gameBoard[HeroX][HeroY] = x;
                }
                else {
                    display(gameBoard);
                }
                observer.onStepsUpdated(steps);
            } while (!isValidInput(input));

            if ((steps = move(gameBoard, steps, input)) == -2) {
                Helltaker.musicPlayer.getMusic().stopMusic();
                MainMenu mainMenu = new MainMenu();
                Helltaker.musicPlayer.startMusic(false);
                mainMenu.displayMenu(0);
                isRunning = false;
            } else if (steps == -3) {
                isRunning = false;
            }
        }
    }

    private boolean checkDirection(String[][] gameboard, int x, int y, int dx, int dy) {
        int newX = x + dx;
        int newY = y + dy;

        // Проверяем, находится ли новая позиция в пределах игрового поля
        if (newX >= 0 && newX < gameboard.length && newY >= 0 && newY < gameboard[0].length) {
            // Проверяем символ в новой позиции
            return gameboard[newX][newY].equals("❤");
        }

        return false;
    }

    private boolean checkEnd(String[][] gameboard) {
        int x = findHeroesX(gameboard);
        int y = findHeroesY(gameboard);

        // Проверяем символы слева, справа, сверху и снизу от текущей позиции
        return checkDirection(gameboard, x, y, -1, 0) || // Слева
                checkDirection(gameboard, x, y, 1, 0) ||  // Справа
                checkDirection(gameboard, x, y, 0, -1) || // Сверху
                checkDirection(gameboard, x, y, 0, 1);   // Снизу
    }
}
