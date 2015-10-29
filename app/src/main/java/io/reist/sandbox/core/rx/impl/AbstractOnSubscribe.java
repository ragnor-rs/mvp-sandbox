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
        } catch (Exception e) {
            doOnError(e);
        }
    }

    protected abstract void emit() throws InterruptedException;

    protected final void doOnNext(T t) {
        System.out.println(onSubscribeName + ": emitted " + t);
        subscriber.onNext(t);
    }

    protected final void doOnError(Throwable e) {
        if (e instanceof Observable.InterruptedException) {
            return;
        }
        System.out.println(onSubscribeName + ": thrown " + e);
        subscriber.onError(e);
    }

    protected final void doOnCompleted() {
        System.out.println(onSubscribeName + ": completed");
        subscriber.onCompleted();
    }

}
