package com.lunaticedit.legendofwaffles.consumers;

import com.badlogic.gdx.math.Rectangle;
import com.lunaticedit.legendofwaffles.contracts.GraphicsGenerator;
import com.lunaticedit.legendofwaffles.contracts.Renderable;
import com.lunaticedit.legendofwaffles.contracts.Repository;
import com.lunaticedit.legendofwaffles.factories.RepositoryFactory;
import com.lunaticedit.legendofwaffles.helpers.Constants;
import com.lunaticedit.legendofwaffles.implementations.Player;
import com.lunaticedit.legendofwaffles.implementations.generators.TilesetGraphicsGenerator;

public final class RenderConsumer {
    Repository _repository;
    private Rectangle _viewport;

    public RenderConsumer(final RepositoryFactory repositoryFactory) throws Exception {
            _repository = repositoryFactory.generate();
    }

    /**
     * Renders graphics to the screen
     */
    public void render() {

        // Render the scene
        _repository
                .getScene()
                .render();

        // Determine the bounds of the screen
        final Rectangle screenBounds = new Rectangle(
            Math.max(0, Player.getInstance().getX() - (Constants.GameWidth / 2)),
            Math.max(0, Player.getInstance().getY() - (Constants.GameHeight / 2)),
            Constants.GameWidth,
            Constants.GameHeight
        );

        GraphicsGenerator g = new TilesetGraphicsGenerator();

        // Render all renderable objects
        for(Renderable r :_repository.getRenderables()){

            // Don't render an object if it is invisible
            if (!r.getVisible())
            { continue; }

            // Also don't render it if it is not visible on the screen
            if (((screenBounds.y + screenBounds.height < r.getY())) ||
                ((screenBounds.x + screenBounds.width) < r.getX()) ||
                ((r.getX() + (r.getTileSize().width * Constants.TileSize)) < screenBounds.y) ||
                ((r.getY() + (r.getTileSize().height * Constants.TileSize)) < screenBounds.x)
            ) { continue; }

            int renderX;
            int renderY = r.getY() - (int)screenBounds.y;

            for(int y = 0; y < r.getTileSize().height; y++) {
                renderX = r.getX() - (int)screenBounds.x;
                for (int x = 0; x < r.getTileSize().width; x++ ) {
                    g.drawTileAbsolute(
                        renderX,
                        renderY,
                        (r.getTileOrigin().left + x) + ((r.getTileOrigin().top + y) * g.getTilesPerRow())
                    );
                    renderX += Constants.TileSize;
                }
                renderY += Constants.TileSize;
            }




        }

    }
}
