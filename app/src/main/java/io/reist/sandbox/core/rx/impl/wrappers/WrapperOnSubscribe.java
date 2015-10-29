package io.reist.sandbox.core.rx.impl.wrappers;

import io.reist.sandbox.core.rx.Observable;
import io.reist.sandbox.core.rx.Observer;
import io.reist.sandbox.core.rx.impl.AbstractOnSubscribe;

/**
 * Created by Reist on 10/28/15.
 */
public abstract class WrapperOnSubscribe<S, D> extends AbstractOnSubscribe<D> implements Observer<S> {

    private final Observable<S> source;

    public WrapperOnSubscribe(Observable<S> source) {
        this.source = source;
    }

    @Override
    protected final void emit() throws InterruptedException {
        source.subscribe(this).unsubscribe();
    }

    @Override
    public void onError(Throwable e) {}

    @Override
    public void onCompleted() {}

}
