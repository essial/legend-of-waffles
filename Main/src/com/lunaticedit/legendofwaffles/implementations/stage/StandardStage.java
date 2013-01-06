package com.lunaticedit.legendofwaffles.implementations.stage;

import com.lunaticedit.legendofwaffles.contracts.Stage;

public class StandardStage implements Stage {
    private int _playerStartX;
    private int _playerStartY;
    private String _defaultStage;
    private int _mapWidth;
    private int _mapHeight;
    private String _stageTitle;
    private int[] _tileData;

    public StandardStage() {
    }

    @Override
    public int getPlayerStartX() {
        return _playerStartX;
    }

    @Override
    public void setPlayerStartX(final int value) {
        _playerStartX = value;
    }

    @Override
    public int getPlayerStartY() {
        return _playerStartY;
    }

    @Override
    public void setPlayerStartY(final int value) {
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

    @Override
    public int getMapWidth() {
        return _mapWidth;
    }

    @Override
    public void setMapWidth(final int value) {
        _mapWidth = value;
    }

    @Override
    public int getMapHeight() {
        return _mapHeight;
    }

    @Override
    public void setMapHeight(final int value) {
        _mapHeight = value;
    }

    @Override
    public String getStageTitle() {
        return _stageTitle;
    }

    @Override
    public void setStageTitle(final String value) {
        _stageTitle = value;
    }

    @Override
    public int[] getTileData() {
        return _tileData;
    }

    @Override
    public void setTileData(final int[] value) {
        _tileData = value;
    }
}
