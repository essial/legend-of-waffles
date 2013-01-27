package com.lunaticedit.legendofwaffles.implementations.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.lunaticedit.legendofwaffles.contracts.Scene;
import com.lunaticedit.legendofwaffles.enums.SceneType;
import com.lunaticedit.legendofwaffles.factories.RepositoryFactory;
import com.lunaticedit.legendofwaffles.factories.SceneFactory;
import com.lunaticedit.legendofwaffles.factories.SpriteBatchFactory;
import com.lunaticedit.legendofwaffles.helpers.Constants;
import com.lunaticedit.legendofwaffles.implementations.Input;
import com.lunaticedit.legendofwaffles.implementations.MusicPlayer;
import com.lunaticedit.legendofwaffles.implementations.graphicsgenerator.TilesetGraphicsGenerator;

public final class MainMenu implements Scene {
    private final Texture _texture;
    private int _menuSelection = 0;

    public MainMenu() {
        _texture = new Texture(Constants.MainMenuBackgroundFile);
        MusicPlayer.getInstance().stopSong();
        MusicPlayer.getInstance().playSong(Constants.MainMenuSong);
        (new RepositoryFactory()).generate().getUI().reset();
        (new RepositoryFactory()).generate().getUI().createInputView(
                (new String[] {"NEW GAME", "CONTINUE", "CONFIG", "QUIT"}),
                16, 32
        );
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
        if (Input.getInstance().getKeyState(Keys.DPAD_UP)) {
            Input.getInstance().setKeyState(Keys.DPAD_UP, false);
            moveSelectionUp();
        } else if (Input.getInstance().getKeyState(Keys.DPAD_DOWN)) {
            Input.getInstance().setKeyState(com.badlogic.gdx.Input.Keys.DPAD_DOWN, false);
            moveSelectionDown();
        } else if (Input.getInstance().getKeyState(Keys.ENTER) ||
                Input.getInstance().getKeyState(Keys.BUTTON_A)) {
            Input.getInstance().setKeyState(Keys.ENTER, false);
            Input.getInstance().setKeyState(Keys.BUTTON_A, false);
            activateMenuOption();
        }
    }
    private void moveSelectionUp() {
        if (--_menuSelection < 0)
        { _menuSelection = 3; }
    }
    private void moveSelectionDown() {
        if (++_menuSelection > 3)
        { _menuSelection = 0; }
    }
    private void activateMenuOption() {
        switch (_menuSelection) {
            case 0: startNewGame(); break;
            case 1: return;
            case 2: return;
            case 3: Gdx.app.exit();
            default: return;
        }
    }
    private void startNewGame() {
        (new RepositoryFactory())
                .generate()
                .setScene(new SceneFactory()
                        .generateScene(SceneType.Game));
    }
    private void drawBorder(final int xPos, final int yPos, final int tileWidth, final int tileHeight) {
        final TilesetGraphicsGenerator g = (new TilesetGraphicsGenerator());
        // Draw Corners
        g.drawTile(xPos, yPos, 800);
        g.drawTile(xPos, yPos+(tileHeight * Constants.TileSize), 864);
        g.drawTile(xPos+(tileWidth*Constants.TileSize), yPos, 802);
        g.drawTile(xPos+(tileWidth*Constants.TileSize), yPos+(tileHeight * Constants.TileSize), 866);


        //Drop top and bottom
        for (int x = 1; x < tileWidth; x++) {
            g.drawTile(xPos + (x*Constants.TileSize), yPos, 801);
            g.drawTile(xPos + (x*Constants.TileSize), yPos + (tileHeight * Constants.TileSize), 865);
        }

        // Draw left and right
        for (int y = 1; y < tileHeight; y++) {
            g.drawTile(xPos,  yPos + (y*Constants.TileSize), 832);
            g.drawTile(xPos + (tileWidth*Constants.TileSize),  yPos + (y*Constants.TileSize), 834);
            for (int x = 1; x < tileWidth; x++) {
                g.drawTile(xPos + (x*Constants.TileSize),  yPos + (y*Constants.TileSize), 833);
            }
        }
    }
}
