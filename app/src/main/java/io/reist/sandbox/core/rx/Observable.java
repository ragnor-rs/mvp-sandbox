package io.reist.sandbox.core.rx;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Reist on 10/14/15.
 */
public abstract class Observable<T> {

    private Scheduler subscriptionScheduler = Schedulers.immediate();
    private Scheduler observationScheduler = Schedulers.immediate();

    public final Subscription subscribe(final Observer<T> observer) {
        final Func0<T> func = getEmittingFunction();
        subscriptionScheduler.post(new Action0() {

            @Override
            public void call() {
                while(!isDepleted()) {
                    try {
                        doOnNext(func.call(), observer);
                    } catch (final Throwable e) {
                        observationScheduler.post(new Action0() {

                            @Override
                            public void call() {
                                observer.onError(e);
                            }

                        });
                    }
                }
                doOnCompleted(observer);
            }

        });
        return new Subscription();
    }

    protected void doOnNext(final T value, final Observer<T> observer) {
        observationScheduler.post(new Action0() {

            @Override
            public void call() {
                observer.onNext(value);
            }

        });
    }

    protected void doOnCompleted(final Observer<T> observer) {
        observationScheduler.post(new Action0() {

            @Override
            public void call() {
                observer.onCompleted();
            }

        });
    }

    protected boolean isDepleted() {
        return false;
    }

    protected abstract Func0<T> getEmittingFunction();

    public final Observable<T> subscribeOn(Scheduler scheduler) {
        this.subscriptionScheduler = scheduler;
        return this;
    }

    public final Observable<T> observeOn(Scheduler scheduler) {
        this.observationScheduler = scheduler;
        return this;
    }

    public static <I> Observable<I> from(final I[] items) {
        return new Observable<I>() {

            private final AtomicInteger pointer = new AtomicInteger();

            @Override
            protected boolean isDepleted() {
                return pointer.get() >= items.length;
            }

            @Override
            protected Func0<I> getEmittingFunction() {
                return new Func0<I>() {

                    @Override
                    public I call() {
                        return items[pointer.getAndIncrement()];
                    }

                };
            }

        };
    }

}
