package io.reist.sandbox.core.rx.impl.wrappers;

import io.reist.sandbox.core.rx.Observable;

/**
 * Created by Reist on 10/28/15.
 */
public class FirstOnSubscribe<T> extends WrapperOnSubscribe<T, T> {

    public FirstOnSubscribe(Observable<T> source) {
        super(source);
    }

    @Override
    public void onNext(T t) {
        doOnNext(t);
        throw new Observable.InterruptedException();
    }

}
