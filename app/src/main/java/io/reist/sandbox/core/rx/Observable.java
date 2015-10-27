package io.reist.sandbox.core.rx;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reist.sandbox.core.rx.impl.ArrayOnSubscribe;
import io.reist.sandbox.core.rx.impl.ConcatWithOnSubscribe;
import io.reist.sandbox.core.rx.impl.ForEachOnSubscribe;
import io.reist.sandbox.core.rx.impl.JustOnSubscribe;

/**
 * Created by Reist on 10/14/15.
 */
public class Observable<T> implements Subscriber<T> {

    public static final Scheduler DEFAULT_SCHEDULER = Schedulers.immediate();

    private Scheduler.Worker backgroundWorker;
    private Scheduler.Worker mainWorker;

    private final List<Observer<T>> observers = Collections.synchronizedList(new ArrayList<Observer<T>>());

    private final OnSubscribe<T> onSubscribe;

    protected Observable(Observable.OnSubscribe<T> onSubscribe) {
        this.onSubscribe = onSubscribe;
    }

    public final Subscription subscribe(final Observer<T> observer) {

        observers.add(observer);

        if (backgroundWorker == null) {
            subscribeOn(DEFAULT_SCHEDULER);
        }

        if (mainWorker == null) {
            observeOn(DEFAULT_SCHEDULER);
        }

        backgroundWorker.schedule(new Action0() {

            @Override
            public void call() {
                onSubscribe.call(Observable.this);
            }

        });

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

    @Override
    public final void onError(final Throwable e) {
        mainWorker.schedule(new Action0() {

            @Override
            public void call() {
                for (Observer<T> observer : observers) {
                    observer.onError(e);
                }
            }

        });
    }

    @Override
    public final void onNext(final T value) {
        mainWorker.schedule(new Action0() {

            @Override
            public void call() {
                for (Observer<T> observer : observers) {
                    observer.onNext(value);
                }
            }

        });
    }

    @Override
    public final void onCompleted() {
        mainWorker.schedule(new Action0() {

            @Override
            public void call() {
                for (Observer<T> observer : observers) {
                    observer.onCompleted();
                }
            }

        });
    }

    public final Observable<T> subscribeOn(Scheduler scheduler) {
        this.backgroundWorker = scheduler.createWorker();
        return this;
    }

    public final Observable<T> observeOn(Scheduler scheduler) {
        this.mainWorker = scheduler.createWorker();
        return this;
    }

    public static <T> Observable<T> from(T[] items) {
        return new Observable<>(new ArrayOnSubscribe<>(items));
    }

    public static <T> Observable<T> just(T t) {
        return new Observable<>(new JustOnSubscribe<>(t));
    }

    public static <T> Observable<T> create(OnSubscribe<T> onSubscribe) {
        return new Observable<>(onSubscribe);
    }

    public final Observable<T> concatWith(Observable<T> alternative) {
        return new Observable<>(new ConcatWithOnSubscribe<>(this, alternative));
    }

    public final Observable<T> forEach(Action1<T> action) {
        return new Observable<>(new ForEachOnSubscribe<>(this, action));
    }

    public final <R> Observable<R> map(Func1<T, R> func) {
        return null; //return new MapObservable<>(this, func);
    }

    public final Observable<T> sample(long period, TimeUnit unit) {
        return null; //return new SampleObservable<>(this, TimeUnit.MILLISECONDS.convert(period, unit));
    }

    public final <R> Observable<R> switchMap(Func1<T, Observable<R>> observableEmitter) {
        return null; //return new SwitchMapObservable<>(this, observableEmitter);
    }

    public interface OnSubscribe<T> extends Action1<Subscriber<T>> {}

}
