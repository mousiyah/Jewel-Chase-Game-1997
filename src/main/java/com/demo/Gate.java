package com.demo;

/** Gate.java
 *
 * @author Emily Crow, 2142214
 * @version 2.0
 */
public class Gate extends Item {

    /** Constructs a gate.
     *
     * @param x x-coordinate of the gate
     * @param y y-coordinate of the gate
     * @param type colour of the gate
     */
    public Gate(int x, int y, String type) {
        super(x, y, type);
        setTypes(new String[]{"blue", "green", "red"});
    }

    /** Constructs a gate.
     *
     */
    public Gate(){
        setTypes(new String[]{"blue", "green", "red"});
    }

    /** Returns boolean indicating if gate has been collected.
     *
     * @return always returns false as it can't be collected
     */
    @Override
    public boolean getCollected() {
        return false;
    }

    /** Returns boolean indicating if gate has been stolen by a thief.
     *
     * @return always returns false as gate can't be stolen
     */
    @Override
    public boolean beStolen() {
        return false;
    }

    @Override
    public void activate() {}

    /** Returns a string of the colour of the gate.
     *
     * @return the colour of the gate
     */
    public String getColor() {
        return type;
    }

    /** Sets the colour of the gate.
     *
     * @param color String that indicates the colour of the gate
     */
    public void setColor(String color) {
        this.type = color;
    }
}
