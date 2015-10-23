package io.reist.sandbox.core.mvp.model;

import java.util.List;

import io.reist.sandbox.core.rx.Func0;
import io.reist.sandbox.core.rx.Observable;
import io.reist.sandbox.core.rx.Observer;

/**
 * Created by Reist on 10/23/15.
 */
public abstract class ListObservable<I> extends Observable<List<I>> {

    public Observable<Integer> store() {
        final Func0<List<I>> emittingFunction = ListObservable.this.getEmittingFunction();
        return new Observable<Integer>() {

            @Override
            protected Func0<Integer> getEmittingFunction() {
                return new Func0<Integer>() {

                    @Override
                    public Integer call() {
                        return put(emittingFunction.call());
                    }

                };
            }

        };
    }

    @Override
    protected Func0<List<I>> getEmittingFunction() {
        return new Func0<List<I>>() {

            @Override
            public List<I> call() {
                return get();
            }

        };
    }

    protected abstract Integer put(List<I> list);

    protected abstract List<I> get();

    public ListObservable<I> switchIfListEmpty(final ListObservable<I> alternative) {
        final ListObservable<I> parent = this;
        return new ListObservable<I>() {

            @Override
            protected Integer put(List<I> list) {
                return parent.put(list);
            }

            @Override
            protected List<I> get() {
                return parent.get();
            }

            @Override
            protected void doOnNext(List<I> value, Observer<List<I>> observer) {
                if (value == null || value.isEmpty()) {
                    alternative.doOnNext(value, observer);
                } else {
                    super.doOnNext(value, observer);
                }
            }

        };
    }

}
