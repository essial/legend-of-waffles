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
                .draw(_texture, -128f, -110f);

    }

    @Override
    public void update() {

    }
}
