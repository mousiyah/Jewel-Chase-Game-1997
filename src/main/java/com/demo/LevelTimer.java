package com.demo;

/**
 * LevelTimer is a timer which counts time left to complete the level.
 * @author user
 * @version 2.0
 */
public class LevelTimer {
    private static int timeLeft;
    private static int timeLimit;

    /**
     * Constructs LevelTimer.
     * @param timeLimit integer indicating the time limit for the level
     */
    public LevelTimer(int timeLimit) {
        setTimeLimit(timeLimit);
        setTimeLeft(timeLimit);
    }

    /**
     * @return remaining time
     */
    public static int getTimeLeft() {
        return timeLeft;
    }

    /**
     * @param timeLeft time remaining
     */
    public static void setTimeLeft(int timeLeft) {
        LevelTimer.timeLeft = timeLeft;
    }

    /**
     * @return time
     */
    public static int getTimeLimit() {
        return timeLimit;
    }

    /**
     * @param timeLimit set time limit
     */
    public static void setTimeLimit(int timeLimit) {
        LevelTimer.timeLimit = timeLimit;
    }
}
