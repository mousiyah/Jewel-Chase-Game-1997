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

import java.beans.EventHandler;
import java.util.ArrayList;

/**
* 
* @author
* @author
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
    * 
    */
    public MovingEntity() {
        setParams();
    }

    /**
    * 
    * @param key
    */
    protected abstract void move(KeyCode key);

    /**
    * 
    * @param x_coordinate
    * @param y_coordinate
    */
    protected boolean isValidMove(int y, int x) {
        return !(getTiles()[y][x].getItem() instanceof Gate)
                && !(getTiles()[y][x].getItem() instanceof Bomb);
    }

    /**
    * 
    */
    public void tick() {
        setTimeLine(new Timeline(new KeyFrame(Duration.millis(speed),
                event -> move(getDirection()))));
        getTimeLine().setCycleCount(Animation.INDEFINITE);
        getTimeLine().play();
    }

    /**
    * 
    * @param string_key
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
    * all four check for final tile so entities can stay on the screen
    * @return true for final tile before the edge of the game
    */
    public boolean isTopEdge() {
        return getY() == 0;
    }
    public boolean isRightEdge() {
        return getX() == getTiles()[0].length-1;
    }
    public boolean isBottomEdge() {
        return getY() == getTiles().length-1;
    }
    public boolean isLeftEdge() {
        return getX() == 0;
    }

    /**
    * 
    * @param tile
    */
    protected abstract void collectItem(Tile tile);

    /**
    * @return y
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
    * 
    * @param image
    */
    @Override
    public void setImg(Image img){
        setImgView(img);
        this.img = getImgView().snapshot(params, null);
    }

    /**
    *  
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
    * 
    * @param image
    */
    public void setImgView(Image img) {
        this.imgView.setImage(img);
    }

    /**
    * 
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
    * @param colour name
    */
    public void setColorToFollow(String colorToFollow) {
        this.colorToFollow = colorToFollow;
    }
}
