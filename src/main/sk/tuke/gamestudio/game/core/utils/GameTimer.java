package main.sk.tuke.gamestudio.game.core.utils;

import java.util.Timer;
import java.util.TimerTask;

public class GameTimer {
    private final Timer timer;

    private int secondsPassed;

    public GameTimer() {
        this.timer = new Timer();
        secondsPassed = 0;
    }

    public void startTimer() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                secondsPassed++;
            }
        };
        // Запускаем таймер, с интервалом в 1 секунду (1000 миллисекунд)
        timer.scheduleAtFixedRate(task, 1000, 1000);
    }

    public void stopTimer() {
        timer.cancel();
    }

    public int getScore() {
        int secondsLeft = 100 - (int) ((float) secondsPassed / 60 * 10);
        return Math.max(0, secondsLeft);
    }

}