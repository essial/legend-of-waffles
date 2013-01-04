package com.lunaticedit.legendofwaffles.factories;

import com.lunaticedit.legendofwaffles.contracts.Scene;
import com.lunaticedit.legendofwaffles.enums.SceneType;
import com.lunaticedit.legendofwaffles.implementations.repository.MainMenu;

public final class SceneFactory {
    public Scene generateScene(SceneType sceneType) throws Exception {
        switch (sceneType) {
            case MainMenu:  return new MainMenu();
            case Game:      throw new Exception("Game scene is not currently implemented!");
            case GameOver:  throw new Exception("Game over scene is not currently implemented!");
            default:        throw new Exception("Tried to generate a scene type that is unknown!");
        }
    }
}
