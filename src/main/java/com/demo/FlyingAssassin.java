package com.demo;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class FlyingAssassin extends MovingEntity {

    public FlyingAssassin() {
        setSpeed(600);
    }

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

    public boolean collusionWithPlayer(int xPlayer, int yPlayer) {
        return getX() == xPlayer && getY() == yPlayer;
    }

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

    @Override
    protected void collectItem(Tile tile) {
    }

}
