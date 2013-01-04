package com.lunaticedit.legendofwaffles.consumers;

import com.badlogic.gdx.math.Rectangle;
import com.lunaticedit.legendofwaffles.contracts.Repository;
import com.lunaticedit.legendofwaffles.factories.RepositoryFactory;

public final class RenderConsumer {
    Repository _repository;
    private Rectangle _viewport;

    public RenderConsumer(final RepositoryFactory repositoryFactory) throws Exception {
            _repository = repositoryFactory.generate();
    }

    /**
     * Renders graphics to the screen
     */
    public void render() {

        _repository.getScene().render();
    }
}
