package io.reist.sandbox.core.rx.impl.wrappers;

import io.reist.sandbox.core.rx.Func1;
import io.reist.sandbox.core.rx.Observable;
import io.reist.sandbox.core.rx.Observer;
import io.reist.sandbox.core.rx.impl.AbstractOnSubscribe;


/**
 * Created by Reist on 10/28/15.
 */
public class SwitchMapOnSubscribe<S, D> extends AbstractOnSubscribe<D> implements Observer<D> {

    private final Observable<S> source;
    private final Func1<S, Observable<D>> func;

    public SwitchMapOnSubscribe(Observable<S> source, Func1<S, Observable<D>> func) {
        this.source = source;
        this.func = func;
    }

    @Override
    protected void emit() throws Exception {
        source.map(func).subscribe(new Observer<Observable<D>>() {

            @Override
            public void onNext(Observable<D> dObservable) {
                dObservable.subscribe(SwitchMapOnSubscribe.this).unsubscribe();
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

    @Override
    public void onNext(D d) {
        doOnNext(d);
    }

    @Override
    public void onError(Throwable e) {
        doOnError(e);
    }

    @Override
    public void onCompleted() {
        doOnCompleted();
    }

    /*
    @Override
    protected void emit() throws Exception {
        source.subscribe(new Observer<S>() {

            @Override
            public void onNext(S s) {
                final Observable<D> observable = func.call(s);
                Observable.just(observable).subscribe(new Observer<Observable<D>>() {

                    @Override
                    public void onNext(Observable<D> dObservable) {
                        dObservable.subscribe(new Observer<D>() {

                            @Override
                            public void onNext(D d) {
                                doOnNext(d);
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

                    @Override
                    public void onError(Throwable e) {
                        doOnError(e);
                    }

                    @Override
                    public void onCompleted() {}

                }).unsubscribe();
            }

            @Override
            public void onError(Throwable e) {
                doOnError(e);
            }

            @Override
            public void onCompleted() {}

        }).unsubscribe();
    }
    */

}


