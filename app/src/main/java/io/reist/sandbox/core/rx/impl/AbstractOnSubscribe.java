package io.reist.sandbox.core.rx.impl;

import io.reist.sandbox.core.rx.Observable;
import io.reist.sandbox.core.rx.Subscriber;

/**
 * Created by Reist on 10/29/15.
 */
public abstract class AbstractOnSubscribe<T> implements Observable.OnSubscribe<T> {

    protected final String onSubscribeName = getClass().getName();

    private Subscriber<T> subscriber;

    @Override
    public final void call(Subscriber<T> subscriber) {
        this.subscriber = subscriber;
        try {
            emit();
            doOnCompleted();
        } catch (Observable.StopThreadException e) {
            throw e;
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
        log("thrown " + e);
        subscriber.onError(e);
    }

    protected final void doOnCompleted() {
        log("completed");
        subscriber.onCompleted();
    }

    protected void log(String str) {
        Observable.log(onSubscribeName, str);
    }

}
