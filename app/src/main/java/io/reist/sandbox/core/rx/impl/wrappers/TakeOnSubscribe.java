package io.reist.sandbox.core.rx.impl.wrappers;

import io.reist.sandbox.core.rx.Func1;
import io.reist.sandbox.core.rx.Observable;

/**
 * Created by Reist on 10/30/15.
 */
public class TakeOnSubscribe<R> extends WrapperOnSubscribe<R, R> {

    private final Func1<R, Boolean> predicate;

    private int i = 0;

    public TakeOnSubscribe(Observable<R> observable, Func1<R, Boolean> predicate) {
        super(observable);
        this.predicate = predicate;
    }

    public TakeOnSubscribe(Observable<R> observable, final int size) {
        super(observable);
        this.predicate = new Func1<R, Boolean>() {

            @Override
            public Boolean call(R r) {
                i++;
                return i <= size;
            }

        };
    }

    @Override
    public void onNext(R r) {
        doOnNext(r);
        if (predicate.call(r)) {
            throw new Observable.StopObservableException();
        }
    }

}
