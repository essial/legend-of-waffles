package com.lunaticedit.legendofwaffles.implementations;

public class Player {
    private static Player _player;
    private int _x;
    private int _y;

    private Player() {
    }

    public static Player getInstance() {
        if (_player == null)
        { _player = new Player(); }
        return _player;
    }

    /**
     * Gets the X position of the player, in pixels.
     */
    public int getX() {
        return _x;
    }

    /**
     * Gets the Y position of the player, in pixels.
     */
    public int getY() {
        return _y;
    }

}
