package io.reist.sandbox.core.rx.impl;

import io.reist.sandbox.core.rx.Observable;
import io.reist.sandbox.core.rx.Subscriber;

/**
 * Created by Reist on 10/29/15.
 */
public abstract class AbstractOnSubscribe<T> implements Observable.OnSubscribe<T> {

    protected static final boolean LOGGING_ENABLED = false;

    protected final String onSubscribeName = getClass().getName();

    private Subscriber<T> subscriber;

    @Override
    public final void call(Subscriber<T> subscriber) {
        this.subscriber = subscriber;
        try {
            emit();
            doOnCompleted();
        } catch (Exception e) {
            doOnError(e);
        }
    }

    protected abstract void emit() throws Exception;

    protected final void doOnNext(T t) {
        final String message = "emitted " + t;
        log(message);
        subscriber.onNext(t);
    }

    protected final void doOnError(Throwable e) {
        if (e instanceof Observable.InterruptedException) {
            return;
        }
        log("thrown " + e);
        subscriber.onError(e);
    }

    protected final void doOnCompleted() {
        log("completed");
        subscriber.onCompleted();
    }

    protected void log(String str) {
        if (LOGGING_ENABLED) {
            System.out.println(onSubscribeName + ": " + str);
        }
    }

}