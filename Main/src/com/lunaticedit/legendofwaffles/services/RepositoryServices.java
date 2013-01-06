package com.lunaticedit.legendofwaffles.services;

import com.lunaticedit.legendofwaffles.contracts.Repository;
import com.lunaticedit.legendofwaffles.enums.SceneType;
import com.lunaticedit.legendofwaffles.factories.RepositoryFactory;
import com.lunaticedit.legendofwaffles.factories.SceneFactory;

public final class RepositoryServices {
    private final Repository _repository;
    private final SceneFactory _sceneFactory;

    public RepositoryServices(
            final RepositoryFactory repositoryFactory,
            final SceneFactory sceneFactory
    ) throws UnsupportedOperationException {
        _repository = repositoryFactory.generate();
        _sceneFactory = sceneFactory;
    }

    /**
     * Kicks off the repository initialization phase, and generates the main menu scene.
     * @throws UnsupportedOperationException when the main menu scene cannot be generated successfully.
     */
    public void bootstrap() throws UnsupportedOperationException {
        _repository.setScene(_sceneFactory.generateScene(SceneType.MainMenu));
    }

}
