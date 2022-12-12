package com.demo;

import javafx.animation.Timeline;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Entity is an abstract parent class for all entities in a game.
 * @author Muslima Karimova 2130288
 * @version 2.0
 */
public abstract class Entity {
    
	private int x, y;
    private static Tile[][] tiles;
    protected ImageView imgView;
    protected Image img;
    private SnapshotParameters params;
    private Timeline timeLine;

    /**
     * An empty constructor.
     */
    protected Entity() {}

    /**
     * Set postitions of entity.
     * @param x x-coordinate
     * @param y y-coordinate
     */
    protected void setPositions(int x, int y) {
        setX(x);
        setY(y);
    }

    /**
     * @return tiles
     */
    public static Tile[][] getTiles() {
        return tiles;
    }

    /**
     * Set tiles.
     * @param tiles
     */
    public static void setTiles(Tile[][] tiles) {
        Entity.tiles = tiles;
    }

    /**
     * @return timeline
     */
    public Timeline getTimeLine() {
        return timeLine;
    }

    /**
     * Set timeline.
     * @param timeLine
     */
    public void setTimeLine(Timeline timeLine) {
        this.timeLine = timeLine;
    }

    /**
     * @return x coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Set x coordinate.
     * @param x
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @return y coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Set y coordinate
     * @param y
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * @return img
     */
    public Image getImg() {
        return img;
    }

    /**
     * Set image.
     * @param img
     */
    public void setImg(Image img) {
        this.img = img;
    }
}
