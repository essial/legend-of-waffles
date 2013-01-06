package com.lunaticedit.legendofwaffles.physics;

import com.badlogic.gdx.physics.box2d.Body;

public final class HitWatcher {
    private final HitHandler _hitHandler;
    private final Body _body;

    public HitWatcher(final HitHandler hitHandler, final Body body) {
        _hitHandler = hitHandler;
        _body = body;
    }

    public HitHandler getHitHandler() {
        return _hitHandler;
    }

    public Body getBody() {
        return _body;
    }
}
