package com.lunaticedit.legendofwaffles.implementations.graphicsgenerator;

import com.badlogic.gdx.graphics.Texture;
import com.lunaticedit.legendofwaffles.contracts.GraphicsGenerator;
import com.lunaticedit.legendofwaffles.factories.SpriteBatchFactory;
import com.lunaticedit.legendofwaffles.helpers.Constants;
import com.lunaticedit.legendofwaffles.helpers.Point;

public class TilesetGraphicsGenerator implements GraphicsGenerator {
    private static Texture _texture;
    private static Point[] _tilePoints; // Fast lookup table for offsets

    public TilesetGraphicsGenerator() {

        // Load the texture into a texture object
        if (_texture == null) {
            _texture = new Texture(Constants.TilesetFile);
        }

        // Create a fast lookup table for tile offsets by tile number
        if (_tilePoints == null) {
            final int tilesPerRow = getTilesPerRow();
            final int tilesPerCol = _texture.getHeight() / Constants.TileSize;
            _tilePoints = new Point[tilesPerRow * tilesPerCol];
            for (int i = 0; i < (tilesPerRow * tilesPerCol); i++) {
                final int tileX = i % tilesPerRow;
                final int tileY = (i - tileX) / tilesPerRow;
                final int startX = tileX * Constants.TileSize;
                final int startY = tileY * Constants.TileSize;
                _tilePoints[i] = new Point(startX, startY);
            }
        }
    }

    @Override
    public void drawTile(final int x, final int y, final int tileNum) {
        final float xPos = -(Constants.GameWidth  / 2) + x;
        final float yPos =  ((Constants.GameHeight - Constants.VerticalReduce) / 2) - y;

        (new SpriteBatchFactory()).generate().draw(
                _texture,
                xPos,
                yPos,
                Constants.TileSize,
                Constants.TileSize,
                _tilePoints[tileNum].left,
                _tilePoints[tileNum].top,
                Constants.TileSize,
                Constants.TileSize,
                false,
                false
        );
    }

    @Override
    public void drawTileAbsolute(final int x, final int y, final int tileNum) {
        final float xPos = -(Constants.GameWidth  / 2) + x;
        final float yPos =  ((Constants.GameHeight - Constants.VerticalReduce) / 2) - y;

        (new SpriteBatchFactory()).generate().draw(
                _texture,
                xPos,
                yPos,
                Constants.TileSize,
                Constants.TileSize,
                x,
                y,
                Constants.TileSize,
                Constants.TileSize,
                false,
                false
        );
    }

    @Override
    public void drawText(final int x, final int y, final String text) {
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == ' ') { continue; }
            drawTile(x + (i * Constants.TileSize), y, Constants.FontStartTile + (text.charAt(i) - '!'));
        }
    }

    @Override
    public int getTilesPerRow() {
        return _texture.getWidth() / Constants.TileSize;
    }
}
