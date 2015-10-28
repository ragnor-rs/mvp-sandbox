package io.reist.sandbox.core.rx.impl;

import io.reist.sandbox.core.rx.Observable;
import io.reist.sandbox.core.rx.Observer;
import io.reist.sandbox.core.rx.Subscriber;

/**
 * Created by Reist on 10/28/15.
 */
public class FirstOnSubscribe<T> implements Observable.OnSubscribe<T> {

    private final Observable<T> source;

    public FirstOnSubscribe(Observable<T> source) {
        this.source = source;
    }

    @Override
    public void call(final Subscriber<T> subscriber) {
        source.subscribe(new Observer<T>() {

            @Override
            public void onNext(T t) {
                subscriber.onNext(t);
                throw new EmittedElementException();
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof EmittedElementException) {
                    subscriber.onCompleted();
                } else {
                    subscriber.onError(e);
                }
            }

            @Override
            public void onCompleted() {}

        });
    }

    private static class EmittedElementException extends RuntimeException {}

}
