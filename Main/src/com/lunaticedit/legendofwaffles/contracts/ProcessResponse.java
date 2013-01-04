package com.lunaticedit.legendofwaffles.contracts;

/**
 * Defines an object that is handled as a response to processing of an object.
 * The repository will be assigned in via setRepository() before process() is called.
 */
public interface ProcessResponse {
    /**
     * Used to give the process response object access to the data repository.
     * This is called just before calling process().
     * @param repository The repository to pass into the process response object.
     */
    public void setRepository(final Repository repository);

    /**
     * Called to process a response object. At this point setRepository() should
     * already have been called.
     */
    public void process();
}
