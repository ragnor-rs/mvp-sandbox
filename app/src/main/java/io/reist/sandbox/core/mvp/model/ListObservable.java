package io.reist.sandbox.core.mvp.model;

import java.util.List;

import io.reist.sandbox.core.rx.Func0;
import io.reist.sandbox.core.rx.Observable;

/**
 * Created by Reist on 10/23/15.
 */
public abstract class ListObservable<I> extends Observable<List<I>> {

    public ListObservable(Observable<?> source) {
        super(source);
    }

    @Override
    public Func0<List<I>> getEmittingFunction() {
        return new Func0<List<I>>() {

            @Override
            public List<I> call() {
                return get();
            }

        };
    }

    public abstract Integer put(List<I> list);

    public abstract List<I> get();

    public ListObservable<I> switchIfListEmpty(final ListObservable<I> alternative) {
        return new SwitchIfListEmptyObservable<>(this, alternative);
    }

}
