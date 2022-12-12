package com.demo;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

/**
 * Floor Following Thief follows assigned color on the edges of the tiles.
 * @author Muslima Karimova 2130288
 * @version 2.0
 */

public class FloorFollowingThief extends Thief {

    /**
      * Contruct smart thief entity and set default speed.
     */
    public FloorFollowingThief() {
        setSpeed(600);
    }

    /**
      * Move the thief on the game board according to direction key.
      * Overrides abstract move method.
      * @param key
     */
    @Override
    public void move(KeyCode key) {
        turnDirection();
        if(getDirection() == KeyCode.UP) {
            setPositions(getX(), getY() - 1);
        } else if(getDirection() == KeyCode.RIGHT) {
            setPositions(getX()+1, getY());
        } else if(getDirection() == KeyCode.DOWN) {
            setPositions(getX(), getY()+1);
        } else if(getDirection() == KeyCode.LEFT) {
            setPositions(getX()-1, getY());
        }
        collectItem(getTiles()[getY()][getX()]);
    }

    /**
      * Turn the NPC in the first direction it can move in.
     */
    public void turnDirection(){

        // facing right
        if(getDirection() == KeyCode.RIGHT) {
            if (canTurn(KeyCode.UP)) {
                setDirection(KeyCode.UP);
            } else if (canTurn(KeyCode.RIGHT)) {
                setDirection(KeyCode.RIGHT);
            } else if (canTurn(KeyCode.DOWN)) {
                setDirection(KeyCode.DOWN);
            } else if (canTurn(KeyCode.LEFT)) {
                setDirection(KeyCode.LEFT);
            }
        }
        // facing down
        else if(getDirection() == KeyCode.DOWN) {
            if (canTurn(KeyCode.RIGHT)) {
                setDirection(KeyCode.RIGHT);
            } else if (canTurn(KeyCode.DOWN)) {
                setDirection(KeyCode.DOWN);
            } else if (canTurn(KeyCode.LEFT)) {
                setDirection(KeyCode.LEFT);
            } else if (canTurn(KeyCode.UP)) {
                setDirection(KeyCode.UP);
            }
        }
        // facing left
        else if(getDirection() == KeyCode.LEFT) {
            if (canTurn(KeyCode.DOWN)) {
                setDirection(KeyCode.DOWN);
            } else if (canTurn(KeyCode.LEFT)) {
                setDirection(KeyCode.LEFT);
            } else if (canTurn(KeyCode.UP)) {
                setDirection(KeyCode.UP);
            } else if (canTurn(KeyCode.RIGHT)) {
                setDirection(KeyCode.RIGHT);
            }
        }
        // facing up
        else if(getDirection() == KeyCode.UP) {
            if (canTurn(KeyCode.LEFT)) {
                setDirection(KeyCode.LEFT);
            } else if (canTurn(KeyCode.UP)) {
                setDirection(KeyCode.UP);
            } else if (canTurn(KeyCode.RIGHT)) {
                setDirection(KeyCode.RIGHT);
            } else if (canTurn(KeyCode.DOWN)) {
                setDirection(KeyCode.DOWN);
            }
        }
    }

    /**
      * Determine if thief can turn in key direction without leaving bounds or breaking rules.
      * @param key
     */
    public boolean canTurn(KeyCode key) {
        if (key == KeyCode.UP) {
            return !isTopEdge() &&
                    Tile.hasCommonColor(getTiles()[getY() - 1][getX()], getTiles()[getY()][getX()])
                    && String.copyValueOf(getTiles()[getY() - 1][getX()].getColors())
                    .contains(getColorToFollow())
                    && isValidMove(getY() - 1,getX());
        } else if (key == KeyCode.DOWN) {
            return !isBottomEdge() &&
                    Tile.hasCommonColor(getTiles()[getY() + 1][getX()], getTiles()[getY()][getX()])
                    && String.copyValueOf(getTiles()[getY() + 1][getX()].getColors())
                    .contains(getColorToFollow())
                    && isValidMove(getY() + 1,getX());
        } else if (key == KeyCode.LEFT) {
            return !isLeftEdge() &&
                    Tile.hasCommonColor(getTiles()[getY()][getX() - 1], getTiles()[getY()][getX()])
                    && String.copyValueOf(getTiles()[getY()][getX() - 1].getColors())
                    .contains(getColorToFollow())
                    && isValidMove(getY(),getX() - 1);
        } else if (key == KeyCode.RIGHT) {
            return !isRightEdge() &&
                    Tile.hasCommonColor(getTiles()[getY()][getX() + 1], getTiles()[getY()][getX()])
                    && String.copyValueOf(getTiles()[getY()][getX() + 1].getColors())
                    .contains(getColorToFollow())
                    && isValidMove(getY(),getX() + 1);
        }
        return false;
    }

}
