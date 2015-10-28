package io.reist.sandbox.core.rx.impl;

import io.reist.sandbox.core.rx.Observable;
import io.reist.sandbox.core.rx.Observer;
import io.reist.sandbox.core.rx.Subscriber;

/**
 * Created by Reist on 10/28/15.
 */
public class SampleOnSubscribe <T> implements Observable.OnSubscribe<T> {

    private final Observable<T> source;
    private final long period;

    public SampleOnSubscribe(Observable<T> source, long period) {
        this.source = source;
        this.period = period;
    }

    @Override
    public void call(final Subscriber<T> subscriber) {
        source.subscribe(new Observer<T>() {

            @Override
            public void onNext(T t) {
                subscriber.onNext(t);
                try {
                    Thread.sleep(period);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
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
