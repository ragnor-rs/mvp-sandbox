package io.reist.sandbox.core.rx.impl;

import java.util.concurrent.atomic.AtomicInteger;

import io.reist.sandbox.core.rx.Func0;

/**
 * Created by Reist on 10/23/15.
 */
public class ArrayOnSubscribe<I> extends CompatOnSubscribe<I> {

    private final AtomicInteger pointer = new AtomicInteger();

    private final I[] items;

    public ArrayOnSubscribe(I[] items) {
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
    public boolean isCompleted() {
        return pointer.get() >= items.length;
    }

}
