package com.lunaticedit.legendofwaffles.contracts;

public interface Stage {
    public int getPlayerStartX();
    public void setPlayerStartX(int value);
    public int getPlayerStartY();
    public void setPlayerStartY(int value);
    public String getDefaultStage();
    public void setDefaultStage(String fileName);
}
