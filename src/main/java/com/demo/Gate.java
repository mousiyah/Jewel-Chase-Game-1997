package com.demo;

/**
 * Gate an item which doesnt allow any entity on it.
 * Can be removed by collecting matching color loot.
 * @author Emily Crow, 2142214
 * @version 2.0
 */
public class Gate extends Item {

    /**
     * Construct a gate with parameters.
     * @param x x-coordinate of the gate
     * @param y y-coordinate of the gate
     * @param type colour of the gate
     */
    public Gate(int x, int y, String type) {
        super(x, y, type);
        setTypes(new String[]{"blue", "green", "red"});
    }

    /**
     * Construct an empty gate.
     */
    public Gate(){
        setTypes(new String[]{"blue", "green", "red"});
    }

    /**
     * Gate cannot be collected.
     * @return always returns false as it can't be collected
     */
    @Override
    public boolean getCollected() {
        return false;
    }

    /**
     * Gate cannot be stolen.
     * @return always returns false as it can't be stolen
     */
    @Override
    public boolean beStolen() {
        return false;
    }

    /**
     * Gate cannot be activated.
     */
    @Override
    public void activate() {}

    /**
     * @return gate color
     */
    public String getColor() {
        return type;
    }

    /**
     * Set gate color.
     * @param color
     */
    public void setColor(String color) {
        this.type = color;
    }
}
