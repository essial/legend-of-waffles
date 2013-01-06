package com.lunaticedit.legendofwaffles.implementations.stageobject;


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
import com.lunaticedit.legendofwaffles.implementations.Player;
import com.lunaticedit.legendofwaffles.physics.HitHandler;
import com.lunaticedit.legendofwaffles.physics.HitWatcher;
import com.lunaticedit.legendofwaffles.physics.Physics;
import org.w3c.dom.Element;

public class ItemBox implements StageObject, Renderable, Processable, HitHandler {
    private int _posX;
    private int _posY;
    private boolean _opened;
    private int _hitsLeft;
    private HitWatcher _hitWatcher;
    private double _lastHit = System.currentTimeMillis();

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
                .getRenderables()
                .add(this);

        (new RepositoryFactory())
                .generate()
                .getProcessables()
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
        return _opened
                ? new Point(7, 0)
                : new Point(8, 0);
    }

    @Override
    public void process() {

    }

    @Override
    public void HitOccurred(final Body body1, final Body body2) {

        if (_opened)
        { return; }

        if ((System.currentTimeMillis() - _lastHit) < 250)
        { return; }

        _lastHit = System.currentTimeMillis();
        if (!Player.getInstance().involved(body1, body2))
        { return; }

        if (Player.getInstance().getY() < _posY)
        { return; }

        MusicPlayer.playSound(SoundEffect.BoxHit);
        if (--_hitsLeft == 0) {
            _opened = true;
            Physics.getInstance().removeHitWatcher(_hitWatcher);
        }

        // TODO: Generate coin

        /*
        Messenger.getInstance().pushMessage(new Message(MessageClass.Stage, MessageType.CoinGenerated,
                new Coin(_tileX * Constants.GAME_TILESIZE, (_tileY - 1) * Constants.GAME_TILESIZE,
                        new Vector2((r.nextFloat() * 0.5f) - 0.25f, -((r.nextFloat() * 0.5f) + 1.0f)))));
        */

    }

    private void initializePhysics() {
        final float meterSize = Physics.toMeters(Constants.TileSize);
        final float halfTile = meterSize / 2.0f;
        BodyDef groundBodyDef = new BodyDef();
        // TODO: Could convert to using direct X/Y position instead of converting back and forth like this...
        groundBodyDef.position.set(
                (meterSize * (float)(_posX / Constants.TileSize)) - halfTile,
                (meterSize * (float)(_posY / Constants.TileSize)) - halfTile);
        final Body _body = Physics.getInstance().createBody(groundBodyDef);
        PolygonShape groundBox = new PolygonShape();
        groundBox.setAsBox(halfTile, halfTile);
        _body.createFixture(groundBox, 0.0f);
        _hitWatcher = new HitWatcher(this, _body);
        Physics.getInstance().registerHitWacher(_hitWatcher);
    }

}
