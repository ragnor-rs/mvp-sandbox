package io.reist.sandbox.core.model;

/**
 * Created by Reist on 10/14/15.
 */
public interface AsyncRequest<T> {

    void enqueue(AsyncResponse<T> response);

}
