package io.reist.sandbox.core.model;

/**
 * Created by Reist on 10/14/15.
 */
public interface AsyncResponse<T> {

    void onSuccess(T body);

    void onError(Throwable t);

}
