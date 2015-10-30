package io.reist.sandbox.core.rx;

/**
 * Created by Reist on 10/26/15.
 */
public class TestObserver<T> implements Observer<T> {

    private final T[] expected;
    private final RxTestCase testCase;

    private int itemCounter = 0;

    protected TestObserver(T[] expected, RxTestCase testCase) {
        this.expected = expected;
        this.testCase = testCase;
    }

    @Override
    public void onNext(T t) {
        testCase.onObservedValue(expected[itemCounter], t);
        itemCounter++;
    }

    @Override
    public void onError(Throwable e) {
        testCase.onObservedError(e);
    }

    @Override
    public void onCompleted() {
        testCase.onObservedFinish();
    }

}
