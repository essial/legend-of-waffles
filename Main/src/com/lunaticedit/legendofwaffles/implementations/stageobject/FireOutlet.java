package com.lunaticedit.legendofwaffles.implementations.stageobject;

import com.lunaticedit.legendofwaffles.contracts.Processable;
import com.lunaticedit.legendofwaffles.contracts.Renderable;
import com.lunaticedit.legendofwaffles.contracts.StageObject;
import com.lunaticedit.legendofwaffles.factories.RepositoryFactory;
import com.lunaticedit.legendofwaffles.helpers.Dimension;
import com.lunaticedit.legendofwaffles.helpers.Point;
import org.w3c.dom.Element;

import java.util.Random;

public class FireOutlet implements StageObject, Renderable, Processable {
    private int _posX;
    private int _posY;
    private int _curFrame;
    private long _lastUpdate;
    private Random r;

    public FireOutlet() {
        r = new Random();
        _curFrame = -1;
    }
    @Override
    public void processXML(final Element element) {
        _posX = (Integer.parseInt(element.getAttribute("x")));
        _posY = (Integer.parseInt(element.getAttribute("y"))) - 8;
        _lastUpdate = System.currentTimeMillis();
        (new RepositoryFactory()).generate().getObjects().add(this);
    }
    @Override
    public int getX()
    { return _posX; }
    @Override
    public int getY()
    { return _posY; }
    @Override
    public boolean getVisible()
    { return true; }
    @Override
    public Dimension getTileSize()
    { return new Dimension(1, 2); }
    @Override
    public Point getTileOrigin() {
        switch(_curFrame) {
            case 0: return new Point(26, 18);
            case 1: return new Point(27, 18);
            case 2: return new Point(28, 18);
            case 3: return new Point(29, 18);
            case 4: return new Point(20, 18);
            case 5: return new Point(21, 18);
            case 6: return new Point(22, 18);
            case 7: return new Point(23, 18);
            default: return new Point(20, 16);
        }
    }
    @Override
    public void process() {
        final long procTime = (System.currentTimeMillis() - _lastUpdate);
        if (procTime < 3000) {
            _curFrame = -1;
            return;
        }
        if (procTime > 9000) {
            _curFrame = -1;
            _lastUpdate = System.currentTimeMillis();
            return;
        }
        if ((procTime < 4000) || (procTime > 8000)) {
            _curFrame = r.nextInt(4) + 4;
            return;
        }
        _curFrame = r.nextInt(4);
    }
}
