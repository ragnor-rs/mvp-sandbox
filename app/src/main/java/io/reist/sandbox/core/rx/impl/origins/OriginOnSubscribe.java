package io.reist.sandbox.core.rx.impl.origins;

import io.reist.sandbox.core.rx.Func0;
import io.reist.sandbox.core.rx.impl.AbstractOnSubscribe;

/**
 * Created by Reist on 10/27/15.
 */
public abstract class OriginOnSubscribe<T> extends AbstractOnSubscribe<T> {

    @Override
    protected void emit() throws Exception {
        while (!isCompleted()) {
            doOnNext(getEmittingFunction().call());
        }
    }

    public abstract Func0<T> getEmittingFunction();

    public abstract boolean isCompleted();

}
