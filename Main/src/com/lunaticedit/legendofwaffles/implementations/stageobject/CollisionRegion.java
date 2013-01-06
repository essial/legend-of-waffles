package com.lunaticedit.legendofwaffles.implementations.stageobject;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.lunaticedit.legendofwaffles.contracts.StageObject;
import com.lunaticedit.legendofwaffles.helpers.Constants;
import com.lunaticedit.legendofwaffles.physics.Physics;
import org.w3c.dom.Element;

public class CollisionRegion implements StageObject {
    @Override
    public void processXML(final Element element) {
        final int xPos = Integer.parseInt(element.getAttribute("x")) - Constants.TileSize;
        final int yPos = Integer.parseInt(element.getAttribute("y")) -  Constants.TileSize;
        final int width = Integer.parseInt(element.getAttribute("width"));
        final int height = Integer.parseInt(element.getAttribute("height"));
        final int centerWidth = width / 2;
        final int centerHeight = height / 2;
        final int centerX = xPos + centerWidth;
        final int centerY = yPos + centerHeight;
        final BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.position.set(Physics.toMeters(centerX), Physics.toMeters(centerY));
        final Body groundBody = Physics.getInstance().createBody(groundBodyDef);
        final PolygonShape groundBox = new PolygonShape();
        groundBox.setAsBox(Physics.toMeters(centerWidth), Physics.toMeters(centerHeight));
        groundBody.createFixture(groundBox, 0.0f);
    }
}
