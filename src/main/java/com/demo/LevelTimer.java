package com.demo;

public class LevelTimer {
    private static int timeLeft;
    private static int timeLimit;

    public LevelTimer(int timeLimit) {
        setTimeLimit(timeLimit);
        setTimeLeft(timeLimit);
    }

    public static int getTimeLeft() {
        return timeLeft;
    }

    public static void setTimeLeft(int timeLeft) {
        LevelTimer.timeLeft = timeLeft;
    }

    public static int getTimeLimit() {
        return timeLimit;
    }

    public static void setTimeLimit(int timeLimit) {
        LevelTimer.timeLimit = timeLimit;
    }
}
