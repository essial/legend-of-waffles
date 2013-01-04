package com.lunaticedit.legendofwaffles.contracts;

import com.lunaticedit.legendofwaffles.helpers.Dimension;
import com.lunaticedit.legendofwaffles.helpers.Point;

/**
 * Defines an object that needs to be draw on each iteration of a game loop.
 */
public interface Renderable {
    /**
     * Gets the X position of the object in pixels relative to the game world.
     */
    public int getX();

    /**
     * Gets the Y position of the object in pixels relative to the game world.
     */
    public int getY();

    /**
     * Gets the visibility of the object.
     * @return False = Don't draw, True = draw.
     */
    public boolean getVisible();

    /**
     * Gets the number of tiles (width and height) of this object (in tiles).
     */
    public Dimension getTileSize();

    /**
     * Gets the top-left point of the graphic in the tile set (in tiles).
     */
    public Point getTileOrigin();
}
