package io.reist.sandbox.core.rx.impl;

import io.reist.sandbox.core.rx.Func0;
import io.reist.sandbox.core.rx.Observable;

/**
 * Created by Reist on 10/26/15.
 */
public class ConcatWithObservable<T> extends Observable<T> {

    private final Observable<T> source;
    private final Observable<T> alternative;

    public ConcatWithObservable(Observable<T> source, Observable<T> alternative) {
        super(source);
        this.source = source;
        this.alternative = alternative;
    }

    @Override
    public Func0<T> getEmittingFunction() {
        return source.isDepleted() ?
                alternative.getEmittingFunction() :
                source.getEmittingFunction();
    }

    @Override
    public boolean isDepleted() {
        return source.isDepleted() && alternative.isDepleted();
    }

}
