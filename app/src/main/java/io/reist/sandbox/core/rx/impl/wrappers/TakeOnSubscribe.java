package io.reist.sandbox.core.rx.impl.wrappers;

import io.reist.sandbox.core.rx.Observable;

/**
 * Created by Reist on 10/30/15.
 */
public class TakeOnSubscribe<R> extends WrapperOnSubscribe<R, R> {

    private final int size;

    private int i = 0;

    public TakeOnSubscribe(Observable<R> observable, int size) {
        super(observable);
        this.size = size;
    }

    @Override
    public void onNext(R r) {
        doOnNext(r);
        i++;
        if (i == size) {
            throw new Observable.StopObservableException();
        }
    }

}
