package com.demo;

/** Clock.java
 * 
 * @author Leon Banks 2015257
 * @version 3.0
 *
 */
public class Clock extends Item {
    
	private final static int TIME = 3;
    
	/** Sets the coordinates of the clock
	 * 
	 * @param x
	 * @param y
	 */
	public Clock(int x, int y) {
        super(x, y);
    }

	/** Gets clock from items
	 * 
	 */
    public Clock() {

    }

    /** Constructs a collected clock
     * 
     * @return collected clock
     */
    @Override
    public boolean getCollected() {
        activate();
        return true;
    }

    /** Constructs a stolen clock
     * 
     * @return stolen clock
     */
    @Override
    public boolean beStolen() {
        LevelTimer.setTimeLeft(LevelTimer.getTimeLeft() - TIME);
        return true;
    }

    /** Adds time to the collected clock
     * 
     */
    @Override
    public void activate() {
        LevelTimer.setTimeLeft(LevelTimer.getTimeLeft() + TIME);
    }
}