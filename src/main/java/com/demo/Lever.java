package com.demo;

public class Lever extends Item {

    public Lever(int x, int y, String type) {
        super(x, y, type);
        setTypes(new String[]{"blue", "green", "red"});
    }

    public Lever() {}

    @Override
    public boolean getCollected() {
        activate();
        getTiles()[getY()][getX()].setItemToNull();
        return true;
    }

    @Override
    public boolean beStolen() {
        getCollected();
        return true;
    }

    @Override
    public void activate() {
        for(int i = 0; i < getTiles().length; i++){
            for(int j = 0; j < getTiles()[i].length; j++){
                if (getTiles()[i][j].getItem() instanceof Gate) {
                    if (getTiles()[i][j].getItem().getType().equals(type)) {
                        getTiles()[i][j].setItem(null);
                    }
                }
            }
        }
    }

}
