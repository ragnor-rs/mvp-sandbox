package io.reist.sandbox.core.rx;

import io.reist.sandbox.core.rx.impl.ArrayObservable;
import io.reist.sandbox.core.rx.impl.ConcatMapObservable;
import io.reist.sandbox.core.rx.impl.ForEachObservable;

/**
 * Created by Reist on 10/14/15.
 */
public abstract class Observable<T> {

    private Scheduler subscriptionScheduler = Schedulers.immediate();
    private Scheduler observationScheduler = Schedulers.immediate();

    public final Subscription subscribe(final Observer<T> observer) {
        final Observable<T> parent = this;
        subscriptionScheduler.post(new Action0() {

            @Override
            public void call() {
                while(!parent.isDepleted()) {
                    try {
                        doOnNext(parent.getEmittingFunction().call(), observer);
                    } catch (final Throwable e) {
                        doOnError(observer, e);
                    }
                }
                doOnCompleted(observer);
            }

        });
        return new Subscription();
    }

    public void doOnError(final Observer<T> observer, final Throwable e) {
        observationScheduler.post(new Action0() {

            @Override
            public void call() {
                observer.onError(e);
            }

        });
    }

    public void doOnNext(final T value, final Observer<T> observer) {
        observationScheduler.post(new Action0() {

            @Override
            public void call() {
                observer.onNext(value);
            }

        });
    }

    public void doOnCompleted(final Observer<T> observer) {
        observationScheduler.post(new Action0() {

            @Override
            public void call() {
                observer.onCompleted();
            }

        });
    }

    public boolean isDepleted() {
        return false;
    }

    public abstract Func0<T> getEmittingFunction();

    public final Observable<T> subscribeOn(Scheduler scheduler) {
        this.subscriptionScheduler = scheduler;
        return this;
    }

    public final Observable<T> observeOn(Scheduler scheduler) {
        this.observationScheduler = scheduler;
        return this;
    }

    public static <I> Observable<I> from(I[] items) {
        return new ArrayObservable<>(items);
    }

    public final Observable<T> forEach(Action1<T> action) {
        return new ForEachObservable<>(this, action);
    }

    public final <R> Observable<R> concatMap(Func1<T, R> func) {
        return new ConcatMapObservable<>(this, func);
    }

}
