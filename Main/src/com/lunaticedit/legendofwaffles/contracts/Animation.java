package com.lunaticedit.legendofwaffles.contracts;

public interface Animation {
    public int getAnimationSpeed();
    public boolean getShouldAnimate();
    public int getCurrentFrame();
    public void setCurrentFrame(int value);
    public long getAnimationTime();
    public void setAnimationTime(long value);
    public boolean getFinal();
}
