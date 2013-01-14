package com.lunaticedit.legendofwaffles.implementations.stageobject;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.lunaticedit.legendofwaffles.contracts.Processable;
import com.lunaticedit.legendofwaffles.contracts.Renderable;
import com.lunaticedit.legendofwaffles.contracts.StageObject;
import com.lunaticedit.legendofwaffles.enums.Orientation;
import com.lunaticedit.legendofwaffles.factories.RepositoryFactory;
import com.lunaticedit.legendofwaffles.helpers.Constants;
import com.lunaticedit.legendofwaffles.helpers.Dimension;
import com.lunaticedit.legendofwaffles.helpers.Point;
import com.lunaticedit.legendofwaffles.physics.HitHandler;
import com.lunaticedit.legendofwaffles.physics.HitWatcher;
import com.lunaticedit.legendofwaffles.physics.Physics;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Platform implements StageObject, Renderable, HitHandler, Processable {
    private int _posX;
    private int _posY;
    private int _distance;
    private int _size;
    private Orientation _orientation;
    private HitWatcher _hitWatcher;
    private Body _body;
    private boolean _reverse;

    @Override
    public void processXML(final Element element) {
        // Attach to the repository
        (new RepositoryFactory())
                .generate()
                .getObjects()
                .add(this);

        _reverse = false;
        _posX = (Integer.parseInt(element.getAttribute("x")));
        _posY = (Integer.parseInt(element.getAttribute("y")));

        final NodeList childNodes = element.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            final Node childNode = childNodes.item(i);

            if (childNode.getNodeType() != Node.ELEMENT_NODE)
            { continue; }

            final Element childElement = (Element) childNode;
            final String cnn = childNode.getNodeName();

            if (cnn.equals("properties")) {
                processProperties(childElement);
            }
        }
        initializePhysics();


    }

    private void processProperties(final Element element) {
        final NodeList childNodes = element.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            final Node childNode = childNodes.item(i);
            if (childNode.getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
            final Element childElement = (Element) childNode;
            final String cnn = childElement.getAttribute("name");
            final String value = childElement.getAttribute("value");
            if (cnn.equals("Distance")) {
                _distance = (Integer.parseInt(value));
            } else if (cnn.equals("Size")) {
                _size = (Integer.parseInt(value));
            } else if (cnn.equals("Orientation")) {
                _orientation = (value.equals("Horizontal")
                        ? Orientation.Horizontal
                        : Orientation.Vertical);
            }
        }
    }

    private void initializePhysics() {
        final int xPos = _posX;
        final int yPos = _posY -  Constants.TileSize;
        final int width = _size * Constants.TileSize;
        final int height = Constants.TileSize;
        final int centerWidth = width / 2;
        final int centerHeight = height / 2;
        final int centerX = xPos + centerWidth;
        final int centerY = yPos + centerHeight;
        final BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(Physics.toMeters(centerX), Physics.toMeters(centerY));
        bodyDef.fixedRotation = true;
        _body = Physics.getInstance().createBody(bodyDef);
        final PolygonShape groundBox = new PolygonShape();
        groundBox.setAsBox(Physics.toMeters(centerWidth), Physics.toMeters(centerHeight));
        _body.createFixture(groundBox, 0.0f);
        _body.setGravityScale(0.0f);
        _hitWatcher = new HitWatcher(this, _body);
        Physics.getInstance().registerHitWacher(_hitWatcher);
    }

    @Override
    public int getX() {
        if (_body == null)
        { return 0; }

        final Vector2 position = _body.getPosition();
        return Physics.toPixels(position.x) - 2;
    }

    @Override
    public int getY() {
        if (_body == null)
        { return 0; }

        final Vector2 position = _body.getPosition();
        return Physics.toPixels(position.y) + 5;
    }

    @Override
    public boolean getVisible() {
        return true;
    }

    @Override
    public Dimension getTileSize() {
        return new Dimension(3, 1);
    }

    @Override
    public Point getTileOrigin() {
        return new Point(19, 2);
    }

    @Override
    public void HitOccurred(final Body body1, final Body body2) {

    }

    @Override
    public void process() {
        switch (_orientation) {
            case Vertical:
            {
                if (!_reverse) {
                    // Moving up
                    if (getY() <= (_posY - (_distance * Constants.TileSize))) {
                        _reverse = true;
                        _body.setLinearVelocity(new Vector2(0.0f, 0.0f));
                        break;
                    }
                    _body.setLinearVelocity(new Vector2(0.0f, -0.8f));
                } else {
                    // Moving down
                    // Moving up
                    if (getY() >_posY) {
                        _reverse = false;
                        _body.setLinearVelocity(new Vector2(0.0f, 0.0f));
                        break;
                    }
                    _body.setLinearVelocity(new Vector2(0.0f, 0.8f));
                }
            } break;
            case Horizontal:
            {

            } break;
        }
    }
}
