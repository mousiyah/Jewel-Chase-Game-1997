package com.demo;

/**
 * Abstract class Thief gives thieves ability to collect items.
 * @author Fidel Little 2123494
 * @author Muslima Karimova 2130288
 * @version 2.0
*/

public abstract class Thief extends MovingEntity {

    protected Thief() {}

    /**
     * If there is an collectable item on the tile it will steal it.
     * If the tile is a bomb area it will activate the bomb.
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
