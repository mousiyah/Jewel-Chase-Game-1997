package com.demo;

/** LevelTime.java
 *
 * @author user
 * @version 2.0
 */
public class LevelTimer {
    private static int timeLeft;
    private static int timeLimit;

    /** Constructs levelTimer.
     *
     * @param timeLimit integer indicating the time limit for the level
     */
    public LevelTimer(int timeLimit) {
        setTimeLimit(timeLimit);
        setTimeLeft(timeLimit);
    }

    /** Returns the time left to complete the level.
     *
     * @return remaining time
     */
    public static int getTimeLeft() {
        return timeLeft;
    }

    /** Sets the time left in the level.
     *
     * @param timeLeft time remaining
     */
    public static void setTimeLeft(int timeLeft) {
        LevelTimer.timeLeft = timeLeft;
    }

    /** Returns th e time limit for the level.
     *
     * @return time limit
     */
    public static int getTimeLimit() {
        return timeLimit;
    }

    /** Sets the time limit for the level.
     *
     * @param timeLimit time limit
     */
    public static void setTimeLimit(int timeLimit) {
        LevelTimer.timeLimit = timeLimit;
    }
}
