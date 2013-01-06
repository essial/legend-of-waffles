package com.lunaticedit.legendofwaffles.contracts;

/**
 * Created with IntelliJ IDEA.
 * User: timsarbin
 * Date: 1/6/13
 * Time: 3:52 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Animation {
    public int getAnimationSpeed();
    public boolean getShouldAnimate();
    public int getCurrentFrame();
    public void setCurrentFrame(int value);
    public long getAnimationTime();
    public void setAnimationTime(long value);
}
