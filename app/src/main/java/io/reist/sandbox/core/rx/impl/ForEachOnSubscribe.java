package io.reist.sandbox.core.rx.impl;

import io.reist.sandbox.core.rx.Action1;
import io.reist.sandbox.core.rx.Observable;
import io.reist.sandbox.core.rx.Observer;
import io.reist.sandbox.core.rx.Subscriber;

public class ForEachOnSubscribe<T> implements Observable.OnSubscribe<T> {

    private final Observable<T> source;
    private final Action1<T> action;

    public ForEachOnSubscribe(Observable<T> source, Action1<T> action) {
        this.source = source;
        this.action = action;
    }

    @Override
    public void call(final Subscriber<T> subscriber) {
        source.subscribe(new Observer<T>() {

            @Override
            public void onNext(T t) {
                action.call(t);
                subscriber.onNext(t);
            }

            @Override
            public void onError(Throwable e) {
                subscriber.onError(e);
            }

            @Override
            public void onCompleted() {
                subscriber.onCompleted();
            }

        });
    }

}