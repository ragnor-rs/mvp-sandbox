package io.reist.sandbox.core.rx.impl.wrappers;

import io.reist.sandbox.core.rx.Func1;
import io.reist.sandbox.core.rx.Observable;

/**
 * Created by Reist on 10/28/15.
 */
public class MapOnSubscribe<T, R> extends TransformOnSubscribe<T, R> {

    private final Func1<T, R> func;

    public MapOnSubscribe(Observable<T> source, Func1<T, R> func) {
        super(source);
        this.func = func;
    }

    @Override
    protected R transform(T t) {
        return func.call(t);
    }

}
