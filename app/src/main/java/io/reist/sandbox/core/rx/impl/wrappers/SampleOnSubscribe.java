package io.reist.sandbox.core.rx.impl.wrappers;

import io.reist.sandbox.core.rx.Observable;

/**
 * Created by Reist on 10/28/15.
 */
public class SampleOnSubscribe<T> extends WrapperOnSubscribe<T, T> {

    private static final String TAG = SampleOnSubscribe.class.getName();

    private final long period;

    public SampleOnSubscribe(Observable<T> source, long period) {
        super(source);
        this.period = period;
    }

    @Override
    public void onNext(T t) {
        try {
            doOnNext(t);
            Thread.sleep(period);
        } catch (java.lang.InterruptedException e) {
            throw new Observable.StopThreadException();
        }
    }

}
