package com.lunaticedit.legendofwaffles.contracts;

/**
 * Describes a rendering system. This could be the main game, a menu, or any other fundamental rendering
 * system.
 */
public interface Scene {
    public void render();
    public void update();
}
