package io.reist.sandbox.core.rx.impl;

import io.reist.sandbox.core.rx.Func0;

/**
 * Created by Reist on 10/26/15.
 */
public class JustOnSubscribe<T> extends CompatOnSubscribe<T> {

    private final T t;

    private boolean called = false;

    public JustOnSubscribe(T t) {
        this.t = t;
    }

    @Override
    public Func0<T> getEmittingFunction() {
        return new Func0<T>() {

            @Override
            public T call() {
                called = true;
                return t;
            }

        };
    }

    @Override
    public boolean isCompleted() {
        return called;
    }

}
