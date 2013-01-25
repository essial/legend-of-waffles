package com.lunaticedit.legendofwaffles.implementations.stageobject;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.lunaticedit.legendofwaffles.contracts.Processable;
import com.lunaticedit.legendofwaffles.contracts.Renderable;
import com.lunaticedit.legendofwaffles.contracts.StageObject;
import com.lunaticedit.legendofwaffles.factories.RepositoryFactory;
import com.lunaticedit.legendofwaffles.helpers.Constants;
import com.lunaticedit.legendofwaffles.helpers.Dimension;
import com.lunaticedit.legendofwaffles.helpers.Point;
import com.lunaticedit.legendofwaffles.implementations.repository.Player;
import org.w3c.dom.Element;

public class LifeUp implements StageObject, Renderable, Processable {
    private int _posX;
    private int _posY;

    @Override
    public void processXML(final Element element) {
        _posX = (Integer.parseInt(element.getAttribute("x")));
        _posY = (Integer.parseInt(element.getAttribute("y"))) - 8;
        (new RepositoryFactory()).generate().getObjects().add(this);
    }
    @Override
    public void process() {
        final Rectangle thisRect = new Rectangle(_posX, _posY, Constants.TileSize, Constants.TileSize);
        final Player player = (new RepositoryFactory()).generate().getPlayer();
        final Rectangle playerRect = new Rectangle(player.getX() - 4, player.getY() - 4, 8, 16);
        if (!Intersector.overlapRectangles(thisRect, playerRect)) {
            return;
        }
        (new RepositoryFactory()).generate().getPlayer().increaseMaxHealth();
        (new RepositoryFactory()).generate().getObjects().remove(this);
    }
    @Override
    public int getX() {
        return _posX;
    }
    @Override
    public int getY() {
        return _posY;
    }
    @Override
    public boolean getVisible() {
        return true;
    }
    @Override
    public Dimension getTileSize() {
        return new Dimension(1, 1);
    }
    @Override
    public Point getTileOrigin() {
        return new Point(0,29);
    }
}
