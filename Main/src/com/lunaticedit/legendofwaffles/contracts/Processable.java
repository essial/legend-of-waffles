package com.lunaticedit.legendofwaffles.contracts;

/**
 * Defines an object that needs processing done on each iteration of a game loop.
 */
public interface Processable {
    /**
     * Allows the object to process (update) itself.
     */
    public void process();
}
