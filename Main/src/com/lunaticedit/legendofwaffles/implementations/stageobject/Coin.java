package com.lunaticedit.legendofwaffles.implementations.stageobject;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.lunaticedit.legendofwaffles.contracts.Renderable;
import com.lunaticedit.legendofwaffles.enums.SoundEffect;
import com.lunaticedit.legendofwaffles.factories.RepositoryFactory;
import com.lunaticedit.legendofwaffles.helpers.Dimension;
import com.lunaticedit.legendofwaffles.helpers.Point;
import com.lunaticedit.legendofwaffles.implementations.MusicPlayer;
import com.lunaticedit.legendofwaffles.physics.HitHandler;
import com.lunaticedit.legendofwaffles.physics.HitWatcher;
import com.lunaticedit.legendofwaffles.physics.Physics;

/**
 * Created with IntelliJ IDEA.
 * User: timsarbin
 * Date: 1/6/13
 * Time: 6:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class Coin implements Renderable, HitHandler {
    private Body _body;
    private HitWatcher _hitWatcher;
    private long _genTime;

    public Coin(final int x, final int y, final Vector2 force) {

        _genTime = System.currentTimeMillis();

        // Attach to the repository
        (new RepositoryFactory())
                .generate()
                .getRenderables()
                .add(this);

        _body = Physics
                .getInstance()
                .createCircularBody(x, y, force);

        _hitWatcher = new HitWatcher(this, _body);
        Physics.getInstance().registerHitWacher(_hitWatcher);
    }

    @Override
    public int getX() {
        if (_body == null)
        { return 0; }

        final Vector2 position = _body.getPosition();
        return Physics.toPixels(position.x) + 5;
    }

    @Override
    public int getY() {
        if (_body == null)
        { return 0; }

        final Vector2 position = _body.getPosition();
        return Physics.toPixels(position.y) + 5;
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
        return new Point(1, 0);
    }

    @Override
    public void HitOccurred(final Body body1, final Body body2) {

        // We only care about contact with the player
        if (!(new RepositoryFactory())
                .generate()
                .getPlayer()
                .involved(body1, body2))
        { return; }

        if ((System.currentTimeMillis() - _genTime) < 100)
        { return; }

                // Detach from the repository
        (new RepositoryFactory())
                .generate()
                .getRenderables()
                .remove(this);

        _body.setUserData(true);
        (new RepositoryFactory())
                .generate()
                .getPlayer()
                .adjustCoins(1);
        Physics.getInstance().removeHitWatcher(_hitWatcher);
        MusicPlayer.playSound(SoundEffect.Coin);
    }
}
