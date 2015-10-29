package io.reist.sandbox.core.rx;

/**
 * Created by Reist on 10/23/15.
 */
public class RxSingleThreadTestCase extends RxTestCase {

    @Override
    protected void onObservedFinish() {}

    @Override
    protected void onObservedError(Throwable e) {}

    @Override
    protected <T> void onObservedValue(T t) {}

    @Override
    public void testRx() throws Exception {
        createObservable()
                .subscribe(createObserver(STRING_VALUES));
    }

    @Override
    public void testForEach() throws Exception {
        createObservable()
                .forEach(new Action1<String>() {

                    private int i = 0;

                    @Override
                    public void call(String s) {
                        assertEquals(STRING_VALUES[i], s);
                        i++;
                    }

                });
    }

    @Override
    public void testMap() throws Exception {
        createObservable()
                .map(new Func1<String, Integer>() {

                    @Override
                    public Integer call(String s) {
                        return s.length();
                    }

                })
                .subscribe(createObserver(expectedForMap()));
    }

    @Override
    public void testConcatWith() throws Exception {
        createObservable()
                .concatWith(
                        Observable.from(MORE_STRING_VALUES)
                )
                .subscribe(createObserver(expectedForConcatWith()));
    }

    @Override
    public void testFirst() throws Exception {
        final String[] expected = expectedForFirst();
        createObservable()
                .first()
                .forEach(new Action1<String>() {

                    private int i = 0;

                    @Override
                    public void call(String s) {
                        assertEquals(expected[i], s);
                        assertEquals(0, i);
                        i++;
                    }

                });
    }

    @Override
    public void testSample() throws Exception {

        final long expectedPeriod = expectedForSample();

        createObservable()
                .sample(PERIOD_VALUE, PERIOD_UNIT)
                .map(new Func1<String, String>() {

                    private long startTime = -1;

                    @Override
                    public String call(String s) {
                        final long now = System.currentTimeMillis();
                        if (startTime != -1) {
                            final long realPeriod = now - startTime;
                            assertTrue(Math.abs(realPeriod - expectedPeriod) < 0.1 * expectedPeriod);
                        }
                        startTime = now;
                        return s;
                    }

                })
                .subscribe(createObserver(STRING_VALUES));

    }

    @Override
    public void testSwitchMap() throws Exception {

        final Observable<String> source = createObservable();
        final Observable<String> output1 = Observable.from(MORE_STRING_VALUES);
        final Observable<String> output2 = Observable.from(MORE_ALTERNATIVE_STRINGS);

        final String[] expected = expectedForSwitchMap();

        source
                .switchMap(new Func1<String, Observable<String>>() {

                    @Override
                    public Observable<String> call(String s) {
                        return checkSwitchCondition(s) ? output1 : output2;
                    }

                })
                .subscribe(createObserver(expected));

    }

    @Override
    public void testJustConcatWith() throws Exception {

        String[] remainingValues = new String[STRING_VALUES.length - 1];
        System.arraycopy(STRING_VALUES, 1, remainingValues, 0, remainingValues.length);

        Observable
                .just(STRING_VALUES[0])
                .concatWith(Observable.from(remainingValues))
                .subscribe(createObserver(STRING_VALUES));

    }

    @Override
    public void testCache() throws Exception {

        final Observable<String> local = Observable.from(RxTestCase.STRING_VALUES);
        final Observable<String> remote = Observable.from(RxTestCase.MORE_STRING_VALUES);

        String[] expected = expectedForCache();

        local
                .switchMap(new Func1<String, Observable<String>>() {

                    @Override
                    public Observable<String> call(String s) {
                        return RxTestCase.checkSwitchCondition(s) ?
                                remote :
                                Observable.just(s).concatWith(local);
                    }

                })
                .subscribe(createObserver(expected));

    }

}
