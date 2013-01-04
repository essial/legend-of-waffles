package com.lunaticedit.legendofwaffles.contracts;

import com.lunaticedit.legendofwaffles.helpers.Point;

/**
 * Defines an object that is capable of rendering graphics to the screen directly.
 */
public interface GraphicsGenerator {
    /**
     * Draws a tile onto the screen.
     * @param x The X position of the object, in pixels.
     * @param y The Y position of the object, in pixels.
     * @param tileNum The tile number to draw.
     */
    public void drawTile(final int x, final int y, final int tileNum);

    /**
     * Draws a tile onto the screen.
     * @param x The X position of the object, in tiles.
     * @param y The Y position of the object, in tiles.
     * @param tileNum The tile number to draw.
     */
    public void drawTileAbsolute(final int x, final int y, final int tileNum);

    /**
     * Draws text onto the screen.
     * @param x The X position of the object, in pixels.
     * @param y The Y position of the object, in pixels.
     * @param text The text to render.
     */
    public void drawText(final int x, final int y, final String text);

    /**
     * Sets the rendering offset (defined as pixels). This offset is subtracted
     * from all positional calculations.
     * @param offset
     */
    public void setOffset(final Point offset);
}
