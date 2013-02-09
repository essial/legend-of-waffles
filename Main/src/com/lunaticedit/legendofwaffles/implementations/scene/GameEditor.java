package com.lunaticedit.legendofwaffles.implementations.scene;

import com.badlogic.gdx.math.Rectangle;
import com.lunaticedit.legendofwaffles.contracts.Scene;
import com.lunaticedit.legendofwaffles.enums.SceneType;
import com.lunaticedit.legendofwaffles.factories.RepositoryFactory;
import com.lunaticedit.legendofwaffles.factories.SceneFactory;
import com.lunaticedit.legendofwaffles.implementations.ui.UI;

public class GameEditor implements Scene {
    private final UI _ui;

    public GameEditor() {
        _ui = (new RepositoryFactory()).generate().getUI();
        _ui.reset();
        _ui.createInputView(new String[] {"EDIT GLOBALS", "MAP EDITOR", "NPC DESIGNER", "OBJECT DESIGNER", "RETURN"}, 8, 8);
    }

    @Override
    public void render(final Rectangle screenBounds) {

    }
    @Override
    public void update() {
        if (_ui.getModalResult() > -1) {
            switch (_ui.getModalResult()) {
                case 4: startMainMenu(); break;
            }
        }
    }
    private void startMainMenu() {
        (new RepositoryFactory()).generate().setScene(new SceneFactory().generateScene(SceneType.MainMenu));
    }
}
