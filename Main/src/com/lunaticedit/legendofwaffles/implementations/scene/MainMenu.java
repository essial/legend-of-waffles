package com.lunaticedit.legendofwaffles.implementations.scene;

import com.badlogic.gdx.graphics.Texture;
import com.lunaticedit.legendofwaffles.contracts.Scene;
import com.lunaticedit.legendofwaffles.factories.SpriteBatchFactory;
import com.lunaticedit.legendofwaffles.helpers.Constants;

public final class MainMenu implements Scene {
    private final Texture _texture;
    public MainMenu() {
        _texture = new Texture(Constants.MainMenuBackgroundFile);
    }

    @Override
    public void render() {
        (new SpriteBatchFactory())
                .generate()
                .draw(
                    _texture,
                    (-(Constants.GameWidth / 2)) +
                            ((Constants.GameWidth - _texture.getWidth()) / 2),
                    (-(Constants.GameHeight / 2)) +
                            (((Constants.GameHeight - _texture.getHeight()) / 2)) +
                            Constants.VerticalReduce
                );
    }

    @Override
    public void update() {

    }
}
