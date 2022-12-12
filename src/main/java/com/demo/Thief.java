package com.demo;

/**
* 
* @author 
* @author Fidel Little 2123494
* @author 
* @version 2.0
*/

public abstract class Thief extends MovingEntity {

    protected Thief() {}

    /**
    * If there is an item on the tile it will collect it
    * If there is a bomb on the tile it will activate it
    * @param tile
    */
    @Override
    protected void collectItem(Tile tile) {
        if (tile.getItem() != null) {
            if (tile.getItem().beStolen()) {
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

}
