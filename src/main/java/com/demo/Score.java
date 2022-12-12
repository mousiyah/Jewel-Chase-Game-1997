package com.demo;

/**
* Score of the Level.
* @author Muslima Karimova 2130288
* @version 2.0
*/


public class Score {
    private static int score;

    /**
    * Increase score by n.
    * @param n
    */
    public static void increaseScoreBy(int n) {
        score = score + n;
    }

    /**
    * @return score
    */
    public static int getScore() {
        return score;
    }

    /**
    * @param score
    */
    public static void setScore(int score) {
        score = score;
    }
}
