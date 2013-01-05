package com.lunaticedit.legendofwaffles.consumers;

import com.badlogic.gdx.math.Rectangle;
import com.lunaticedit.legendofwaffles.contracts.Processable;
import com.lunaticedit.legendofwaffles.contracts.Repository;
import com.lunaticedit.legendofwaffles.factories.RepositoryFactory;

public class ProcessableConsumer {
    Repository _repository;
    private Rectangle _viewport;

    public ProcessableConsumer(final RepositoryFactory repositoryFactory) throws Exception {
        _repository = repositoryFactory.generate();
    }

    /**
     * Processes all processable objects in a repository.
     */
    public void process() {

        // Process the scene
        _repository
                .getScene()
                .update();

        // Process all processable objects
        for(Processable p :_repository.getProcessables())
        { p.process(); }

    }
}
