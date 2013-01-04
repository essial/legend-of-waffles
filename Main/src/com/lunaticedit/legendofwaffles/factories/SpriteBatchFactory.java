package com.lunaticedit.legendofwaffles.factories;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public final class SpriteBatchFactory {
    private static SpriteBatch _spriteBatch;

    public SpriteBatch generate() {
        if (_spriteBatch == null) {
            _spriteBatch = new SpriteBatch();
        }
        return _spriteBatch;
    }
}
