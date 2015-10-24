package io.reist.sandbox.core.mvp.model;

import java.util.List;

import io.reist.sandbox.core.rx.Func0;

/**
 * Created by Reist on 10/23/15.
 */
public class SwitchIfListEmptyObservable<I> extends ListObservable<I> {

    private final ListObservable<I> source;
    private final ListObservable<I> alternative;

    public SwitchIfListEmptyObservable(ListObservable<I> source, ListObservable<I> alternative) {
        super(source);
        this.source = source;
        this.alternative = alternative;
    }

    @Override
    public Integer put(List<I> list) {
        throw new RuntimeException("It should never happen");
    }

    @Override
    public List<I> get() {
        throw new RuntimeException("It should never happen");
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

    @Override
    public boolean isDepleted() {
        return source.isDepleted();
    }

}
