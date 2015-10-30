package io.reist.sandbox.core.rx.impl.wrappers;

import io.reist.sandbox.core.rx.Func1;
import io.reist.sandbox.core.rx.Observable;
import io.reist.sandbox.core.rx.Observer;
import io.reist.sandbox.core.rx.impl.AbstractOnSubscribe;

/**
 * Created by Reist on 10/30/15.
 */
public class FirstOnSubscribe<R> extends AbstractOnSubscribe<R> {

    private final Func1<R, Boolean> predicate;
    private final Observable<R> observable;

    private R firstItem;

    private Throwable e;

    public FirstOnSubscribe(Observable<R> source) {
        this(source, null);
    }

    public FirstOnSubscribe(Observable<R> observable, Func1<R, Boolean> predicate) {
        this.observable = observable;
        this.predicate = predicate;
    }

    @Override
    protected void emit() throws Exception {
        while (true) {

            if (firstItem == null) {

                e = null;

                observable.subscribe(new Observer<R>() {

                    @Override
                    public void onNext(R r) {
                        firstItem = r;
                        throw new Observable.StopObservableException();
                    }

                    @Override
                    public void onError(Throwable e) {
                        FirstOnSubscribe.this.e = e;
                    }

                    @Override
                    public void onCompleted() {}

                }).unsubscribe();

                if (e != null) {
                    doOnError(e);
                    break;
                }

            }

            doOnNext(firstItem);

        }
    }

}
