package io.reist.sandbox.core.rx;

/**
 * Created by Reist on 10/27/15.
 */
public interface Subscriber<T> {

    void onError(Throwable e);

    void onNext(T value);

    void onCompleted();

}
