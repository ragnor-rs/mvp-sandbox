package io.reist.sandbox.core.rx.impl;

/*
public class SwitchMapObservable<T, R> extends Observable<R> {

    private final Observable<T> source;
    private final Func1<T, Observable<R>> observableEmitter;

    public SwitchMapObservable(Observable<T> source, Func1<T, Observable<R>> observableEmitter) {
        super(source);
        this.source = source;
        this.observableEmitter = observableEmitter;
    }

    @Override
    public Func0<R> getEmittingFunction() {
        final Func0<T> sourceEmitter = source.getEmittingFunction();
        return new Func0<R>() {

            @Override
            public R call() {
                final T item = sourceEmitter.call();
                final Observable<R> output = observableEmitter.call(item);
                return output.getEmittingFunction().call();
            }

        };
    }

}
*/