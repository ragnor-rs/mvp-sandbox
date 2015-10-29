package io.reist.sandbox.core.rx;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reist.sandbox.core.rx.impl.origins.ArrayOnSubscribe;
import io.reist.sandbox.core.rx.impl.origins.JustOnSubscribe;
import io.reist.sandbox.core.rx.impl.wrappers.ConcatWithOnSubscribe;
import io.reist.sandbox.core.rx.impl.wrappers.FirstOnSubscribe;
import io.reist.sandbox.core.rx.impl.wrappers.ForEachObserver;
import io.reist.sandbox.core.rx.impl.wrappers.MapOnSubscribe;
import io.reist.sandbox.core.rx.impl.wrappers.SampleOnSubscribe;
import io.reist.sandbox.core.rx.impl.wrappers.SwitchMapOnSubscribe;

/**
 * Created by Reist on 10/14/15.
 */
public final class Observable<T> extends Subscriber<T> {

    public static final Scheduler DEFAULT_SCHEDULER = Schedulers.immediate();

    public static final boolean LOGGING_ENABLED = false;

    private Scheduler.Worker backgroundWorker;
    private Scheduler.Worker mainWorker;

    private final List<Observer<T>> observers = Collections.synchronizedList(new ArrayList<Observer<T>>());

    public final OnSubscribe<T> onSubscribe;

    protected Observable(Observable.OnSubscribe<T> onSubscribe) {
        this.onSubscribe = onSubscribe;
    }

    public static void log(String tag, String str) {
        if (LOGGING_ENABLED) {
            System.out.println(tag + ": " + str);
        }
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
                try {
                    onSubscribe.call(Observable.this);
                } catch (StopThreadException e) {
                    throw new StopThreadException();
                } catch (StopObservableException e) {
                    onCompleted();
                }
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
                observers.clear();
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

    public final void forEach(Action1<T> action) {
        subscribe(new ForEachObserver<>(this, action));
    }

    public final <R> Observable<R> map(Func1<T, R> func) {
        return new Observable<>(new MapOnSubscribe<>(this, func));
    }

    public final Observable<T> sample(long period, TimeUnit unit) {
        return new Observable<>(new SampleOnSubscribe<>(this, TimeUnit.MILLISECONDS.convert(period, unit)));
    }

    public final <R> Observable<R> switchMap(Func1<T, Observable<R>> observableEmitter) {
        return new Observable<>(new SwitchMapOnSubscribe<>(this, observableEmitter));
    }

    public final Observable<T> first() {
        return new Observable<>(new FirstOnSubscribe<>(this));
    }

    @Override
    public void unsubscribe() {
        throw new UnsupportedOperationException();
    }

    public interface OnSubscribe<T> extends Action1<Subscriber<T>> {}

    public static class StopObservableException extends RuntimeException {}

    public static class StopThreadException extends RuntimeException {}

}
