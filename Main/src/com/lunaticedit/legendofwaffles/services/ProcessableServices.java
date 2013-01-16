package com.lunaticedit.legendofwaffles.services;

import com.lunaticedit.legendofwaffles.contracts.Processable;
import com.lunaticedit.legendofwaffles.contracts.Repository;
import com.lunaticedit.legendofwaffles.factories.RepositoryFactory;

public class ProcessableServices {
    Repository _repository;

    public ProcessableServices(final RepositoryFactory repositoryFactory) throws Exception {
        _repository = repositoryFactory.generate();
    }
    public void process() {
        _repository.getScene().update();
        for(Object px : _repository.getObjects().toArray()) {
            if (!(px instanceof Processable)) { continue; }
            ((Processable)px).process();
        }
    }
}
