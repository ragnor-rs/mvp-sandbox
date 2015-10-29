package io.reist.sandbox.core.rx.impl.origins;

/**
 * Created by Reist on 10/26/15.
 */
public class JustOnSubscribe<T> extends OriginOnSubscribe<T> {

    private final T t;

    private boolean called = false;

    public JustOnSubscribe(T t) {
        this.t = t;
    }

    @Override
    public boolean isCompleted() {
        return called;
    }

    @Override
    public T call() {
        called = true;
        return t;
    }

}
