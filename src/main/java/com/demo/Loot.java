package com.demo;

import java.util.Arrays;

/** Loot.java
 *
 * @author Emily Crow 2142214
 * @version 2.0
 */
public class Loot extends Item {

    private static final int VALUE = 3;

    /** Constructs the loot
     *
     * @param x x-coordinate of the loot
     * @param y y-coordinate of the loot
     * @param type String indicating the type of loot it is
     */
    public Loot(int x, int y, String type) {
        super(x, y, type);
        //types in order of value
        setTypes(new String[]{"dollar", "gold", "ruby", "diamond"});
    }

    /** Constructs the loot
     *
     */
    public Loot(){
        setTypes(new String[]{"dollar", "gold", "ruby", "diamond"});
    }

    /** Returns if loot has been collected.
     * 'Activates' the loot as well.
     *
     * @return always returns true
     */
    @Override
    public boolean getCollected() {
        activate();
        return true;
    }

    @Override
    public boolean beStolen() {
        return true;
    }

    @Override
    public void activate() {
        Score.increaseScoreBy((Arrays.asList(types).indexOf(type)+1) * VALUE);
    }


}
