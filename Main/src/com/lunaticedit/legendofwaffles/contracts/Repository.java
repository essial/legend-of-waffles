package com.lunaticedit.legendofwaffles.contracts;

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
     * Gets the current stage.
     */
    public Scene getScene();

    /**
     * Sets the current stage. This is typically used in the render and update logic
     * so changing this effectively changes the current stage being displayed
     * int he game.
     */
    public void setScene(final Scene scene);

}
