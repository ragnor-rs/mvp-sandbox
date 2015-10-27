package io.reist.sandbox.core.rx.impl;

/*
public class SampleObservable<T> extends Observable<T> {

    private final long period;
    private final Observable<T> source;

    public SampleObservable(Observable<T> source, long period) {
        super(source);
        this.source = source;
        this.period = period;
    }

    @Override
    public Func0<T> getEmittingFunction() {
        return source.getEmittingFunction();
    }

    @Override
    public void onItemEmitted() {
        super.onItemEmitted();
        try {
            Thread.sleep(period);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
*/
