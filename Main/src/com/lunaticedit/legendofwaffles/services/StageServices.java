package com.lunaticedit.legendofwaffles.services;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.lunaticedit.legendofwaffles.factories.RepositoryFactory;
import com.lunaticedit.legendofwaffles.factories.StageFactory;
import com.lunaticedit.legendofwaffles.helpers.Constants;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

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
        loadXML();
    }

    private void loadXML() {
        try {
            final FileHandle xmlFile = Gdx.files.internal(Constants.StageDefaultsFile);
            final DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            final DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            final Document doc = dBuilder.parse(xmlFile.read());
            doc.getDocumentElement().normalize();
            processStageManagerXML(doc.getDocumentElement());
        }
        catch (Exception e) { Gdx.app.log("Error", e.getMessage(), e); }
    }

    private void processStageManagerXML(final Element stageManagerElement) {
        final NodeList childNodes = stageManagerElement.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            final Node childNode = childNodes.item(i);
            if (childNode.getNodeType() != Node.ELEMENT_NODE)
            { continue; }
            final Element childElement = (Element)childNode;
            final String nodeName = childElement.getNodeName();

            if (nodeName.equals("Property"))
            { processPropertyXML(childElement); }
        }
    }

    private void processPropertyXML(final Element propertyElement) {
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
        String stageToLoad = (currentStage.isEmpty())
                ? _stageFactory.generate().getDefaultStage()
                : currentStage;
    }
}
