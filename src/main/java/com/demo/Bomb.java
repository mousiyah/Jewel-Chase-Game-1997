package com.demo;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Bomb is an item which gets triggered if thief or player occupies the same tile.
 * It counts down and explodes vertically and horizontally on tiles.
 * On the way it destroys loots and levers.
 * Level can become unwinnable if all levers destroyed but some gates left.
 * @author Muslima Karimova 2130288
 * @version 2.0
 */
public class Bomb extends Item {

    // count down in seconds
    private static final int COUNT_DOWN = 3;

    /**
     * Construct a bomb with parameters.
     * @param x x-coordinate
     * @param y y-coordinate
     */
    public Bomb(int x, int y) {
        super(x, y);
        isActive = false;
    }

    /**
     * Construct an empty bomb.
     */
    public Bomb() {}

    /**
     * Get collected and start to tick.
     * @return always true
     */
    @Override
    public boolean getCollected() {
        isActive = true;
        tick();
        return true;
    }

    /**
     * Be stolen -> get collected.
     * @return always true;
     */
    @Override
    public boolean beStolen() {
        getCollected();
        return true;
    }

    /**
     * Count down.
     */
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

    /**
     * Explode and destroy items on the way.
     */
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
