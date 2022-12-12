package com.demo;

import javafx.scene.input.KeyCode;

import java.util.ArrayList;

/**
 * FlyingAssassin moves vertically or horizontally ignoring movement rules.
 * @author Muslima Karimova 2130288
 * @version 2.0
 */

public class FlyingAssassin extends MovingEntity {

    /**
     * Construct a flying assassin.
     */
    public FlyingAssassin() {
        setSpeed(600);
    }

    /**
     * Moves the flying assassin on the game board according to direction key.
     * Overrides abstract move method.
     * @param key
     */
    @Override
    public void move(KeyCode key) {
        if (key == KeyCode.UP) {
            if (!isTopEdge()){
                setPositions(getX(),getY() - 1);
            } else{
                setDirection(KeyCode.DOWN);
            }
        } else if (key == KeyCode.DOWN) {
            if (!isBottomEdge()){
                setPositions(getX(),getY() + 1);
            } else{
                setDirection(KeyCode.UP);
            }
        } else if (key == KeyCode.LEFT) {
            if (!isLeftEdge()){
                setPositions((getX() - 1), getY());
            } else{
                setDirection(KeyCode.RIGHT);
            }
        } else if (key == KeyCode.RIGHT) {
            if (!isRightEdge()){
                setPositions(getX() + 1, getY());
            } else{
                setDirection(KeyCode.LEFT);
            }
        }
    }

    /**
     * Check if it has a collusion with a player.
     * @return true if so
     */
    public boolean collusionWithPlayer(int xPlayer, int yPlayer) {
        return getX() == xPlayer && getY() == yPlayer;
    }

    /**
     * Check if it has a collusion with Thief.
     * @return true if so
     */
    public void collusionWithThief(ArrayList<MovingEntity> movingEntities) {
        for (MovingEntity movingEntity: movingEntities) {
            if (movingEntity instanceof FloorFollowingThief ||
                    movingEntity instanceof SmartThief) {
                if (getX() == movingEntity.getX() && getY() == movingEntity.getY()) {
                    movingEntities.remove(movingEntity);
                }
            }
        }
    }

    /**
     * Flying assassin cannot collect an item.
     */
    @Override
    protected void collectItem(Tile tile) {
    }

}
