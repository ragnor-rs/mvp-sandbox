package io.reist.sandbox.core.rx.impl.origins;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Reist on 10/23/15.
 */
public class ArrayOnSubscribe<I> extends OriginOnSubscribe<I> {

    private final AtomicInteger pointer = new AtomicInteger();

    private final I[] items;

    public ArrayOnSubscribe(I[] items) {
        this.items = items;
    }

    @Override
    public boolean isCompleted() {
        return pointer.get() >= items.length;
    }

    @Override
    public I call() {
        return items[pointer.getAndIncrement()];
    }

}
