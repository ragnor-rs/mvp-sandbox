package io.reist.sandbox.core.rx.impl;

import io.reist.sandbox.core.rx.Func1;
import io.reist.sandbox.core.rx.Observable;
import io.reist.sandbox.core.rx.Observer;
import io.reist.sandbox.core.rx.Subscriber;

/**
 * Created by Reist on 10/28/15.
 */
public class SwitchMapOnSubscribe<T, R> implements Observable.OnSubscribe<R> {

    private final Observable<T> source;
    private final Func1<T, Observable<R>> func;

    public SwitchMapOnSubscribe(Observable<T> source, Func1<T, Observable<R>> func) {
        this.source = source;
        this.func = func;
    }

    @Override
    public void call(final Subscriber<R> subscriber) {
        source.subscribe(new Observer<T>() {

            @Override
            public void onNext(T t) {
                final Observable<R> observable = func.call(t).first();
                observable.subscribe(new Observer<R>() {

                    @Override
                    public void onNext(R r) {
                        subscriber.onNext(r);
                    }

                    @Override
                    public void onError(Throwable e) {
                        subscriber.onError(e);
                    }

                    @Override
                    public void onCompleted() {}

                });
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
