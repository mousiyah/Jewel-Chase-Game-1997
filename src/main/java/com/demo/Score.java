package com.demo;

public class Score {
    private static int Score;

    public static void increaseScoreBy(int n) {
        Score = Score + n;
    }

    public static int getScore() {
        return Score;
    }

    public static void setScore(int score) {
        Score = score;
    }
}
