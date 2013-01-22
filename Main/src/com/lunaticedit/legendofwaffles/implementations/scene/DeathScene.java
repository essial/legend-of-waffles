package com.lunaticedit.legendofwaffles.implementations.scene;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.lunaticedit.legendofwaffles.contracts.Scene;
import com.lunaticedit.legendofwaffles.enums.SceneType;
import com.lunaticedit.legendofwaffles.factories.RepositoryFactory;
import com.lunaticedit.legendofwaffles.factories.SceneFactory;
import com.lunaticedit.legendofwaffles.helpers.Constants;
import com.lunaticedit.legendofwaffles.implementations.Input;
import com.lunaticedit.legendofwaffles.implementations.graphicsgenerator.TilesetGraphicsGenerator;

public class DeathScene implements Scene {
    private int _menuSelection = 0;
    public DeathScene() {
        (new RepositoryFactory()).generate().getObjects().clear();
    }
    @Override
    public void render(final Rectangle screenBounds) {
        final TilesetGraphicsGenerator g = (new TilesetGraphicsGenerator());
        final int centerX = (Constants.GameWidth / 2);
        drawBorder(7, 6, 32, 5);
        g.drawText(centerX - ((8 * 13) / 2), 22, "YOU HAS DIED!");
        g.drawText(centerX - ((8 * 27) / 2), 30, "TRY AGAIN FOR GREAT JUSTICE");

        drawBorder(92, 94, 11, 5);
        g.drawText(centerX - ((8 * 8) / 2), 110, "CONTINUE");
        g.drawText(centerX - ((8 * 4) / 2), 118, "QUIT");

        if (Gdx.app.getType() == Application.ApplicationType.Android) {

        } else {
            g.drawTile(centerX - ((8 * 10) / 2), 109 + (_menuSelection * 8), 1);
            g.drawTile(centerX + ((8 * 8) / 2), 109 + (_menuSelection * 8), 1);
        }
    }
    @Override
    public void update() {
        if (Input.getInstance().getKeyState(com.badlogic.gdx.Input.Keys.DPAD_UP)) {
            Input.getInstance().setKeyState(com.badlogic.gdx.Input.Keys.DPAD_UP, false);
            moveSelectionUp();
        } else if (Input.getInstance().getKeyState(com.badlogic.gdx.Input.Keys.DPAD_DOWN)) {
            Input.getInstance().setKeyState(com.badlogic.gdx.Input.Keys.DPAD_DOWN, false);
            moveSelectionDown();
        } else if (Input.getInstance().getKeyState(com.badlogic.gdx.Input.Keys.ENTER) ||
                Input.getInstance().getKeyState(com.badlogic.gdx.Input.Keys.BUTTON_A)) {
            Input.getInstance().setKeyState(com.badlogic.gdx.Input.Keys.ENTER, false);
            Input.getInstance().setKeyState(com.badlogic.gdx.Input.Keys.BUTTON_A, false);
            activateMenuOption();
        }
    }
    private void moveSelectionUp() {
        if (--_menuSelection < 0)
        { _menuSelection = 1; }
    }
    private void moveSelectionDown() {
        if (++_menuSelection > 1)
        { _menuSelection = 0; }
    }
    private void activateMenuOption() {
        switch (_menuSelection) {
            case 0: RetryGame(); break;
            case 1: QuitGame(); break;
            default: return;
        }
    }
    private void QuitGame() {
        (new RepositoryFactory()).generate().setScene(new SceneFactory().generateScene(SceneType.MainMenu));
    }
    private void RetryGame() {
        int health = (new RepositoryFactory()).generate().getPlayer().getMaxHealth();
        (new RepositoryFactory()).generate().getPlayer().setHealth(health);
        (new RepositoryFactory()).generate().setScene(new SceneFactory().generateScene(SceneType.Game));
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
