package com.lunaticedit.legendofwaffles.contracts;

public interface Stage {
    public int getPlayerStartX();
    public void setPlayerStartX(final int value);
    public int getPlayerStartY();
    public void setPlayerStartY(final int value);
    public String getDefaultStage();
    public void setDefaultStage(String fileName);
    public int getMapWidth();
    public void setMapWidth(final int value);
    public int getMapHeight();
    public void setMapHeight(final int value);
    public String getStageTitle();
    public void setStageTitle(final String value);
    public int[] getTileData();
    public void setTileData(final int[] value);
}
