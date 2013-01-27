package com.lunaticedit.legendofwaffles.contracts;

import com.lunaticedit.legendofwaffles.implementations.repository.Player;
import com.lunaticedit.legendofwaffles.implementations.ui.UI;

import java.util.LinkedList;

/**
 * Defines the data used to run the game.
 */
public interface Repository {
    public LinkedList<Object> getObjects();
    public void removeObject(Object object);
    public Scene getScene();
    public void setScene(final Scene scene);
    public Player getPlayer();
    public UI getUI();
}
