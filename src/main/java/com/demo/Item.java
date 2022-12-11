package com.demo;

public abstract class Item extends Entity {

    protected String[] types;
    protected String type;
    protected boolean isActive;

    public Item(int x, int y, String type) {
        setX(x);
        setY(y);
        setType(type);
    }

    public Item(int x, int y) {
        setX(x);
        setY(y);
    }

    protected Item() {
    }


    public abstract boolean getCollected();

    public abstract boolean beStolen();

    public abstract void activate();

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String[] getTypes() {
        return types;
    }

    public void setTypes(String[] types) {
        this.types = types;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setIsActive(boolean active) {
        isActive = active;
    }
}
