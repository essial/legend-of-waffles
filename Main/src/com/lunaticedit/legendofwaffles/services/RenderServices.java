package com.lunaticedit.legendofwaffles.services;

import com.badlogic.gdx.math.Rectangle;
import com.lunaticedit.legendofwaffles.contracts.Attackable;
import com.lunaticedit.legendofwaffles.contracts.GraphicsGenerator;
import com.lunaticedit.legendofwaffles.contracts.Renderable;
import com.lunaticedit.legendofwaffles.contracts.Repository;
import com.lunaticedit.legendofwaffles.factories.RepositoryFactory;
import com.lunaticedit.legendofwaffles.helpers.Constants;
import com.lunaticedit.legendofwaffles.implementations.graphicsgenerator.TilesetGraphicsGenerator;
import com.lunaticedit.legendofwaffles.implementations.repository.Player;

public final class RenderServices {
    Repository _repository;

    public RenderServices(final RepositoryFactory repositoryFactory) throws Exception {
            _repository = repositoryFactory.generate();
    }

    /**
     * Renders graphics to the screen
     */
    public void render() {

        final Player player = (new RepositoryFactory())
                .generate()
                .getPlayer();

        // Determine the bounds of the screen
        final Rectangle screenBounds = new Rectangle(
            Math.max(0, player.getX() - (Constants.GameWidth / 2)),
            Math.max(0, (player.getY() - 50) - (Constants.GameHeight / 2)),
            Constants.GameWidth,
            Constants.GameHeight
        );

        // Render the scene
        _repository
                .getScene()
                .render(screenBounds);

        GraphicsGenerator g = new TilesetGraphicsGenerator();

        // Render all renderable objects
        for(Object rx :_repository.getObjects()){
            if (!(rx instanceof  Renderable))
            { continue; }

            Renderable r = (Renderable)rx;

            int actualX = r.getX() - (int)screenBounds.x;
            int actualY = r.getY() - (int)screenBounds.y;

            // Don't render an object if it is invisible
            if (!r.getVisible())
            { continue; }

            // Also don't render it if it is not visible on the screen
            if (((screenBounds.y + screenBounds.height) < r.getY()) ||
                ((screenBounds.x + screenBounds.width) < r.getX()) ||
                ((r.getX() + (r.getTileSize().width * Constants.TileSize)) < screenBounds.x) ||
                ((r.getY() + (r.getTileSize().height * Constants.TileSize)) < screenBounds.y)
            ) { continue; }

            int renderX;
            int renderY = actualY;

            for(int y = 0; y < r.getTileSize().height; y++) {
                renderX = actualX;
                for (int x = 0; x < r.getTileSize().width; x++ ) {
                    g.drawTile(
                            renderX,
                            renderY,
                            (r.getTileOrigin().left + x) + ((r.getTileOrigin().top + y) * g.getTilesPerRow())
                    );
                    renderX += Constants.TileSize;
                }
                renderY += Constants.TileSize;
            }

            if (r instanceof Attackable) {
                Attackable a = (Attackable)r;

                if (!(new AttackableServices(a)).isAttacking())
                { continue; }


                switch (a.getWeaponType()) {
                    case None:
                        break;
                    case ShortSword: {
                        switch (a.getFacingDirection()) {
                            case Left: {
                                g.drawTile(
                                        (actualX - (Constants.TileSize)) + 3,
                                        (actualY + (Constants.TileSize / 2)) + 2,
                                        72
                                );
                                g.drawTile(
                                        (actualX - (Constants.TileSize * 2)) + 3,
                                        (actualY + (Constants.TileSize / 2)) + 2,
                                        71
                                );
                            } break;
                            case Right: {
                                g.drawTile(
                                        (actualX + (Constants.TileSize)) - 3,
                                        (actualY + (Constants.TileSize / 2)) + 2,
                                        69
                                );
                                g.drawTile(
                                        (actualX + (Constants.TileSize * 2)) - 3,
                                        (actualY + (Constants.TileSize / 2)) + 2,
                                        70
                                );
                            } break;
                        }
                    } break;
                }
            }
        }

    }
}
