package io.reist.sandbox.core.rx.impl.wrappers;

import io.reist.sandbox.core.rx.Func1;
import io.reist.sandbox.core.rx.Observable;

/**
 * Created by Reist on 10/30/15.
 */
public class LastOnSubscribe<R> extends WrapperOnSubscribe<R, R> {

    private final Func1<R, Boolean> predicate;

    public LastOnSubscribe(Observable<R> source) {
        this(source, null);
    }

    public LastOnSubscribe(Observable<R> observable, Func1<R, Boolean> predicate) {
        super(observable);
        this.predicate = predicate;
    }

    @Override
    public void onNext(R r) {
        if (predicate == null || predicate.call(r)) {
            doOnNext(r);
        }
    }

}
