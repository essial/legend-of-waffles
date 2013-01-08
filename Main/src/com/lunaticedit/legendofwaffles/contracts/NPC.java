package com.lunaticedit.legendofwaffles.contracts;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.lunaticedit.legendofwaffles.enums.Demeanor;
import com.lunaticedit.legendofwaffles.enums.Facing;

public interface NPC {
    public Demeanor getDemeanor();
    public Facing getFacingDirection();
    public void setFacingDirection(Facing value);
    Vector2 getVelocity();
    void setVelocity(Vector2 velocity);
    Body getBody();
}
