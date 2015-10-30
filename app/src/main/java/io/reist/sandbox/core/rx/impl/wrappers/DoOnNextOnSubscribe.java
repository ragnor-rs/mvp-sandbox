package io.reist.sandbox.core.rx.impl.wrappers;

import io.reist.sandbox.core.rx.Action1;
import io.reist.sandbox.core.rx.Observable;

public class DoOnNextOnSubscribe<T> extends WrapperOnSubscribe<T, T> {

    private final Action1<T> action;

    public DoOnNextOnSubscribe(Observable<T> source, Action1<T> action) {
        super(source);
        this.action = action;
    }

    @Override
    public void onNext(T t) {
        doOnNext(t);
        action.call(t);
    }

}