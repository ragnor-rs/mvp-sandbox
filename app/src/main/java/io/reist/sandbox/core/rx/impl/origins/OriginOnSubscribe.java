package io.reist.sandbox.core.rx.impl.origins;

import io.reist.sandbox.core.rx.impl.StoppableOnSubscribe;

/**
 * Created by Reist on 10/27/15.
 */
public abstract class OriginOnSubscribe<T> extends StoppableOnSubscribe<T> {

    @Override
    protected void emit() throws Exception {
        while (!isCompleted()) {
            doOnNext(call());
        }
    }

    protected abstract T call() throws Exception;

    public abstract boolean isCompleted();

}
