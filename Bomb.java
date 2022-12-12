package com.demo;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.Objects;

/** Bomb.java
 * 
 * @author user
 * @version 2.0
 */
public class Bomb extends Item {

    private static final int COUNT_DOWN = 3;

    public Bomb(int x, int y) {
        super(x, y);
        isActive = false;
    }

    public Bomb() {

    }

    @Override
    public boolean getCollected() {
        isActive = true;
        tick();
        return true;
    }

    @Override
    public boolean beStolen() {
        getCollected();
        return true;
    }

    public void tick() {
        setImg(new Image(Objects.requireNonNull(getClass().
                getResourceAsStream("img/bomb" + 3 + ".png"))));
        setTimeLine(new Timeline(new KeyFrame(Duration.seconds(1),
                new EventHandler<ActionEvent>() {
                    int n = COUNT_DOWN-1;
                    @Override
                    public void handle(ActionEvent t) {
                        setImg(new Image(Objects.requireNonNull(getClass().
                                getResourceAsStream("img/bomb" + n + ".png"))));
                        n--;
                        if (n==0) {
                            activate();
                        }
                    }
                })));
        getTimeLine().setCycleCount(COUNT_DOWN-1);
        getTimeLine().play();
    }

    @Override
    public void activate() {
        int i;
        for (i = getY(); i >= 0; i--) {
            getTiles()[i][getX()].setItemToNull();
        }
        for (i = getY(); i < getTiles().length; i++) {
            getTiles()[i][getX()].setItemToNull();
        }
        for (i = getX(); i < getTiles()[0].length; i++) {
            getTiles()[getY()][i].setItemToNull();
        }
        for (i = getX(); i >= 0; i--) {
            getTiles()[getY()][i].setItemToNull();
        }
        getTiles()[getY()][getX()].setItem(null);
    }
}