package com.demo;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 * Moving Entity is an abstract class for entities that can move
 * @author Muslima Karimova 2130288
 * @version 2.0
*/

public abstract class MovingEntity extends Entity {

    private int speed;

    private KeyCode direction;
    private String colorToFollow;

    protected ImageView imgView;
    private SnapshotParameters params;

    private Timeline timeLine;

    /**
     * Constructor
    */
    public MovingEntity() {
        setParams();
    }

    /**
     * Abstract method move
     * @param key
    */
    protected abstract void move(KeyCode key);

    /**
     * Check if the move is valid
     * @param x coordinate
     * @param y coordinate
     * @return true if its valid move
    */
    protected boolean isValidMove(int y, int x) {
        return !(getTiles()[y][x].getItem() instanceof Gate)
                && !(getTiles()[y][x].getItem() instanceof Bomb);
    }

    /**
     * Move on every tick
    */
    public void tick() {
        setTimeLine(new Timeline(new KeyFrame(Duration.millis(speed),
                event -> move(getDirection()))));
        getTimeLine().setCycleCount(Animation.INDEFINITE);
        getTimeLine().play();
    }

    /**
     * Get KeyCode from String
     * @param key
     * @return key
    */
    public static KeyCode getKeyCodeFromString(String key) {
        return switch (key) {
            case "UP" -> KeyCode.UP;
            case "DOWN" -> KeyCode.DOWN;
            case "LEFT" -> KeyCode.LEFT;
            case "RIGHT" -> KeyCode.RIGHT;
            default -> null;
        };
    }

    /**
     *
     * @param key
    */
    protected void rotateImg(KeyCode key) {
        if (key == KeyCode.UP) {
            getImgView().setRotate(0);
        } else if (key == KeyCode.DOWN) {
            getImgView().setRotate(180);
        } else if (key == KeyCode.RIGHT) {
            getImgView().setRotate(90);
        } else if (key == KeyCode.LEFT) {
            getImgView().setRotate(-90);
        }
        updateImg();
    }

    /**
     * Check if Moving entity is on the top edge of the tiles
     * @return true if so
    */
    public boolean isTopEdge() {
        return getY() == 0;
    }

    /**
     * Check if Moving entity is on the right edge of the tiles
     * @return true if so
     */
    public boolean isRightEdge() {
        return getX() == getTiles()[0].length-1;
    }

    /**
     * Check if Moving entity is on the bottom edge of the tiles
     * @return true if so
     */
    public boolean isBottomEdge() {
        return getY() == getTiles().length-1;
    }

    /**
     * Check if Moving entity is on the left edge of the tiles
     * @return true if so
     */
    public boolean isLeftEdge() {
        return getX() == 0;
    }

    /**
     * Abstract method for collecting an item
     * @param tile
    */
    protected abstract void collectItem(Tile tile);

    /**
     * @return direction
    */
    public KeyCode getDirection() {
        return direction;
    }

    /**
     * @param direction
    */
    public void setDirection(KeyCode direction) {
        this.direction = direction;
        rotateImg(direction);
    }

    /**
     * Set Image
     * @param img
    */
    @Override
    public void setImg(Image img){
        setImgView(img);
        this.img = getImgView().snapshot(params, null);
    }

    /**
     * Update Image
    */
    public void updateImg() {
        this.img = getImgView().snapshot(params, null);
    }

    /**
     * @return imageview
    */
    public ImageView getImgView() {
        return imgView;
    }

    /**
     * Set Image View
     * @param img
    */
    public void setImgView(Image img) {
        this.imgView.setImage(img);
    }

    /**
     * Set snapshot parameters and link image view with image
    */
    public void setParams(){
        params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        this.imgView = new ImageView();
        imgView.setFitHeight(50);
        imgView.setFitWidth(50);
    }

    /**
     * @return speed
    */
    public int getSpeed() {
        return speed;
    }

    /**
     * @param speed value
    */
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     * @return colour name
    */
    public String getColorToFollow() {
        return colorToFollow;
    }

    /**
     * @param colorToFollow
    */
    public void setColorToFollow(String colorToFollow) {
        this.colorToFollow = colorToFollow;
    }
}
