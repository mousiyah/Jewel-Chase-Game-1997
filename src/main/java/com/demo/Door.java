package com.demo;

import javafx.scene.image.Image;
import java.util.Objects;

/**
 * Door an exit for player to the next level.
 * Once all loots are collected and gates are destroyed door is open.
 * @author Emily Crow 2142214
 * @version 2.0
 */
public class Door extends Item {

    private boolean isReached;
    private boolean isClosedForever;

    /**
     * Construct a door with parameters.
     * @param x x-coordinate of the gate
     * @param y y-coordinate of the gate
     */
    public Door(int x, int y) {
        super(x, y);
        isActive = false;
        isReached = false;
        isClosedForever = false;
    }

    /**
     * Construct an empty door.
     */
    public Door() {}

    /**
     * Upon collection by player isReached is true if door is open.
     * @return false if not open
     */
    @Override
    public boolean getCollected() {
        if (isActive) {
            isReached = true;
        }
        return false;
    }

    /**
     * Upon collection by thief if door is open it closes forever.
     * @return false if not open
     */
    @Override
    public boolean beStolen() {
        if (isActive) {
            isClosedForever = true;
        }
        return false;
    }

    /**
     * Open the door.
     */
    @Override
    public void activate() {
        setImg(new Image(Objects.requireNonNull(getClass().
                getResourceAsStream("img/openDoor.png"))));
        setIsActive(true);
    }

    /**
     * Check if no Loots and Gates Left.
     * @return true if yes, false otherwise
     */
    public static boolean noLootsAndGatesLeft() {
        boolean check = true;
        for (int i = 0; i < getTiles().length; i++) {
            for(int j = 0; j < getTiles()[i].length; j++){
                if (getTiles()[i][j].getItem() instanceof Loot
                        || getTiles()[i][j].getItem() instanceof Gate) {
                    check = false;
                }
            }
        }
        return check;
    }

    /**
     * @return true if Door is reached, false otherwise.
     */
    public boolean isReached() {
        return isReached;
    }

    /**
     * Set reached.
     * @param reached
     */
    public void setReached(boolean reached) {
        isReached = reached;
    }

    /**
     * @return true if Door is closed forever, false otherwise.
     */
    public boolean isClosedForever() {
        return isClosedForever;
    }

    /**
     * Set closed forever.
     * @param closedForver
     */
    public void setClosedForever(boolean closedForver) {
        isClosedForever = closedForver;
    }

}
