package com.lunaticedit.legendofwaffles.implementations.ui;

import com.lunaticedit.legendofwaffles.factories.RepositoryFactory;
import com.lunaticedit.legendofwaffles.helpers.Constants;
import com.lunaticedit.legendofwaffles.implementations.Input;
import com.lunaticedit.legendofwaffles.implementations.graphicsgenerator.TilesetGraphicsGenerator;

public class InputView implements View {
    private final String[] _choices;
    private final int _x;
    private final int _y;
    private final int _width;
    private final int _height;
    private int _selection;
    private final long _startTime;
    private int _result;

    public InputView(final String[] choices, final int x, final int y) {
        _selection = 0;
        _choices = choices;
        _x = x;
        _y = y;
        int w = 0;
        _height = choices.length + 1;
        for(String choice : choices) {
            if (choice.length() > w) { w = choice.length(); }
        }
        _width = w + 2;
        _startTime = System.currentTimeMillis();
    }

    @Override
    public void Draw() {
        final TilesetGraphicsGenerator g = (new TilesetGraphicsGenerator());

        final long timeDiff = (System.currentTimeMillis() - _startTime);

        if (timeDiff > 250) {
            drawBorder(_x, _y, _width, _height);
            for(int i = 0; i < _choices.length; i++) {
                g.drawText(_x+(Constants.TileSize*2), _y + (8*(i+1)), _choices[i]);
            }
            g.drawTile(_x + Constants.TileSize, _y + (8*(_selection+1)), 1018);
        } else {
            drawBorder(_x, _y, (int)((float)_width * ((float)timeDiff / 250)), (int)((float)_height * ((float)timeDiff / 250.0f)));
        }
    }
    @Override
    public void Process() {
        if (System.currentTimeMillis() - _startTime < 250) { return; }
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
            for (int x = 1; x < tileWidth; x++)
            { g.drawTile(xPos + (x*Constants.TileSize),  yPos + (y*Constants.TileSize), 833); }
        }
    }
    private void moveSelectionUp() {
        if (--_selection < 0)
        { _selection = _choices.length-1; }
    }
    private void moveSelectionDown() {
        if (++_selection >= _choices.length)
        { _selection = 0; }
    }
    private void activateMenuOption() {
        final UI ui = (new RepositoryFactory()).generate().getUI();
       ui.setModalResult(_selection);
        ui.removeView(this);

    }
}
