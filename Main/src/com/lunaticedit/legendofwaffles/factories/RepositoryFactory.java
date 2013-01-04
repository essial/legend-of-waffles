package com.lunaticedit.legendofwaffles.factories;

import com.lunaticedit.legendofwaffles.contracts.Repository;
import com.lunaticedit.legendofwaffles.helpers.Constants;
import com.lunaticedit.legendofwaffles.implementations.repository.LiveRepository;

public final class RepositoryFactory {

    public static Repository _liveRepository;

    private Repository getRepository() throws Exception {
        switch (Constants.RepositoryType) {
            case Test:
                throw new Exception(
                        "The Test repository has not been implemented!"
                );
            case Live:
                if (_liveRepository == null)
                { _liveRepository = new LiveRepository(); }
                return _liveRepository;
        }
        throw new Exception("Unknown repository type supplied!");
    }

    public Repository generate()
            throws Exception {
        return getRepository();
    }
}
