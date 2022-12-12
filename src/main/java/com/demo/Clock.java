package com.demo;

/**
 * Clock is an item which can be collected.
 * Upon collection by the player it adds to the level timer fixed time.
 * Upon collection by a thief it takes a fixed time from the level timer.
 * @author Leon Banks 2015257
 * @version 3.0
 */
public class Clock extends Item {
    
	private final static int TIME = 3;
    
	/**
     * Construct a clock with parameters.
	 * @param x x-coordinate
	 * @param y y-coordinate
	 */
	public Clock(int x, int y) {
        super(x, y);
    }

	/**
     * Construct an empty clock.
	 */
    public Clock() {

    }

    /**
     * Get collected.
     * @return always true
     */
    @Override
    public boolean getCollected() {
        activate();
        return true;
    }

    /**
     * Be stolen -> take from level timer.
     * @return always true;
     */
    @Override
    public boolean beStolen() {
        LevelTimer.setTimeLeft(LevelTimer.getTimeLeft() - TIME);
        return true;
    }

    /**
     * Activate the clock -> add to level timer.
     */
    @Override
    public void activate() {
        LevelTimer.setTimeLeft(LevelTimer.getTimeLeft() + TIME);
    }
}
