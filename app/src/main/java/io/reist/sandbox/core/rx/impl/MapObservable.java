package io.reist.sandbox.core.rx.impl;

import io.reist.sandbox.core.rx.Func0;
import io.reist.sandbox.core.rx.Func1;
import io.reist.sandbox.core.rx.Observable;

/**
 * Created by Reist on 10/23/15.
 */
public class MapObservable<T, R> extends Observable<R> {

    private final Observable<T> source;
    private final Func1<T, R> func;

    public MapObservable(Observable<T> source, Func1<T, R> func) {
        super(source);
        this.source = source;
        this.func = func;
    }

    @Override
    public Func0<R> getEmittingFunction() {
        return new Func0<R>() {

            @Override
            public R call() {
                return func.call(source.getEmittingFunction().call());
            }

        };
    }

}
