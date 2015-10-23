package io.reist.sandbox.core.model;

/**
 * Created by Reist on 10/14/15.
 */
public interface Observable<T> {

    void subscribe(Observer<T> observer);

}
