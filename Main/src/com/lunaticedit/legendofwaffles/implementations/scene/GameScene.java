package com.lunaticedit.legendofwaffles.implementations.scene;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.lunaticedit.legendofwaffles.contracts.Scene;
import com.lunaticedit.legendofwaffles.contracts.Stage;
import com.lunaticedit.legendofwaffles.factories.RepositoryFactory;
import com.lunaticedit.legendofwaffles.factories.SpriteBatchFactory;
import com.lunaticedit.legendofwaffles.factories.StageFactory;
import com.lunaticedit.legendofwaffles.helpers.Constants;
import com.lunaticedit.legendofwaffles.implementations.graphicsgenerator.TilesetGraphicsGenerator;
import com.lunaticedit.legendofwaffles.physics.Physics;
import com.lunaticedit.legendofwaffles.services.StageServices;

public final class GameScene implements Scene {
    private int _loading;
    private String _currentStage;
    private final Texture _bg1;
    private final Texture _bg2;
    private boolean _bootstrapped;

    public GameScene() {

        _bootstrapped = false;
        _loading = 0;
        _currentStage = "";

        _bg1 = new Texture(Constants.GameBG1File);
        _bg2 = new Texture(Constants.GameBG2File);
    }
    @Override
    public void render(final Rectangle screenBounds) {
        if (_loading == 2)  {
            renderGame(screenBounds);
            renderUI();
        }
        else { showLoading(); }
    }
    @Override
    public void update() {
        // NOTE: On slow devices, it may take a while to load a stage, so we want to make
        // sure we get to render the loading message before actually loading. This is why
        // this check here actually loads the stage.
        if (_loading == 0) {
            _loading = 1;
        } else if (_loading == 1) {
            loadStage();

            // Attach the player's activities to the repository
            (new RepositoryFactory())
                    .generate()
                    .getPlayer()
                    .attach();

            _loading = 2;
            return;
        }

        Physics.getInstance().step();
    }
    private void loadStage() {

        // Bootstrap the stage - load in stage defaults (starting stage, starting
        // player location, etc...
        if (!_bootstrapped) {
            long secs = System.currentTimeMillis();
            (new StageServices(new RepositoryFactory(), new StageFactory()))
                    .bootstrap();
            while ((System.currentTimeMillis() - secs) < 5000) {

            }
        }
        // Load the stage
        (new StageServices(new RepositoryFactory(), new StageFactory()))
                .loadStage(_currentStage);
    }
    private void showLoading() {
        final TilesetGraphicsGenerator g = (new TilesetGraphicsGenerator());
        final int centerX = (Constants.GameWidth / 2);

        g.drawText(
                centerX - ((8 * 16) / 2),
                (Constants.GameHeight / 2) - (Constants.TileSize / 2),
                "LOADING PLZ WAIT"
        );
    }
    private void renderGame(final Rectangle screenBounds) {
        final SpriteBatch s = new SpriteBatchFactory().generate();
        final int bgX = (int)(screenBounds.x % 1600) / 4;
        final int bgY = (int)(screenBounds.y - 1000) / 4;
        s.draw(_bg1, -140f - bgX, -110f + bgY);
        s.draw(_bg1, 260f - bgX, -110f + bgY);
        //s.draw(_bg2, -140f, -82f);

        final Stage stage = (new StageFactory()).generate();

        final int pixelOffX = ((int)screenBounds.x % Constants.TileSize);
        final int pixelOffY = ((int)screenBounds.y % Constants.TileSize);
        final int tileOffX = (int)screenBounds.x / Constants.TileSize;
        final int tileOffY = (int)screenBounds.y / Constants.TileSize;

        final int tilesPerRow = stage.getMapWidth();
        final int[] tileData = stage.getTileData();



        int yPos = (-1 * Constants.TileSize) - pixelOffY;
        for (int y = -1; y < (Constants.GameHeight / Constants.TileSize) + 2; y++) {
            int xPos = (-1 * Constants.TileSize) - pixelOffX;
            for (int x = -1; x < (Constants.GameWidth / Constants.TileSize) + 1; x++) {

                if (x + tileOffX < 0) {continue;}
                if (y + tileOffY < 0) {continue;}

                final int tileNum = tileData[x + tileOffX + ((y + tileOffY) * tilesPerRow)];

                if (tileNum == -1) {
                    xPos += Constants.TileSize;
                    continue;
                }

                (new TilesetGraphicsGenerator())
                        .drawTile(
                                xPos,
                                yPos,
                                tileNum
                        );
                xPos += Constants.TileSize;
            }
            yPos += Constants.TileSize;
        }

    }
    private void renderUI() {
        final TilesetGraphicsGenerator g = (new TilesetGraphicsGenerator());
        final int healthMax = (new RepositoryFactory()).generate().getPlayer().getMaxHealth();
        final int health = (new RepositoryFactory()).generate().getPlayer().getHealth();

        drawBorder(Constants.GameWidth - (Constants.TileSize * 3), -2, 2, (healthMax / 2) + 3);
        g.drawText(Constants.GameWidth - (Constants.TileSize * 2), 6, "L");
        int y = Constants.TileSize * 2;

        for (int i = healthMax; i > 0; i--) {
            g.drawTile(Constants.GameWidth - 16, y, (i > health) ? 63 : 31);
            y += (Constants.TileSize / 2);
        }

        drawBorder(Constants.GameWidth - (Constants.TileSize * 14), -2, 11, 2);
        g.drawText(Constants.GameWidth - (Constants.TileSize * 13), 6, "COINS");
        int coins = (new RepositoryFactory()).generate().getPlayer().getCoins();
        g.drawText(Constants.GameWidth - (Constants.TileSize * 7), 6, String.valueOf(coins));
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
