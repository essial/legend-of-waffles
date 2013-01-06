package com.lunaticedit.legendofwaffles.implementations.stage;

import com.lunaticedit.legendofwaffles.contracts.Stage;

public class StandardStage implements Stage {
    private int _playerStartX;
    private int _playerStartY;
    private String _defaultStage;

    public StandardStage() {
    }

    @Override
    public int getPlayerStartX() {
        return _playerStartX;
    }

    @Override
    public void setPlayerStartX(int value) {
        _playerStartX = value;
    }

    @Override
    public int getPlayerStartY() {
        return _playerStartY;
    }

    @Override
    public void setPlayerStartY(int value) {
        _playerStartY = value;
    }

    @Override
    public String getDefaultStage() {
        return _defaultStage;
    }

    @Override
    public void setDefaultStage(final String fileName) {
        _defaultStage = fileName;
    }
}
