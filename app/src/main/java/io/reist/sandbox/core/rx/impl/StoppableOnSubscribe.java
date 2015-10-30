package io.reist.sandbox.core.rx.impl;

import io.reist.sandbox.core.rx.Observable;

/**
 * Created by Reist on 10/31/15.
 */
public abstract class StoppableOnSubscribe<T> extends AbstractOnSubscribe<T> {

    @Override
    protected final boolean isCriticalException(Exception e) {
        return !(e instanceof Observable.StopObservableException);
    }
}
