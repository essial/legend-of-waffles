package com.lunaticedit.legendofwaffles.services;

import com.lunaticedit.legendofwaffles.contracts.Processable;
import com.lunaticedit.legendofwaffles.contracts.Repository;
import com.lunaticedit.legendofwaffles.factories.RepositoryFactory;

public class ProcessableServices {
    Repository _repository;

    public ProcessableServices(final RepositoryFactory repositoryFactory) throws Exception {
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
        Object[] objs = _repository.getObjects().toArray();
        for(Object px : objs) {
            if (!(px instanceof Processable))
            { continue; }
            Processable p = (Processable)px;
            p.process();
        }

    }
}
