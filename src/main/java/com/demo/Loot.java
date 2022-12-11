package com.demo;

import java.util.Arrays;

public class Loot extends Item {

    private static final int VALUE = 3;

    public Loot(int x, int y, String type) {
        super(x, y, type);
        //types in order of value
        setTypes(new String[]{"dollar", "gold", "ruby", "diamond"});
    }

    public Loot(){
        setTypes(new String[]{"dollar", "gold", "ruby", "diamond"});
    }

    @Override
    public boolean getCollected() {
        activate();
        return true;
    }

    @Override
    public boolean beStolen() {
        return true;
    }

    @Override
    public void activate() {
        Score.increaseScoreBy((Arrays.asList(types).indexOf(type)+1) * VALUE);
    }


}
