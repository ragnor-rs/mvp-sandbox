package io.reist.sandbox.core.rx.impl;

import io.reist.sandbox.core.rx.Func0;
import io.reist.sandbox.core.rx.Observable;

/**
 * Created by Reist on 10/26/15.
 */
public class JustObservable<T> extends Observable<T> {

    private final T t;

    private boolean called = false;

    public JustObservable(T t) {
        super(null);
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
    public boolean isDepleted() {
        return called;
    }

}
