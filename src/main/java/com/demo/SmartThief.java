package com.demo;

import javafx.scene.input.KeyCode;
import java.util.ArrayList;

/**
* 
* @author Fidel Little 2123494
* @author
* @author 
* @version 2.0
*/

public class SmartThief extends Thief {

    private ArrayList<Loot> loots;

    /**
    * Contructs smart thief entity and sets default speed
    * 
    */
    public SmartThief() {
        setSpeed(600);
    }

    /**
    * Moves the thief on the game board according to direction key
    * Overrides abstract move method
    * @param key
    */
    @Override
    public void move(KeyCode key) {
        turnDirection();
        if (getDirection() == KeyCode.UP) {
            setPositions(getX(), getY() - 1);
        } else if (getDirection() == KeyCode.RIGHT) {
            setPositions(getX() + 1, getY());
        } else if (getDirection() == KeyCode.DOWN) {
            setPositions(getX(), getY() + 1);
        } else if (getDirection() == KeyCode.LEFT) {
            setPositions(getX() - 1, getY());
        }
        collectItem(getTiles()[getY()][getX()]);
    }

    /**
    * turns the NPC in the first direction it can move in
    */
    public void turnDirection() {
        if (getDirection() == KeyCode.RIGHT) {
            if (canTurn(KeyCode.UP)) {
                setDirection(KeyCode.UP);
            } else if (canTurn(KeyCode.RIGHT)) {
                setDirection(KeyCode.RIGHT);
            } else if (canTurn(KeyCode.DOWN)) {
                setDirection(KeyCode.DOWN);
            } else if (canTurn(KeyCode.LEFT)) {
                setDirection(KeyCode.LEFT);
            }
        } else if (getDirection() == KeyCode.DOWN) {
            if (canTurn(KeyCode.RIGHT)) {
                setDirection(KeyCode.RIGHT);
            } else if (canTurn(KeyCode.DOWN)) {
                setDirection(KeyCode.DOWN);
            } else if (canTurn(KeyCode.LEFT)) {
                setDirection(KeyCode.LEFT);
            } else if (canTurn(KeyCode.UP)) {
                setDirection(KeyCode.UP);
            }
        } else if (getDirection() == KeyCode.LEFT) {
            if (canTurn(KeyCode.DOWN)) {
                setDirection(KeyCode.DOWN);
            } else if (canTurn(KeyCode.LEFT)) {
                setDirection(KeyCode.LEFT);
            } else if (canTurn(KeyCode.UP)) {
                setDirection(KeyCode.UP);
            } else if (canTurn(KeyCode.RIGHT)) {
                setDirection(KeyCode.RIGHT);
            }
        } else if (getDirection() == KeyCode.UP) {
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
    * Determines if thief can turn in key direction without leaving bounds or breaking rules
    * 
    * @param key
    */
    public boolean canTurn(KeyCode key) {
        if (key == KeyCode.UP) {
            return !isTopEdge() &&
                    Tile.hasCommonColor(getTiles()[getY() - 1][getX()], getTiles()[getY()][getX()])
                    && isValidMove(getY() - 1, getX());
        } else if (key == KeyCode.DOWN) {
            return !isBottomEdge() &&
                    Tile.hasCommonColor(getTiles()[getY() + 1][getX()], getTiles()[getY()][getX()])
                    && isValidMove(getY() + 1, getX());
        } else if (key == KeyCode.LEFT) {
            return !isLeftEdge() &&
                    Tile.hasCommonColor(getTiles()[getY()][getX() - 1], getTiles()[getY()][getX()])
                    && isValidMove(getY(), getX() - 1);
        } else if (key == KeyCode.RIGHT) {
            return !isRightEdge() &&
                    Tile.hasCommonColor(getTiles()[getY()][getX() + 1], getTiles()[getY()][getX()])
                    && isValidMove(getY(), getX() + 1);
        }
        return false;
    }
}
