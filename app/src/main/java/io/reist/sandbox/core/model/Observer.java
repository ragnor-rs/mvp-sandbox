package io.reist.sandbox.core.model;

/**
 * Created by Reist on 10/14/15.
 */
public interface Observer<T> {

    void onNext(T t);

    void onError(Throwable e);

}
