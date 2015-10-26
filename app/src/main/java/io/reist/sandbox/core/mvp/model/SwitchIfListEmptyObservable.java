package io.reist.sandbox.core.mvp.model;

import java.util.List;

import io.reist.sandbox.core.rx.Func0;
import io.reist.sandbox.core.rx.Observable;

/**
 * Created by Reist on 10/23/15.
 */
public class SwitchIfListEmptyObservable<I> extends Observable<List<I>> {

    private final Observable<List<I>> source;
    private final Observable<List<I>> alternative;

    public SwitchIfListEmptyObservable(Observable<List<I>> source, Observable<List<I>> alternative) {
        super(source);
        this.source = source;
        this.alternative = alternative;
    }

    @Override
    public Func0<List<I>> getEmittingFunction() {
        final Func0<List<I>> sourceEmitter = source.getEmittingFunction();
        final Func0<List<I>> alternativeEmitter = source.getEmittingFunction();
        return new Func0<List<I>>() {

            @Override
            public List<I> call() {
                final List<I> value = sourceEmitter.call();
                return value == null || value.isEmpty() ?
                        alternativeEmitter.call() :
                        value;
            }

        };
    }

}
