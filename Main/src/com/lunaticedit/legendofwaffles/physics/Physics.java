package com.lunaticedit.legendofwaffles.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.lunaticedit.legendofwaffles.helpers.Constants;

import java.util.ArrayList;
import java.util.Iterator;

public final class Physics implements ContactListener {
    private static Physics _physics = null;

    private final World _world;
    private final ArrayList<HitWatcher> _hitWatchers;
    private final ArrayList<HitWatcher> _hitWatcherRemovalQueue;

    private Physics() {
        _world = new World(new Vector2(0, Constants.PhysicsGravity), true);
        _hitWatchers = new ArrayList<HitWatcher>();
        _hitWatcherRemovalQueue = new ArrayList<HitWatcher>();
        _world.setContactListener(this);
    }

    public synchronized void registerHitWacher(final HitWatcher hitWatcher) {
        _hitWatchers.add(hitWatcher);
    }

    public synchronized void removeHitWatcher(final HitWatcher hitWatcher) {
        _hitWatcherRemovalQueue.add(hitWatcher);
    }

    public static Physics getInstance() {
        if (_physics == null) {
            _physics = new Physics();
        }
        return _physics;
    }

    public static float toMeters(final int pixels) {
        return pixels * 0.02f;
    }

    public static int toPixels(final float meters) {
        return (int)(meters * 50.0f);
    }

    public Body createBody(final BodyDef bodyDef) {
        Body b = _world.createBody(bodyDef);
        b.setUserData(false);
        return b;
    }

    public synchronized void removeBody(final Body body) {
        body.setUserData(true);
    }

    public static void reset() {
        getInstance()._world.dispose();
        _physics = null;
        _physics = new Physics();
        //Player.getInstance().initializePhysics();
    }

    public synchronized void step() {

        // Please note that when we remove bodies, there is a chance that the bodies
        // won't actually exist anymore. This typically happens when stages have changed while
        // dynamic bodies are in play in a thread. This will ensure that this special scenario
        // is handled -- by checking the bodies before we actually remove them.

        if (!_world.isLocked()){
            for(HitWatcher hitWatcher : _hitWatcherRemovalQueue) {
                _hitWatchers.remove(hitWatcher);
            }
            _hitWatcherRemovalQueue.clear();

            // Make sure this body still actually exists...
            Iterator<Body> bi = _world.getBodies();

            while(bi.hasNext() && !_world.isLocked()) {
                Body b = bi.next();
                if (b == null) {
                    continue;
                }

                if (!((Boolean) b.getUserData())) {
                    continue;
                }

                final ArrayList<JointEdge> list = b.getJointList();
                while ((list.size() > 0) && !_world.isLocked()) {
                    _world.destroyJoint(list.get(0).joint);
                }

                if (!_world.isLocked()) {
                    _world.destroyBody(b);
                }

                break;
            }

        }

        final int _velocityIterations = 12;
        final int _positionIterations = 4;
        _world.step(Constants.PhysicsRenderTime, _velocityIterations, _positionIterations);

    }

    @Override
    public void beginContact(final Contact contact) {
        for (final HitWatcher hitWatcher : _hitWatchers) {

            if (
                    (hitWatcher.getBody() != contact.getFixtureA().getBody()) &&
                            (hitWatcher.getBody() != contact.getFixtureB().getBody())
                    ) { continue; }

            hitWatcher.getHitHandler().HitOccurred(
                    contact.getFixtureA().getBody(),
                    contact.getFixtureB().getBody()
            );
        }
    }

    public Body createHumanoidBody(final int x, final int y) {
        return createHumanoidBody(x, y, 0.4f, 2.0f);
    }

    public Body createHumanoidBody(final int x, final int y,
                                   final float friction, final float density) {
        Body result;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(Physics.toMeters(x), Physics.toMeters(y));
        result = Physics.getInstance().createBody(bodyDef);
        CircleShape dynamicBox = new CircleShape();
        float width = Physics.toMeters(Constants.TileSize) / 2.0f;
        dynamicBox.setRadius(width);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = dynamicBox;
        fixtureDef.density = density;
        fixtureDef.friction = friction;
        result.createFixture(fixtureDef);
        result.setFixedRotation(true);
        result.setUserData(false);

        return result;
    }

    public Body createRectangularBody(final int xPos, final int yPos, final int width, final int height) {
        final int centerWidth = width / 2;
        final int centerHeight = height / 2;
        final int centerX = xPos + centerWidth;
        final int centerY = yPos + centerHeight;
        final BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.type = BodyDef.BodyType.StaticBody;
        groundBodyDef.position.set(Physics.toMeters(centerX), Physics.toMeters(centerY));
        final Body groundBody = Physics.getInstance().createBody(groundBodyDef);
        final PolygonShape groundBox = new PolygonShape();
        groundBox.setAsBox(Physics.toMeters(centerWidth), Physics.toMeters(centerHeight));
        groundBody.createFixture(groundBox, 0.0f);
        groundBody.setUserData(false);
        return groundBody;
    }

    @Override
    public void endContact(final Contact contact) {
    }

    @Override
    public void preSolve(final Contact contact, final Manifold manifold) {
    }

    @Override
    public void postSolve(final Contact contact, final ContactImpulse contactImpulse) {
    }
}
