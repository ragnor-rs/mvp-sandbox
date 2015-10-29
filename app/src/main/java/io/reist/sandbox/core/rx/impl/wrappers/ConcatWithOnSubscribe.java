package io.reist.sandbox.core.rx.impl.wrappers;

import io.reist.sandbox.core.rx.Observable;
import io.reist.sandbox.core.rx.Observer;

/**
 * Created by Reist on 10/26/15.
 */
public class ConcatWithOnSubscribe<T> extends WrapperOnSubscribe<T, T> {

    private final Observable<T> alternative;

    private Throwable e;

    public ConcatWithOnSubscribe(Observable<T> source, Observable<T> alternative) {
        super(source);
        this.alternative = alternative;
    }

    @Override
    public void onNext(T t) {
        doOnNext(t);
    }

    @Override
    public void onCompleted() {

        e = null;

        alternative.subscribe(new Observer<T>() {

            @Override
            public void onNext(T t) {
                doOnNext(t);
            }

            @Override
            public void onError(Throwable e) {
                ConcatWithOnSubscribe.this.e = e;
            }

            @Override
            public void onCompleted() {}

        }).unsubscribe();

        if (e != null) {
            throw new RuntimeException(e);
        }

    }

}
