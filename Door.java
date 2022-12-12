package com.demo;

import javafx.scene.image.Image;
import java.util.Objects;

/** Door.java
 * 
 * @author user
 * @version 2.0
 */
public class Door extends Item {

    private boolean isReached;
    private boolean isClosedForever;

    public Door(int x, int y) {
        super(x, y);
        isActive = false;
        isReached = false;
        isClosedForever = false;
    }

    public Door() {

    }

    @Override
    public boolean getCollected() {
        if (isActive) {
            isReached = true;
        }
        return false;
    }

    @Override
    public boolean beStolen() {
        if (isActive) {
            isClosedForever = true;
        }
        return false;
    }

    @Override
    public void activate() {
        setImg(new Image(Objects.requireNonNull(getClass().
                getResourceAsStream("img/openDoor.png"))));
        setIsActive(true);
    }

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

    public boolean isReached() {
        return isReached;
    }

    public void setReached(boolean reached) {
        isReached = reached;
    }

    public boolean isClosedForever() {
        return isClosedForever;
    }

    public void setClosedForever(boolean closedForver) {
        isClosedForever = closedForver;
    }
}