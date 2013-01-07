package com.lunaticedit.legendofwaffles.services;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Base64Coder;
import com.lunaticedit.legendofwaffles.factories.RepositoryFactory;
import com.lunaticedit.legendofwaffles.factories.StageFactory;
import com.lunaticedit.legendofwaffles.factories.StageObjectFactory;
import com.lunaticedit.legendofwaffles.helpers.Constants;
import com.lunaticedit.legendofwaffles.implementations.MusicPlayer;
import com.lunaticedit.legendofwaffles.implementations.repository.Player;
import com.lunaticedit.legendofwaffles.physics.Physics;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

/**
 * Provides services for Stage objects.
 */
public class StageServices {
    private final RepositoryFactory _repositoryFactory;
    private final StageFactory _stageFactory;

    public StageServices(RepositoryFactory repositoryFactory, StageFactory stageFactory) {
        _repositoryFactory = repositoryFactory;
        _stageFactory = stageFactory;
    }

    /**
     * Load the stage defaults from the defaults file.
     */
    public void bootstrap() {
        loadStageDefaultsXML();
    }

    private void loadStageDefaultsXML() {
        try {
            final FileHandle xmlFile = Gdx.files.internal(Constants.StageDefaultsFile);
            final DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            final DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            final Document doc = dBuilder.parse(xmlFile.read());
            doc.getDocumentElement().normalize();
            processStageDefaultsXML(doc.getDocumentElement());
        }
        catch (Exception e) { Gdx.app.log("Error", e.getMessage(), e); }
    }

    private void processStageDefaultsXML(final Element stageManagerElement) {
        final NodeList childNodes = stageManagerElement.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            final Node childNode = childNodes.item(i);
            if (childNode.getNodeType() != Node.ELEMENT_NODE)
            { continue; }
            final Element childElement = (Element)childNode;
            final String nodeName = childElement.getNodeName();

            if (nodeName.equals("Property"))
            { processDefaultsPropertyXML(childElement); }
        }
    }

    private void processDefaultsPropertyXML(final Element propertyElement) {
        final String propName = propertyElement.getAttribute("name");
        final String propValue = propertyElement.getAttribute("value");

        if (propName.equals("DefaultStage")) {
            _stageFactory.generate()
                    .setDefaultStage(propValue);
            return;
        }

        if (propName.equals("PlayerStartX")) {
            _stageFactory.generate()
                    .setPlayerStartX(Integer.parseInt(propValue));
            return;
        }

        if (propName.equals("PlayerStartY")) {
            _stageFactory.generate()
                    .setPlayerStartY(Integer.parseInt(propValue));
            return;
        }

        throw new UnsupportedOperationException("Unknown property type: " + propName);
    }

    public void loadStage(final String currentStage) {

        MusicPlayer.getInstance().stopSong();

        // Reset the physics engine
        Physics.reset();

        // Clear out existing repository information as we are about to load new elements in.
        _repositoryFactory
                .generate()
                .getObjects()
                .clear();

        final Player player = (new RepositoryFactory())
                .generate()
                .getPlayer();

        if (currentStage.equals("")) {
            player.initializePhysics();
            player.setPosition(
                    (_stageFactory
                            .generate()
                            .getPlayerStartX() * Constants.TileSize),
                    (_stageFactory
                            .generate()
                            .getPlayerStartY() * Constants.TileSize)
            );
        }

        try {
            loadStageXML(
                    (currentStage.isEmpty())
                        ? _stageFactory.generate().getDefaultStage()
                        : currentStage
            );
        } catch (ParserConfigurationException e) {
            Gdx.app.log("Error", e.getMessage(), e);
        } catch (IOException e) {
            Gdx.app.log("Error", e.getMessage(), e);
        } catch (SAXException e) {
            Gdx.app.log("Error", e.getMessage(), e);
        }

        MusicPlayer.getInstance().playSong(Constants.GameSong);
    }

    public void loadStageXML(final String fileName)
            throws ParserConfigurationException, IOException, SAXException {
        final DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        final DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        final Document doc = dBuilder.parse(Gdx.files.internal(fileName + ".tmx").read());
        doc.getDocumentElement().normalize();

        final int mapWidth = Integer.parseInt(doc.getDocumentElement().getAttribute("width"));
        final int mapHeight = Integer.parseInt(doc.getDocumentElement().getAttribute("height"));
        _stageFactory
                .generate()
                .setMapWidth(mapWidth);

        _stageFactory
                .generate()
                .setMapHeight(mapHeight);

        final NodeList childNodes = doc.getDocumentElement().getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            final Node childNode = childNodes.item(i);

            if (childNode.getNodeType() != Node.ELEMENT_NODE)
            { continue; }

            final Element childElement = (Element) childNode;
            final String cnn = childNode.getNodeName();

            if (cnn.equals("layer")) {
                processLayerDataXML(childElement, mapWidth, mapHeight);
            }
            else if (cnn.equals("objectgroup")) {
                if (!childElement.getAttribute("name").equals("Data")) {
                    continue; // TODO: This is an error condition while loading map
                }
                processStageObjectDataXML(childElement);
            }
            else if (cnn.equals("properties")) {
                processStagePropertiesXML(childNode);
            }

        }
    }

    private void processStagePropertiesXML(final Node propertiesNode) {
        NodeList childNodes = propertiesNode.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            final Node childNode = childNodes.item(i);
            if (childNode.getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
            final Element childElement = (Element) childNode;
            final String cnn = childElement.getAttribute("name");

            if (cnn.equals("Title")) {
                _stageFactory
                        .generate()
                        .setStageTitle(childElement.getAttribute("value"));
            }
        }
    }

    private void processStageObjectDataXML(final Element objectNode) {
        final NodeList childNodes = objectNode.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            final Node childNode = childNodes.item(i);
            if (childNode.getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
            final Element childElement = (Element) childNode;
            final String cnn = childElement.getAttribute("type");
            try {
            (new StageObjectFactory().generate(cnn))
                    .processXML(childElement);
            } catch (UnsupportedOperationException e) {}

            /*
            if (cnn.equals("Collision Region"))  { processCollisionRegionXML(childElement); } else
            if (cnn.equals("Coin Box"))          { processCoinBoxXML(childElement);         } else
            if (cnn.equals("Vertical Cannon"))   { processVerticalCannonXML(childElement);  } else
            if (cnn.equals("Warp Zone"))         { processWarpZoneXML(childElement);        } else
            if (cnn.equals("Enemy Path"))        { processEnemyPath(childElement);          } else
            if (cnn.equals("Enemy Girl"))        { processEnemyGirl(childElement);          } else
            if (cnn.equals("Enemy Crab"))        { processEnemyCrab(childElement);          } else
            if (cnn.equals("Vertical Platform")) { processVerticalPlatform(childElement);   }
              */
        }
    }

    private void processLayerDataXML(final Element layerNode, final int width, final int height)
            throws IOException {
        final String data = layerNode.getChildNodes().item(1).getTextContent().trim();
        final byte[] decodedBytes = Base64Coder.decode(data);
        final GZIPInputStream stream = new GZIPInputStream(new ByteArrayInputStream(decodedBytes));
        final int[] _tileData = new int[width * height];
        int pos = 0;
        while (pos < (width * height)) {
            int tileNum = stream.read();
            tileNum |= (stream.read() << 8);
            tileNum |= (stream.read() << 16);
            tileNum |= (stream.read() << 24);
            _tileData[pos++] = tileNum - 1;
        }
        _stageFactory
                .generate()
                .setTileData(_tileData);

    }

}
