package com.lunaticedit.legendofwaffles.base;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.lunaticedit.legendofwaffles.contracts.Actor;
import com.lunaticedit.legendofwaffles.contracts.Animation;
import com.lunaticedit.legendofwaffles.contracts.Attackable;
import com.lunaticedit.legendofwaffles.contracts.Renderable;
import com.lunaticedit.legendofwaffles.helpers.Dimension;
import com.lunaticedit.legendofwaffles.physics.Physics;

public abstract class ActorBase implements Actor, Animation, Renderable, Attackable {
    protected Body _body;
    protected long _animationTime;
    protected int _currentFrame;
    protected boolean _staggered;
    protected long _attackTime;
    protected long _attackedTime;
    protected int _health;
    protected int _deadX;
    protected int _deadY;
    protected boolean _final;

    public int getX() {
        if (_body == null) { return 0; }
        if (_final)        { return _deadX; }
        final Vector2 position = _body.getPosition();
        return Physics.toPixels(position.x) + 5;
    }
    public int getY() {
        if (_body == null) { return 0; }
        if (_final)        { return _deadY; }
        final Vector2 position = _body.getPosition();
        return Physics.toPixels(position.y) - 3;
    }
    public boolean getVisible()
    { return true; }
    public int getCurrentFrame()
    { return _currentFrame; }
    public void setCurrentFrame(final int value)
    { _currentFrame = value; }
    public long getAnimationTime()
    { return _animationTime; }
    public void setAnimationTime(final long value)
    { _animationTime = value; }
    public Dimension getTileSize()
    { return new Dimension(1, 2); }
    public int getHealth()
    { return _health; }
    public void setHealth(final int value)
    { _health = value; }
    public boolean getStaggered()
    { return _staggered; }
    public void setStaggered(final boolean value)
    { _staggered = value; }
    public boolean getFinal()
    { return _final; }
    public long getAttackTime()
    { return _attackTime; }
    public void setAttackTime(final long value)
    { _attackTime = value; }
    public long getAttackedTime()
    { return _attackedTime; }
    public void setAttackedTime(final long value)
    { _attackedTime = value; }

}
