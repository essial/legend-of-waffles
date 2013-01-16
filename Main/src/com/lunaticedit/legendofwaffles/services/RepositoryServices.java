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
    public void bootstrap() throws UnsupportedOperationException {
        _repository.setScene(_sceneFactory.generateScene(SceneType.MainMenu));
    }

}
