package com.demo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Score {
    private static int score;
    private LocalDateTime timeStamp;

    public static void increaseScoreBy(int n) {
        score = score + n;
    }

    public static int getScore() {
        return score;
    }


    public String getFormatTimeStamp(){
        return timeStamp.format(DateTimeFormatter.ofPattern("yy-MM-dd HH:mm"));
    }
    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public static Score createNew(int score){
        Score newScore = new Score();
        newScore.score = score;
        newScore.timeStamp = LocalDateTime.now();
        return newScore;
    }

    public static void setScore(int score) {
        score = score;
    }
}
