package io.reist.sandbox.core.rx.impl;

import io.reist.sandbox.core.rx.Observable;
import io.reist.sandbox.core.rx.Observer;
import io.reist.sandbox.core.rx.Subscriber;

/**
 * Created by Reist on 10/26/15.
 */
public class ConcatWithOnSubscribe<T> implements Observable.OnSubscribe<T> {

    private final Observable<T> source;
    private final Observable<T> alternative;

    public ConcatWithOnSubscribe(Observable<T> source, Observable<T> alternative) {
        this.source = source;
        this.alternative = alternative;
    }

    @Override
    public void call(final Subscriber<T> subscriber) {
        source.subscribe(new Observer<T>() {

            @Override
            public void onNext(T t) {
                subscriber.onNext(t);
            }

            @Override
            public void onError(Throwable e) {
                subscriber.onError(e);
            }

            @Override
            public void onCompleted() {
                alternative.subscribe(new Observer<T>() {

                    @Override
                    public void onNext(T t) {
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

        });
    }

}
