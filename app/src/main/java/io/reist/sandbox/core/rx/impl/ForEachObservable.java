package io.reist.sandbox.core.rx.impl;

import io.reist.sandbox.core.rx.Action1;
import io.reist.sandbox.core.rx.Func0;
import io.reist.sandbox.core.rx.Observable;

/**
 * Created by Reist on 10/23/15.
 */
public class ForEachObservable<T> extends Observable<T> {

    private final Observable<T> source;
    private final Action1<T> action;

    public ForEachObservable(Observable<T> source, Action1<T> action) {
        super(source);
        this.source = source;
        this.action = action;
    }

    @Override
    public Func0<T> getEmittingFunction() {
        final Func0<T> emittingFunction = source.getEmittingFunction();
        return new Func0<T>() {

            @Override
            public T call() {
                T value = emittingFunction.call();
                action.call(value);
                return value;
            }

        };
    }

}
