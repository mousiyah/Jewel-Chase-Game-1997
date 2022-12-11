package com.demo;

import javafx.scene.input.KeyCode;

public class Player extends MovingEntity{

    private boolean isDead;

    public Player() {
        isDead = false;
    }

    @Override
    public void move(KeyCode key) {
        Tile nextTile = nextTile(key, getX(), getY());
        if (nextTile != null) {
            setDirection(key);
            setPositions(nextTile.getY(), nextTile.getX());
            collectItem(getTiles()[getY()][getX()]);
        }
    }

    @Override
    protected void collectItem(Tile tile) {
        if (tile.getItem() != null) {
            if (tile.getItem().getCollected()) {
                tile.setItemToNull();
            }
        }
        if (tile.isBombArea()) {
            if (!tile.getBombArea().isActive) {
                tile.getBombArea().getCollected();
                tile.setBombArea(null);
            }
        }
    }

    public Tile nextTile(KeyCode key, int x, int y){

        if (key == KeyCode.UP && !isTopEdge()) {
            for(int i = y-1; i > -1; i--){
                if(Tile.hasCommonColor(getTiles()[i][x],
                        getTiles()[y][x]) && isValidMove(i,x)){
                    return getTiles()[i][x];
                }
            }
        } else if (key == KeyCode.DOWN && !isBottomEdge()) {
            for(int i = y+1; i < getTiles().length; i++){
                if(Tile.hasCommonColor(getTiles()[i][x],
                        getTiles()[y][x]) && isValidMove(i,x)){
                    return getTiles()[i][x];
                }
            }
        } else if (key == KeyCode.LEFT && !isLeftEdge()) {
            for(int i = x-1; i > -1; i--){
                if(Tile.hasCommonColor(getTiles()[y][i],
                        getTiles()[y][x]) && isValidMove(y,i)){
                    return getTiles()[y][i];
                }
            }
        } else if (key == KeyCode.RIGHT && !isRightEdge()) {
            for(int i = x+1; i < getTiles()[0].length; i++){
                if(Tile.hasCommonColor(getTiles()[y][i],
                        getTiles()[y][x]) && isValidMove(y,i)){
                    return getTiles()[y][i];
                }
            }
        }
        return null;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }
}