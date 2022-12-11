package com.demo;

public class Gate extends Item {

    public Gate(int x, int y, String type) {
        super(x, y, type);
        setTypes(new String[]{"blue", "green", "red"});
    }

    public Gate(){}

    @Override
    public boolean getCollected() {
        return false;
    }

    @Override
    public boolean beStolen() {
        return false;
    }

    @Override
    public void activate() {}

    public String getColor() {
        return type;
    }

    public void setColor(String color) {
        this.type = color;
    }
}
