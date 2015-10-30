package io.reist.sandbox.core.rx.impl;

import android.util.Log;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Reist on 10/29/15.
 */
public abstract class AbstractOnSubscribe<T> implements Observable.OnSubscribe<T> {

    private static final boolean LOGGING_ENABLED = true;

    protected final String onSubscribeName = getClass().getName();

    private Subscriber<? super T> subscriber;

    @Override
    public void call(Subscriber<? super T> subscriber) {
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
        log("emitted " + t);
        subscriber.onNext(t);
    }

    protected void log(String s) {
        log(s, null);
    }

    protected final void doOnError(Throwable e) {
        log("thrown error", e);
        subscriber.onError(e);
    }

    protected final void doOnCompleted() {
        log("completed");
        subscriber.onCompleted();
    }

    protected void log(String str, Throwable throwable) {
        if (LOGGING_ENABLED) {
            if (throwable == null) {
                Log.i(onSubscribeName, str);
            } else {
                Log.e(onSubscribeName, str, throwable);
            }
        }
    }

}
