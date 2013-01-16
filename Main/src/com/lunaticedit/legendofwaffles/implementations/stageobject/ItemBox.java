package com.lunaticedit.legendofwaffles.implementations.stageobject;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.lunaticedit.legendofwaffles.contracts.Processable;
import com.lunaticedit.legendofwaffles.contracts.Renderable;
import com.lunaticedit.legendofwaffles.contracts.StageObject;
import com.lunaticedit.legendofwaffles.enums.SoundEffect;
import com.lunaticedit.legendofwaffles.factories.RepositoryFactory;
import com.lunaticedit.legendofwaffles.helpers.Constants;
import com.lunaticedit.legendofwaffles.helpers.Dimension;
import com.lunaticedit.legendofwaffles.helpers.Point;
import com.lunaticedit.legendofwaffles.implementations.MusicPlayer;
import com.lunaticedit.legendofwaffles.implementations.repository.Player;
import com.lunaticedit.legendofwaffles.physics.HitHandler;
import com.lunaticedit.legendofwaffles.physics.HitWatcher;
import com.lunaticedit.legendofwaffles.physics.Physics;
import org.w3c.dom.Element;

import java.util.Random;

public class ItemBox implements StageObject, Renderable, Processable, HitHandler {
    private int _posX;
    private int _posY;
    private boolean _opened;
    private int _hitsLeft;
    private HitWatcher _hitWatcher;
    private final Random r;
    private double _lastHit = System.currentTimeMillis();
    private boolean _generateCoin;

    public ItemBox() {
        r = new Random();
        _generateCoin = false;
    }

    @Override
    public void processXML(final Element element) {

        // Initialize Defaults
        _posX = (Integer.parseInt(element.getAttribute("x")));
        _posY = (Integer.parseInt(element.getAttribute("y")));
        _opened = false;
        _hitsLeft = 3;

        // Attach to the repository
        (new RepositoryFactory())
                .generate()
                .getObjects()
                .add(this);

        initializePhysics();
    }

    @Override
    public int getX() {
        return _posX;
    }

    @Override
    public int getY() {
        return _posY;
    }

    @Override
    public boolean getVisible() {
        return true;
    }

    @Override
    public Dimension getTileSize() {
        return new Dimension(1, 1);
    }

    @Override
    public Point getTileOrigin() {
        return !_opened
                ? new Point(7, 0)
                : new Point(8, 0);
    }

    @Override
    public void process() {
        if (_generateCoin) {
            _generateCoin = false;
            new Coin(
                    getX() - (Constants.TileSize / 2),
                    getY() - Constants.TileSize,
                    new Vector2((r.nextFloat() * 0.5f) - 0.25f, -((r.nextFloat() * 0.5f) + 1.0f))
            );
        }
    }

    @Override
    public void hitOccurred(final Body body1, final Body body2) {

        if (_opened)
        { return; }

        if ((System.currentTimeMillis() - _lastHit) < 250)
        { return; }

        _lastHit = System.currentTimeMillis();

        final Player player = (new RepositoryFactory())
                .generate()
                .getPlayer();

        if (!player.involved(body1, body2))
        { return; }

        if (player.getY() < _posY)
        { return; }

        if (--_hitsLeft == 0) {
            _opened = true;
            Physics.getInstance().removeHitWatcher(_hitWatcher);
        }

        MusicPlayer.playSound(SoundEffect.BoxHit);

        // NOTE: We cannot generate a coin here because this is called during the physics' update
        // loop. Attempting to modify the bodies will cause the physics engine to throw an assert and crash.
        _generateCoin = true;
    }

    private void initializePhysics() {
        final float meterSize = Physics.toMeters(Constants.TileSize);
        final float halfTile = meterSize / 2.0f;
        BodyDef groundBodyDef = new BodyDef();
        // TODO: Could convert to using direct X/Y position instead of converting back and forth like this...
        groundBodyDef.position.set(
                (meterSize * (_posX / Constants.TileSize)) - halfTile,
                (meterSize * (_posY / Constants.TileSize)) - halfTile);
        final Body _body = Physics.getInstance().createBody(groundBodyDef);
        PolygonShape groundBox = new PolygonShape();
        groundBox.setAsBox(halfTile, halfTile);
        _body.createFixture(groundBox, 0.0f);
        _hitWatcher = new HitWatcher(this, _body);
        Physics.getInstance().registerHitWacher(_hitWatcher);
    }

}
