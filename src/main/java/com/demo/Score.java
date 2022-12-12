package com.demo;

/**
* 
* @author
* @author
* @version 2.0
*/


public class Score {
    private static int Score;

    /**
    *
    * 
    * @param score_increase
    */
    public static void increaseScoreBy(int n) {
        Score = Score + n;
    }

    /**
    * @return score
    */
    public static int getScore() {
        return Score;
    }

    /**
    * @param score
    */
    public static void setScore(int score) {
        Score = score;
    }
}
