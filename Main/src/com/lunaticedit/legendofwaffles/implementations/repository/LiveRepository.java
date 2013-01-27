package com.lunaticedit.legendofwaffles.implementations.repository;

import com.lunaticedit.legendofwaffles.contracts.Repository;
import com.lunaticedit.legendofwaffles.contracts.Scene;
import com.lunaticedit.legendofwaffles.implementations.ui.UI;

import java.util.ArrayList;
import java.util.LinkedList;

public final class LiveRepository implements Repository {
    // Data Store
    private final LinkedList<Object> _objects;
    private final ArrayList<Object> _removalQueue;
    private Scene _scene;
    private Player _player;
    private UI _ui;

    public LiveRepository() {
        _objects = new LinkedList<Object>();
        _removalQueue = new ArrayList<Object>();
        _player = new Player();
        _ui = new UI();
    }

    @Override
    public LinkedList<Object> getObjects() {
        if (_removalQueue.size() > 0) {
            for(Object o : _removalQueue) {
                if (_objects.contains(o))
                { _objects.remove(o); }
            }
            _removalQueue.clear();
        }
        return _objects;
    }

    @Override
    public void removeObject(final Object object) {
        _removalQueue.add(object);
    }

    @Override
    public Scene getScene() {
        return _scene;
    }

    @Override
    public void setScene(final Scene scene) {
        _scene = scene;
    }

    @Override
    public Player getPlayer() {
        return _player;
    }

    @Override
    public UI getUI() {
        return _ui;
    }


}
