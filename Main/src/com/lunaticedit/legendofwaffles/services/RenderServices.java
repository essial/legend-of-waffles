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
    private Rectangle getScreenBounds() {
        final Player player = (new RepositoryFactory()).generate().getPlayer();
        final int maxSize = 255 * 8;
        final int maxX = maxSize - (Constants.GameWidth);
        final int maxY = maxSize - (Constants.GameHeight);
        return new Rectangle(
                Math.min(Math.max(8, player.getX() - (Constants.GameWidth / 2)), maxX),
                Math.min(Math.max(8, (player.getY() - 50) - (Constants.GameHeight / 2)), maxY),
                Constants.GameWidth,
                Constants.GameHeight);
    }
    public boolean isVisibleOnScreen(final Rectangle screenBounds, final int x, final int y, final int width,
                                     final int height) {
        return !(((screenBounds.y + screenBounds.height) < y) || ((screenBounds.x + screenBounds.width) < x) ||
                (x + width < screenBounds.x) || ((y + height) < screenBounds.y));
    }
    public void render() {
        final Rectangle screenBounds = getScreenBounds();
        _repository.getScene().render(screenBounds);
        GraphicsGenerator g = new TilesetGraphicsGenerator();
        for(Object rx :_repository.getObjects()){
            if (!(rx instanceof  Renderable)) { continue; }
            Renderable r = (Renderable)rx;
            int actualX = r.getX() - (int)screenBounds.x;
            int actualY = r.getY() - (int)screenBounds.y;
            if (!r.getVisible()) { continue; }
            if (!isVisibleOnScreen(screenBounds, r.getX(), r.getY(), r.getTileSize().width * Constants.TileSize,
                    r.getTileSize().height * Constants.TileSize)) { continue; }
            int renderX;
            int renderY = actualY;
            for(int y = 0; y < r.getTileSize().height; y++) {
                renderX = actualX;
                for (int x = 0; x < r.getTileSize().width; x++ ) {
                    g.drawTile(renderX, renderY, (r.getTileOrigin().left + x) + ((r.getTileOrigin().top + y) *
                            g.getTilesPerRow()));
                    renderX += Constants.TileSize;
                } renderY += Constants.TileSize;
            }
            if (r instanceof Attackable) {
                Attackable a = (Attackable)r;
                if (!(new AttackableServices(a)).isAttacking()) { continue; }
                switch (a.getWeaponType()) {
                    case None: break;
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
