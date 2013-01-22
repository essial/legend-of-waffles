package com.lunaticedit.legendofwaffles.factories;

import com.lunaticedit.legendofwaffles.contracts.Scene;
import com.lunaticedit.legendofwaffles.enums.SceneType;
import com.lunaticedit.legendofwaffles.implementations.scene.DeathScene;
import com.lunaticedit.legendofwaffles.implementations.scene.GameScene;
import com.lunaticedit.legendofwaffles.implementations.scene.MainMenu;

public final class SceneFactory {
    public Scene generateScene(SceneType sceneType) throws UnsupportedOperationException {
        switch (sceneType) {
            case MainMenu:  return new MainMenu();
            case Game:      return new GameScene();
            case Death:     return new DeathScene();
            case GameOver:  throw new UnsupportedOperationException("Game over scene is not currently implemented!");
            default:        throw new UnsupportedOperationException("Tried to generate a scene type that is unknown!");
        }
    }
}
