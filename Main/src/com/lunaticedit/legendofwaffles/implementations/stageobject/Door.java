package com.lunaticedit.legendofwaffles.implementations.stageobject;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.lunaticedit.legendofwaffles.contracts.Processable;
import com.lunaticedit.legendofwaffles.contracts.Renderable;
import com.lunaticedit.legendofwaffles.contracts.StageObject;
import com.lunaticedit.legendofwaffles.factories.RepositoryFactory;
import com.lunaticedit.legendofwaffles.helpers.Constants;
import com.lunaticedit.legendofwaffles.helpers.Dimension;
import com.lunaticedit.legendofwaffles.helpers.Point;
import com.lunaticedit.legendofwaffles.physics.HitHandler;
import com.lunaticedit.legendofwaffles.physics.HitWatcher;
import com.lunaticedit.legendofwaffles.physics.Physics;
import org.w3c.dom.Element;

public class Door implements StageObject, Renderable, Processable, HitHandler {
    private boolean _opened;
    private int _posX;
    private int _posY;
    private Body _body;
    private HitWatcher _hitWatcher;
    private long _openTime;
    private int _curFrame;

    @Override
    public void processXML(final Element element) {
        _posX = (Integer.parseInt(element.getAttribute("x")));
        _posY = (Integer.parseInt(element.getAttribute("y"))) - 8;
        _opened = false;
        _curFrame = 0;
        initializePhysics();
        (new RepositoryFactory()).generate().getObjects().add(this);
    }
    @Override
    public void process() {
        if (!_opened) { return; }
        if (_opened && ((System.currentTimeMillis() - _openTime) >= 5000)) {
            final long timeDiff = (System.currentTimeMillis() - _openTime);
            if (timeDiff < 5100) { _curFrame = 4; return; }
            if (timeDiff < 5200) { _curFrame = 3; return; }
            if (timeDiff < 5300) { _curFrame = 2; return; }
            if (timeDiff < 5400) { _curFrame = 1; return; }
            _curFrame = 0;
            _opened = false;
            initializePhysics();
            return;
        }
        if (_opened && ((System.currentTimeMillis() - _openTime) >= 500)) {
            if (_curFrame == -1) { return; }
            _curFrame = -1;
            finalizePhysics();
            return;
        }
        final long timeDiff = (System.currentTimeMillis() - _openTime);
        if (timeDiff < 100) { _curFrame = 1; return; }
        if (timeDiff < 200) { _curFrame = 2; return; }
        if (timeDiff < 300) { _curFrame = 3; return; }
        if (timeDiff < 400) { _curFrame = 4; return; }
        _curFrame = 5;
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
        return (_curFrame != -1);
    }
    @Override
    public Dimension getTileSize() {
        return new Dimension(1,2);
    }
    @Override
    public Point getTileOrigin() {
        switch(_curFrame) {
            case 1: return new Point(9, 19);
            case 2: return new Point(10, 19);
            case 3: return new Point(11, 19);
            case 4: return new Point(12, 19);
            case 5: return new Point(13, 19);
            default: return new Point(8, 19);
        }

    }
    @Override
    public void hitOccurred(final Body body1, final Body body2) {
        if (_opened) { return; }
        if (!(new RepositoryFactory()).generate().getPlayer().involved(body1, body2)) { return; }
        _opened = true;
        _openTime = System.currentTimeMillis();
    }
    private void initializePhysics() {
        final float meterSize = Physics.toMeters(Constants.TileSize);
        final float halfTile = meterSize / 2.0f;
        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.position.set(
                (meterSize * (_posX / Constants.TileSize)) - halfTile,
                (meterSize * (_posY / Constants.TileSize)) - meterSize);
        _body = Physics.getInstance().createBody(groundBodyDef);
        PolygonShape groundBox = new PolygonShape();
        groundBox.setAsBox(halfTile, meterSize);
        _body.createFixture(groundBox, 0.0f);

        _hitWatcher = new HitWatcher(this, _body);
        Physics.getInstance().registerHitWacher(_hitWatcher);
    }
    private void finalizePhysics() {
        Physics.getInstance().removeHitWatcher(_hitWatcher);
        _hitWatcher = null;
        Physics.getInstance().removeBody(_body);
        _body = null;
    }
}
