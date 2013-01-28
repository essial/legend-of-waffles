package com.lunaticedit.legendofwaffles.implementations.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.lunaticedit.legendofwaffles.contracts.Scene;
import com.lunaticedit.legendofwaffles.enums.SceneType;
import com.lunaticedit.legendofwaffles.factories.RepositoryFactory;
import com.lunaticedit.legendofwaffles.factories.SceneFactory;
import com.lunaticedit.legendofwaffles.factories.SpriteBatchFactory;
import com.lunaticedit.legendofwaffles.helpers.Constants;
import com.lunaticedit.legendofwaffles.implementations.MusicPlayer;
import com.lunaticedit.legendofwaffles.implementations.ui.UI;

public final class MainMenu implements Scene {
    private final Texture _texture;
    private int _menuSelection = 0;
    private final UI _ui;

    public MainMenu() {
        _ui = (new RepositoryFactory()).generate().getUI();
        _texture = new Texture(Constants.MainMenuBackgroundFile);
        MusicPlayer.getInstance().stopSong();
        MusicPlayer.getInstance().playSong(Constants.MainMenuSong);
        _ui.reset();
        _ui.createInputView((new String[]{"NEW GAME", "CONTINUE", "CONFIG", "QUIT"}), 16, 112);
    }

    @Override
    public void render(final Rectangle screenBounds) {
        final SpriteBatch s = new SpriteBatchFactory().generate();
        //final TilesetGraphicsGenerator g = (new TilesetGraphicsGenerator());
        // Render the background image
        s.draw(_texture, -128f, -110f);


        /*
        // Render the main menu options.
        drawBorder(16, 112, 10, 5);
        g.drawText(32, 120 + (8*0),  "NEW GAME");
        g.drawText(32, 120 + (8*1), "CONTINUE");
        g.drawText(32, 120 + (8*2), "CONFIG");
        g.drawText(32, 120 + (8*3), "QUIT");

        // Render the menu selector if not on a touch screen
        if (Gdx.app.getType() == Application.ApplicationType.Android) {

        } else {
            g.drawTile(24, 120 + (_menuSelection * 8), 1018);
        }
        */
    }

    @Override
    public void update() {
        if (_ui.getModalResult() > -1) {
            switch (_ui.getModalResult()) {
                case 0: startNewGame(); break;
                case 1: startContinue(); break;
                case 2: startConfig(); break;
                case 3: Gdx.app.exit(); break;
            }
        }
    }
    private void startConfig() {

    }
    private void startContinue() {

    }
    private void startNewGame() {
        (new RepositoryFactory())
                .generate()
                .setScene(new SceneFactory()
                        .generateScene(SceneType.Game));
    }
}
