package io.reist.sandbox.core.rx.impl;

import io.reist.sandbox.core.rx.Func0;
import io.reist.sandbox.core.rx.Observable;
import io.reist.sandbox.core.rx.Subscriber;

/**
 * Created by Reist on 10/27/15.
 */
public abstract class CompatOnSubscribe<T> implements Observable.OnSubscribe<T> {

    @Override
    public void call(Subscriber<T> subscriber) {
        try {
            while (!isCompleted()) {
                subscriber.onNext(getEmittingFunction().call());
            }
            subscriber.onCompleted();
        } catch (Exception e) {
            subscriber.onError(e);
        }
    }

    public abstract Func0<T> getEmittingFunction();

    public abstract boolean isCompleted();

}
