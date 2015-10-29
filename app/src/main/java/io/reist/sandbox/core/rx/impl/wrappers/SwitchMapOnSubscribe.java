package io.reist.sandbox.core.rx.impl.wrappers;

import io.reist.sandbox.core.rx.Func1;
import io.reist.sandbox.core.rx.Observable;
import io.reist.sandbox.core.rx.Observer;

/**
 * Created by Reist on 10/28/15.
 */
public class SwitchMapOnSubscribe<S, D> extends WrapperOnSubscribe<S, D> {

    private final Func1<S, Observable<D>> func;

    public SwitchMapOnSubscribe(Observable<S> source, Func1<S, Observable<D>> func) {
        super(source);
        this.func = func;
    }

    @Override
    public void onNext(S s) {
        final Observable<D> result = func.call(s);
        System.out.println(onSubscribeName + ": --- BEGIN --- ");
        result.first().subscribe(new Observer<D>() {

            @Override
            public void onNext(D d) {
                doOnNext(d);
            }

            @Override
            public void onError(Throwable e) {
                doOnError(e);
            }

            @Override
            public void onCompleted() {}

        }).unsubscribe();
        System.out.println(onSubscribeName + ": --- END --- ");
    }

}


