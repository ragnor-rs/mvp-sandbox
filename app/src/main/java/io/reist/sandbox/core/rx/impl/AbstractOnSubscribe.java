package io.reist.sandbox.core.rx.impl;

import android.util.Log;

import io.reist.sandbox.core.rx.Observable;
import io.reist.sandbox.core.rx.Subscriber;

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
            if (isCriticalException(e)) {
                doOnError(e);
            } else {
                doOnCompleted();
            }
        }
    }

    protected boolean isCriticalException(Exception e) {
        return true;
    }

    protected abstract void emit() throws Exception;

    protected void doOnNext(T t) {
        log("emitted " + t);
        subscriber.onNext(t);
    }

    protected void log(String s) {
        log(s, null);
    }

    protected void doOnError(Throwable e) {
        log("thrown error", e);
        subscriber.onError(e);
    }

    protected void doOnCompleted() {
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
