package com.lunaticedit.legendofwaffles.implementations.repository;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.lunaticedit.legendofwaffles.contracts.Processable;
import com.lunaticedit.legendofwaffles.contracts.Renderable;
import com.lunaticedit.legendofwaffles.factories.RepositoryFactory;
import com.lunaticedit.legendofwaffles.helpers.Constants;
import com.lunaticedit.legendofwaffles.helpers.Dimension;
import com.lunaticedit.legendofwaffles.helpers.Point;
import com.lunaticedit.legendofwaffles.physics.Physics;

public class Player implements Renderable, Processable {
    private Body _body;

    /**
     * Gets the X position of the player, in pixels.
     */
    public int getX() {
        if (_body == null)
        { return 0; }

        final Vector2 position = _body.getPosition();
        return Physics.toPixels(position.x) + 5;
    }

    /**
     * Gets the Y position of the player, in pixels.
     */
    public int getY() {
        if (_body == null)
        { return 0; }

        final Vector2 position = _body.getPosition();
        return Physics.toPixels(position.y) - 3;
    }

    public boolean involved(Body body1, Body body2) {
        return ((body1 == _body) || (body2 == _body));
    }

    public void initializePhysics() {
        _body = Physics.getInstance().createHumanoidBody(getX(), getY(), 0.0f, 1.0f);
        _body.setFixedRotation(true);
    }

    public void setPosition(final int x, final int y) {
        final float meterSize = Physics.toMeters(Constants.TileSize);
        final float halfTile = meterSize / 2.0f;
        _body.setTransform(new Vector2(Physics.toMeters(x) - halfTile, Physics.toMeters(y + 10) - meterSize), 0.0f);
        _body.setLinearVelocity(new Vector2(0.0f, 0.0f));
    }


    public void attach() {
        (new RepositoryFactory())
                .generate()
                .getProcessables()
                .add(this);

        (new RepositoryFactory())
                .generate()
                .getRenderables()
                .add(this);
    }

    @Override
    public boolean getVisible() {
        return true;
    }

    @Override
    public Dimension getTileSize() {
        return new Dimension(1, 2);
    }

    @Override
    public Point getTileOrigin() {
        return new Point(0, 1);
    }

    @Override
    public void process() {
        // TODO: Processing logic
    }
}
