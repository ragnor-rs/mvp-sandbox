package io.reist.sandbox.core.rx.impl;

import java.util.concurrent.atomic.AtomicInteger;

import io.reist.sandbox.core.rx.Func0;
import io.reist.sandbox.core.rx.Observable;

/**
 * Created by Reist on 10/23/15.
 */
public class ArrayObservable<I> extends Observable<I> {

    private final AtomicInteger pointer = new AtomicInteger();

    private final I[] items;

    public ArrayObservable(I[] items) {
        super(null);
        this.items = items;
    }

    @Override
    public Func0<I> getEmittingFunction() {
        return new Func0<I>() {

            @Override
            public I call() {
                return items[pointer.getAndIncrement()];
            }

        };
    }

    @Override
    public boolean isDepleted() {
        return pointer.get() >= items.length;
    }

}
