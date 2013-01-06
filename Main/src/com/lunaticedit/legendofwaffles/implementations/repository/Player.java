package com.lunaticedit.legendofwaffles.implementations.repository;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.lunaticedit.legendofwaffles.contracts.Animation;
import com.lunaticedit.legendofwaffles.contracts.Processable;
import com.lunaticedit.legendofwaffles.contracts.Renderable;
import com.lunaticedit.legendofwaffles.factories.RepositoryFactory;
import com.lunaticedit.legendofwaffles.helpers.Constants;
import com.lunaticedit.legendofwaffles.helpers.Dimension;
import com.lunaticedit.legendofwaffles.helpers.Point;
import com.lunaticedit.legendofwaffles.implementations.Input;
import com.lunaticedit.legendofwaffles.physics.Physics;
import com.lunaticedit.legendofwaffles.services.AnimationService;

public class Player implements Renderable, Processable, Animation {
    private Body _body;
    private boolean _running;
    private boolean _movingLeft;
    private boolean _movingRight;
    private long _groundTimeStart;
    private boolean _animate;
    private boolean _faceLeft;
    private int _currentFrame;
    private long _animationTime;

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
        return new Point(_currentFrame, _faceLeft ? 3 : 1);
    }

    @Override
    public void process() {
        handleInput();

        final Vector2 vel = _body.getLinearVelocity();

        final float runMode = _running
                ? 1.5f
                : 0.7f;

        if (_movingLeft) {
            vel.x = -1.2f * runMode;
            _body.setLinearVelocity(vel);
        }
        if (_movingRight) {
            vel.x = 1.2f * runMode;
            _body.setLinearVelocity(vel);
        }

        if ((_movingLeft || _movingRight) && (vel.y != 0.0f)) {
            _animate = true;
        } else if ((!_movingLeft && !_movingRight) && (vel.y == 0.0f)) {
            _animate = false;
            vel.x *= 0.5f;
            _body.setLinearVelocity(vel);
        }

        double jumpVel = Math.abs(vel.y);
        if (jumpVel > 0.05f) {
            _groundTimeStart = System.currentTimeMillis();
        } else {
            if (!_movingRight && !_movingLeft && vel.x != 0.0) {

                vel.x *= 0.5;
                _body.setLinearVelocity(vel);
            }
        }
        (new AnimationService(this)).update();
    }

    private void handleInput() {

        // Running
        if (Input.getInstance().getKeyState(com.badlogic.gdx.Input.Keys.Z)) {
            _running = true;
        } else if (_running) {
            _running = false;
        }


        // Moving Left
        if (Input.getInstance().getKeyState(com.badlogic.gdx.Input.Keys.DPAD_LEFT)) {
            if (!_movingLeft) {
                _movingLeft = true;
                _movingRight = false;
                _animate = true;
                _faceLeft = true;
            }
        } else if (_movingLeft) {
            _movingLeft = false;
            _animate = false;
        }


        // Moving Right
        if (Input.getInstance().getKeyState(com.badlogic.gdx.Input.Keys.DPAD_RIGHT)) {
            if (!_movingRight) {
                _movingRight = true;
                _movingLeft = false;
                _animate = true;
                _faceLeft = false;
            }
        } else if (_movingRight) {
            _movingRight = false;
            _animate = false;
        }


        if (Input.getInstance().getKeyState(com.badlogic.gdx.Input.Keys.X)) {
            Input.getInstance().setKeyState(com.badlogic.gdx.Input.Keys.X, false);
            if (!canJump()) {
                return;
            }
            Vector2 vel = _body.getLinearVelocity();
            vel.y = -4.3f;
            vel.x = 0.0f;
            _body.applyForce(vel, _body.getWorldCenter());
        }

    }

    private boolean canJump() {
        return ((System.currentTimeMillis() - _groundTimeStart) > 35);
    }

    @Override
    public int getAnimationSpeed() {
        return (_running)
                ? 100
                : 250;
    }

    @Override
    public boolean getShouldAnimate() {
        return _animate;
    }

    @Override
    public int getCurrentFrame() {
        return canJump()
            ? _currentFrame
            : 1;
    }

    @Override
    public void setCurrentFrame(final int value) {
        _currentFrame = value;
    }

    @Override
    public long getAnimationTime() {
        return _animationTime;
    }

    @Override
    public void setAnimationTime(final long value) {
        _animationTime = value;
    }
}
