package io.reist.sandbox.core.rx;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reist.sandbox.core.rx.impl.ArrayObservable;
import io.reist.sandbox.core.rx.impl.ConcatMapObservable;
import io.reist.sandbox.core.rx.impl.ForEachObservable;

/**
 * Created by Reist on 10/14/15.
 */
public abstract class Observable<T> {

    public static final Scheduler DEFAULT_SCHEDULER = Schedulers.immediate();

    private final Action0 work = new Action0() {

        @Override
        public void call() {
            final Observable<T> source = Observable.this;
            while (!source.isDepleted()) {
                try {
                    source.doOnNext(source.getEmittingFunction().call());
                } catch (final Throwable e) {
                    source.doOnError(e);
                }
            }
            source.doOnCompleted();
        }

    };

    private final Observable<?> source;

    private Scheduler.Worker backgroundWorker;
    private Scheduler.Worker mainWorker;

    private final List<Observer<T>> observers = Collections.synchronizedList(new ArrayList<Observer<T>>());

    public Observable(Observable<?> source) {
        this.source = source;
    }

    public final Subscription subscribe(final Observer<T> observer) {

        observers.add(observer);

        initWorkers();

        return new Subscription() {

            @Override
            public void unsubscribe() {
                observers.remove(observer);
                if (observers.isEmpty()) {
                    backgroundWorker.unsubscribe();
                }
            }

        };

    }

    private void initWorkers() {

        if (mainWorker == null) {
            mainWorker = source == null ?
                    DEFAULT_SCHEDULER.createWorker() :
                    source.mainWorker;
        }

        if (backgroundWorker == null) {
            backgroundWorker = source == null ?
                    DEFAULT_SCHEDULER.createWorker() :
                    source.backgroundWorker;
        }

        if (source != null) {
            source.initWorkers();
        }

    }

    public final void doOnError(final Throwable e) {
        mainWorker.schedule(new Action0() {

            @Override
            public void call() {
                for (Observer<T> observer : observers) {
                    observer.onError(e);
                }
            }

        });
    }

    public final void doOnNext(final T value) {
        mainWorker.schedule(new Action0() {

            @Override
            public void call() {
                for (Observer<T> observer : observers) {
                    observer.onNext(value);
                }
            }

        });
    }

    public final void doOnCompleted() {
        mainWorker.schedule(new Action0() {

            @Override
            public void call() {
                for (Observer<T> observer : observers) {
                    observer.onCompleted();
                }
            }

        });
    }

    public boolean isDepleted() {
        return source != null && source.isDepleted();
    }

    public abstract Func0<T> getEmittingFunction();

    public final Observable<T> subscribeOn(Scheduler scheduler) {
        this.backgroundWorker = scheduler.createWorker();
        backgroundWorker.schedule(work);
        return this;
    }

    public final Observable<T> observeOn(Scheduler scheduler) {
        this.mainWorker = scheduler.createWorker();
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
