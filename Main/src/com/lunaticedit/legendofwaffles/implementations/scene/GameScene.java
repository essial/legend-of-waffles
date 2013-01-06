package com.lunaticedit.legendofwaffles.implementations.scene;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lunaticedit.legendofwaffles.services.StageServices;
import com.lunaticedit.legendofwaffles.contracts.Scene;
import com.lunaticedit.legendofwaffles.factories.RepositoryFactory;
import com.lunaticedit.legendofwaffles.factories.SpriteBatchFactory;
import com.lunaticedit.legendofwaffles.factories.StageFactory;
import com.lunaticedit.legendofwaffles.helpers.Constants;
import com.lunaticedit.legendofwaffles.implementations.generators.TilesetGraphicsGenerator;

public final class GameScene implements Scene {

    private boolean _loading;
    private String _currentStage;

    public GameScene() {

        _loading = true;
        _currentStage = "";

        // Bootstrap the stage - load in stage defaults (starting stage, starting
        // player location, etc...
        (new StageServices(new RepositoryFactory(), new StageFactory()))
                .bootstrap();
    }

    @Override
    public void render() {
        if (!_loading)
        { renderGame(); }
        else { showLoading(); }
    }

    @Override
    public void update() {
        // NOTE: On slow devices, it may take a while to load a stage, so we want to make
        // sure we get to render the loading message before actually loading. This is why
        // this check here actually loads the stage.
        if (_loading) {
            loadStage();
            _loading = false;
        }

    }

    private void loadStage() {
        // Load the stage
        (new StageServices(new RepositoryFactory(), new StageFactory()))
                .loadStage(_currentStage);
    }

    private void showLoading() {
        final SpriteBatch s = new SpriteBatchFactory().generate();
        final TilesetGraphicsGenerator g = (new TilesetGraphicsGenerator());
        final int centerX = (Constants.GameWidth / 2);

        g.drawText(
                centerX - ((8 * 16) / 2),
                (Constants.GameHeight / 2) - (Constants.TileSize / 2),
                "LOADING PLZ WAIT"
        );
    }

    private void renderGame() {

    }
}
