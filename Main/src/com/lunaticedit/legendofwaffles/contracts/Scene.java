package com.lunaticedit.legendofwaffles.contracts;


import com.badlogic.gdx.math.Rectangle;

/**
 * Describes a rendering system. This could be the main game, a menu, or any other fundamental rendering
 * system.
 */
public interface Scene {
    public void render(final Rectangle screenBounds);
    public void update();
}
