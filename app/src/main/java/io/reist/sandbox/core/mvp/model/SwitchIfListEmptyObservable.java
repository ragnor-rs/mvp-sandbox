package io.reist.sandbox.core.mvp.model;

import java.util.List;

import io.reist.sandbox.core.rx.Func0;
import io.reist.sandbox.core.rx.Observer;

/**
 * Created by Reist on 10/23/15.
 */
public class SwitchIfListEmptyObservable<I> extends ListObservable<I> {

    private final ListObservable<I> source;
    private final ListObservable<I> alternative;

    public SwitchIfListEmptyObservable(ListObservable<I> source, ListObservable<I> alternative) {
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
    public void doOnNext(List<I> value, Observer<List<I>> observer) {
        if (isEmpty(value)) {
            alternative.doOnNext(value, observer);
        } else {
            source.doOnNext(value, observer);
        }
    }

    private boolean isEmpty(List<I> value) {
        return value == null || value.isEmpty();
    }

    @Override
    public Func0<List<I>> getEmittingFunction() {
        return source.getEmittingFunction();
    }

    @Override
    public void doOnError(Observer<List<I>> observer, Throwable e) {
        source.doOnError(observer, e);
    }

    @Override
    public void doOnCompleted(Observer<List<I>> observer) {
        source.doOnCompleted(observer);
    }

    @Override
    public boolean isDepleted() {
        return source.isDepleted();
    }

}
