package com.lunaticedit.legendofwaffles.factories;

import com.lunaticedit.legendofwaffles.contracts.Scene;
import com.lunaticedit.legendofwaffles.enums.SceneType;
import com.lunaticedit.legendofwaffles.implementations.scene.MainMenu;

public final class SceneFactory {
    public Scene generateScene(SceneType sceneType) throws UnsupportedOperationException {
        switch (sceneType) {
            case MainMenu:  return new MainMenu();
            case Game:      throw new UnsupportedOperationException("Game scene is not currently implemented!");
            case GameOver:  throw new UnsupportedOperationException("Game over scene is not currently implemented!");
            default:        throw new UnsupportedOperationException("Tried to generate a scene type that is unknown!");
        }
    }
}
