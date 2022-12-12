package com.demo;

/** Lever.java
 *
 * @author Emily Crow 2142214
 * @version 2.0
 */
public class Lever extends Item {

    /** Constructs a lever.
     *
     * @param x x-coordinate for the lever
     * @param y y-coordinate for the lever
     * @param type String indicating the colour of the lever
     */
    public Lever(int x, int y, String type) {
        super(x, y, type);
        setTypes(new String[]{"blue", "green", "red"});
    }

    /** Constructs a lever.
     *
     */
    public Lever() {
        setTypes(new String[]{"blue", "green", "red"});
    }

    /** Returns if lever is collectable.
     *
     * @return always returns true
     */
    @Override
    public boolean getCollected() {
        activate();
        getTiles()[getY()][getX()].setItemToNull();
        return true;
    }

    @Override
    public boolean beStolen() {
        getCollected();
        return true;
    }

    @Override
    public void activate() {
        for(int i = 0; i < getTiles().length; i++){
            for(int j = 0; j < getTiles()[i].length; j++){
                if (getTiles()[i][j].getItem() instanceof Gate) {
                    if (getTiles()[i][j].getItem().getType().equals(type)) {
                        getTiles()[i][j].setItem(null);
                    }
                }
            }
        }
    }

}
