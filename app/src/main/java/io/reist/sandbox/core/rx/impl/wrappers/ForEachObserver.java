package io.reist.sandbox.core.rx.impl.wrappers;

import io.reist.sandbox.core.rx.Action1;
import io.reist.sandbox.core.rx.Observable;
import io.reist.sandbox.core.rx.Observer;

public class ForEachObserver<T> implements Observer<T> {

    private final Action1<T> action;
    private final Observable<T> source;

    public ForEachObserver(Observable<T> source, Action1<T> action) {
        this.source = source;
        this.action = action;
    }

    @Override
    public void onNext(T t) {
        action.call(t);
    }

    @Override
    public void onError(Throwable e) {}

    @Override
    public void onCompleted() {}

}