package io.reist.sandbox.core.rx.impl.wrappers;

import io.reist.sandbox.core.rx.Observable;
import io.reist.sandbox.core.rx.Observer;

/**
 * Created by Reist on 10/26/15.
 */
public class ConcatWithOnSubscribe<T> extends WrapperOnSubscribe<T, T> {

    private final Observable<T> alternative;

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
        alternative.subscribe(new Observer<T>() {

            @Override
            public void onNext(T t) {
                doOnNext(t);
            }

            @Override
            public void onError(Throwable e) {
                doOnError(e);
            }

            @Override
            public void onCompleted() {
                doOnCompleted();
            }

        }).unsubscribe();
    }

}
