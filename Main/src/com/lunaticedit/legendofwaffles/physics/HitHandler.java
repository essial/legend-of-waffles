package com.lunaticedit.legendofwaffles.physics;

import com.badlogic.gdx.physics.box2d.Body;

public interface HitHandler {
    public void hitOccurred(Body body1, Body body2);
}