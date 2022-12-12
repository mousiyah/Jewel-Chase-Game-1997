package com.demo;

import javafx.animation.Timeline;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/** Entity.java
 * 
 * @author user
 * @version 2.0
 */
public abstract class Entity {
    
	private int x, y;
    private static Tile[][] tiles;
    protected ImageView imgView;
    protected Image img;
    private SnapshotParameters params;
    private Timeline timeLine;

    protected Entity() {
    
    }

    protected void setPositions(int x, int y) {
        setX(x);
        setY(y);
    }

    public static Tile[][] getTiles() {
        return tiles;
    }

    public static void setTiles(Tile[][] tiles) {
        Entity.tiles = tiles;
    }

    public Timeline getTimeLine() {
        return timeLine;
    }

    public void setTimeLine(Timeline timeLine) {
        this.timeLine = timeLine;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
    }
}