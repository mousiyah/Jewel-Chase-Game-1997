package com.demo;

import javafx.scene.input.KeyCode;

public class SmartThief extends Thief {
    public SmartThief() {
    }

    @Override
    public void move(KeyCode key) {
        collectItem(getTiles()[getY()][getX()]);
    }
}
