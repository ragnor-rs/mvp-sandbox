package io.reist.sandbox.core.rx;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reist.sandbox.core.rx.impl.ArrayObservable;
import io.reist.sandbox.core.rx.impl.ConcatWithObservable;
import io.reist.sandbox.core.rx.impl.ForEachObservable;
import io.reist.sandbox.core.rx.impl.JustObservable;
import io.reist.sandbox.core.rx.impl.MapObservable;
import io.reist.sandbox.core.rx.impl.SampleObservable;
import io.reist.sandbox.core.rx.impl.SwitchMapObservable;

/**
 * Created by Reist on 10/14/15.
 */
public abstract class Observable<T> implements Action0 {

    public static final Scheduler DEFAULT_SCHEDULER = Schedulers.immediate();

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

        backgroundWorker.schedule(this);

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

        if (mainWorker == null){
            observeOn(DEFAULT_SCHEDULER);
        }

        if (backgroundWorker == null) {
            subscribeOn(DEFAULT_SCHEDULER);
        }

        if (source != null) {

            source.initWorkers();

            source.mainWorker = mainWorker;
            source.backgroundWorker = backgroundWorker;

        }

    }

    @Override
    public final void call() {
        try {
            while (!isDepleted()) {
                doOnNext(getEmittingFunction().call());
                onItemEmitted();
            }
        } catch (final Throwable e) {
            doOnError(e);
        }
        doOnCompleted();
        backgroundWorker.unsubscribe();
    }

    public void onItemEmitted() {
        if (source != null) {
            source.onItemEmitted();
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
        return source != null && source.isDepleted() || getEmittingFunction() == null;
    }

    public abstract Func0<T> getEmittingFunction();

    public final Observable<T> subscribeOn(Scheduler scheduler) {
        this.backgroundWorker = scheduler.createWorker();
        return this;
    }

    public final Observable<T> observeOn(Scheduler scheduler) {
        this.mainWorker = scheduler.createWorker();
        return this;
    }

    public static <T> Observable<T> from(T[] items) {
        return new ArrayObservable<>(items);
    }

    public static <T> Observable<T> just(T t) {
        return new JustObservable<>(t);
    }

    public final Observable<T> forEach(Action1<T> action) {
        return new ForEachObservable<>(this, action);
    }

    public final <R> Observable<R> map(Func1<T, R> func) {
        return new MapObservable<>(this, func);
    }

    public final Observable<T> sample(long period, TimeUnit unit) {
        return new SampleObservable<>(this, TimeUnit.MILLISECONDS.convert(period, unit));
    }

    public final Observable<T> concatWith(Observable<T> alternative) {
        return new ConcatWithObservable<>(this, alternative);
    }

    public final <R> Observable<R> switchMap(Func1<T, Observable<R>> observableEmitter) {
        return new SwitchMapObservable<>(this, observableEmitter);
    }

}
