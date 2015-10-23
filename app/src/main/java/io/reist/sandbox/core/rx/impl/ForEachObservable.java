package io.reist.sandbox.core.rx.impl;

import io.reist.sandbox.core.rx.Action1;
import io.reist.sandbox.core.rx.Func0;
import io.reist.sandbox.core.rx.Observable;
import io.reist.sandbox.core.rx.Observer;

/**
 * Created by Reist on 10/23/15.
 */
public class ForEachObservable<T> extends Observable<T> {

    private final Observable<T> source;
    private final Action1<T> action;

    public ForEachObservable(Observable<T> source, Action1<T> action) {
        this.source = source;
        this.action = action;
    }

    @Override
    public Func0<T> getEmittingFunction() {
        return source.getEmittingFunction();
    }

    @Override
    public void doOnNext(T value, Observer<T> observer) {
        source.doOnNext(value, observer);
        action.call(value);
    }

    @Override
    public void doOnCompleted(Observer<T> observer) {
        source.doOnCompleted(observer);
    }

    @Override
    public boolean isDepleted() {
        return source.isDepleted();
    }

    @Override
    public void doOnError(Observer<T> observer, Throwable e) {
        source.doOnError(observer, e);
    }

}
