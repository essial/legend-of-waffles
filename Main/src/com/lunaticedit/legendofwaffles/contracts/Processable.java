package com.lunaticedit.legendofwaffles.contracts;

import java.util.ArrayList;

/**
 * Defines an object that needs processing done on each iteration of a game loop.
 */
public interface Processable {
    /**
     * Allows the object to process (update) itself.
     * @return A list of response objects.
     */
    public ArrayList<ProcessResponse> process();
}
