package com.demo;

public class Time {
    private static int timeLeft;
    private static int timeLimit;

    public Time(int timeLimit) {
        setTimeLimit(timeLimit);
        setTimeLeft(timeLimit);
    }

    public static int getTimeLeft() {
        return timeLeft;
    }

    public static void setTimeLeft(int timeLeft) {
        Time.timeLeft = timeLeft;
    }

    public static int getTimeLimit() {
        return timeLimit;
    }

    public static void setTimeLimit(int timeLimit) {
        Time.timeLimit = timeLimit;
    }
}
