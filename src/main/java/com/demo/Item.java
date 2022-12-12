package com.demo;

/**
 * Item is an abstract parent class for all items.
 * @author Emily Crow, 2142214
 * @version 2.0
 */
public abstract class Item extends Entity {

    protected String[] types;
    protected String type;
    protected boolean isActive;

    /**
     * Construct an item with type.
     * @param x integer of x-coordinate of an item
     * @param y integer y-coordinate of an item
     * @param type String indicating the type of the item
     */
    public Item(int x, int y, String type) {
        setX(x);
        setY(y);
        setType(type);
    }

    /**
     * Construct an item without type.
     * @param x x-coordinate of an item
     * @param y y-coordinate of an item
     */
    public Item(int x, int y) {
        setX(x);
        setY(y);
    }

    /**
     * Construct an empty item.
     */
    protected Item() {
    }

    /**
     * Abstract method to get collected.
     */
    public abstract boolean getCollected();

    /**
     * Abstract method to be stolen.
     */
    public abstract boolean beStolen();

    /**
     * Abstract method to be activated.
     */
    public abstract void activate();

    /**
     * @return Item type
     */
    public String getType() {
        return type;
    }

    /**
     * Set item type.
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return Item list of types
     */
    public String[] getTypes() {
        return types;
    }

    /**
     * Set list of item types.
     * @param types of item
     */
    public void setTypes(String[] types) {
        this.types = types;
    }

    /**
     * Check if item is active
     * @return boolean
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * Set isActive.
     * @param active
     */
    public void setIsActive(boolean active) {
        isActive = active;
    }
}
