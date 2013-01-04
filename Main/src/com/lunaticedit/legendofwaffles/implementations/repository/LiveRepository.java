package com.lunaticedit.legendofwaffles.implementations.repository;

import com.lunaticedit.legendofwaffles.contracts.Processable;
import com.lunaticedit.legendofwaffles.contracts.Renderable;
import com.lunaticedit.legendofwaffles.contracts.Repository;
import com.lunaticedit.legendofwaffles.contracts.Scene;

import java.util.LinkedList;

public final class LiveRepository implements Repository {
    // Data Store
    private final LinkedList<Renderable> _renderables;
    private final LinkedList<Processable> _processables;
    private  Scene _scene;

    public LiveRepository() {
        _renderables = new LinkedList<Renderable>();
        _processables = new LinkedList<Processable>();
    }

    @Override
    public LinkedList<Renderable> getRenderables() {

        return _renderables;
    }
    @Override
    public LinkedList<Processable> getProcessables() {
        return _processables;
    }

    @Override
    public Scene getScene() {
        return _scene;
    }

    @Override
    public void setScene(final Scene scene) {
        _scene = scene;
    }


}
