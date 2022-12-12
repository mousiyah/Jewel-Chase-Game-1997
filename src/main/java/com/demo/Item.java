package com.demo;

/** Item.java
 *
 * @author Emily Crow, 2142214
 * @version 2.0
 */
public abstract class Item extends Entity {

    protected String[] types;
    protected String type;
    protected boolean isActive;

    /** Constructs an item.
     *
     * @param x integer of x-coordinate of an item
     * @param y integer y-coordinate of an item
     * @param type String indicating the colour of the item
     */
    public Item(int x, int y, String type) {
        setX(x);
        setY(y);
        setType(type);
    }

    /** Constructs an item.
     *
     * @param x x-coordinate of an item
     * @param y y-coordinate of an item
     */
    public Item(int x, int y) {
        setX(x);
        setY(y);
    }

    /** Constructs an item.
     *
     */
    protected Item() {
    }


    public abstract boolean getCollected();

    public abstract boolean beStolen();

    public abstract void activate();

    /** Returns the colour of the item.
     *
     * @return String of the colour of the item
     */
    public String getType() {
        return type;
    }

    /** Sets the colour of the item.
     *
     * @param type string indicating the colour of the item
     */
    public void setType(String type) {
        this.type = type;
    }

    /** Returns all the colours of the items.
     *
     * @return array of strings including all the colours of the items
     */
    public String[] getTypes() {
        return types;
    }

    /** Sets the array of item colours.
     *
     * @param types array of strings that contains the item colours
     */
    public void setTypes(String[] types) {
        this.types = types;
    }

    public boolean isActive() {
        return isActive;
    }

    /** Sets the boolean active.
     *
     * @param active boolean indicating if an item is active
     */
    public void setIsActive(boolean active) {
        isActive = active;
    }
}
