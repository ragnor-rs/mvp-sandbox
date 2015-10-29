package io.reist.sandbox.core.rx.impl.wrappers;

import io.reist.sandbox.core.rx.Observable;

/**
 * Created by Reist on 10/29/15.
 */
public abstract class TransformOnSubscribe<S, D> extends WrapperOnSubscribe<S, D> {

    public TransformOnSubscribe(Observable<S> source) {
        super(source);
    }

    @Override
    public void onNext(S s) {
        doOnNext(transform(s));
    }

    protected abstract D transform(S s);

}
