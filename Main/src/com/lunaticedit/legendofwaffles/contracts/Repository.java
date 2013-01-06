package com.lunaticedit.legendofwaffles.contracts;

import com.lunaticedit.legendofwaffles.implementations.repository.Player;

import java.util.LinkedList;

/**
 * Defines the data used to run the game.
 */
public interface Repository {
    /**
     * Gets the collection of objects that can be rendered onto the screen.
     */
    public LinkedList<Renderable> getRenderables();

    /**
     * Gets the collection of objects that can be processed each game loop iteration.
     */
    public LinkedList<Processable> getProcessables();

    /**
     * Gets the current scene.
     */
    public Scene getScene();

    /**
     * Sets the current scene. This is typically used in the render and update logic
     * so changing this effectively changes the current scene being displayed
     * in the game (main menu, game over, etc).
     */
    public void setScene(final Scene scene);

    public Player getPlayer();
}
