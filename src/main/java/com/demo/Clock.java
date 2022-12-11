package com.demo;

public class Clock extends Item {
    private final static int TIME = 3;
    public Clock(int x, int y) {
        super(x, y);
    }

    public Clock(){}

    @Override
    public boolean getCollected() {
        activate();
        return true;
    }

    @Override
    public boolean beStolen() {
        Time.setTimeLeft(Time.getTimeLeft() - TIME);
        return true;
    }

    @Override
    public void activate() {
        Time.setTimeLeft(Time.getTimeLeft() + TIME);
    }

}
