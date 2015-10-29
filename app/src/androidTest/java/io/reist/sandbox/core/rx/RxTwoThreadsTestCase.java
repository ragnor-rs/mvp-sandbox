package io.reist.sandbox.core.rx;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Reist on 10/26/15.
 */
public class RxTwoThreadsTestCase extends RxTestCase {

    private final Object lock = new Object();

    private Thread mainThread;

    private final List<Thread> computationThreads = new ArrayList<>();

    @Override
    public void testRx() throws Exception {
        createObservable()
                .map(new Func1<String, String>() {

                    @Override
                    public String call(String s) {
                        checkThreads();
                        return s;
                    }

                })
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.io())
                .subscribe(createObserver(STRING_VALUES));
    }

    @Override
    public void testForEach() throws Exception {
        createObservable()
                .map(new Func1<String, String>() {

                    @Override
                    public String call(String s) {
                        checkThreads();
                        return s;
                    }

                })
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.io())
                .forEach(new Action1<String>() {

                    private int i = 0;

                    @Override
                    public void call(String s) {

                        assertEquals(STRING_VALUES[i], s);
                        i++;

                        onObservedFinish();

                    }

                });
    }

    @Override
    public void testMap() throws Exception {
        createObservable()
                .map(new Func1<String, Integer>() {

                    @Override
                    public Integer call(String s) {
                        checkThreads();
                        return s.length();
                    }

                })
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.io())
                .subscribe(createObserver(expectedForMap()));
    }

    @Override
    public void testConcatWith() throws Exception {
        createObservable()
                .concatWith(
                        Observable.from(MORE_STRING_VALUES)
                )
                .map(new Func1<String, String>() {

                    @Override
                    public String call(String s) {
                        checkThreads();
                        return s;
                    }

                })
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.io())
                .subscribe(createObserver(expectedForConcatWith()));
    }

    @Override
    public void testFirst() throws Exception {
        final String[] expected = expectedForFirst();
        createObservable()
                .first()
                .map(new Func1<String, String>() {

                    @Override
                    public String call(String s) {
                        checkThreads();
                        return s;
                    }

                })
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.io())
                .forEach(new Action1<String>() {

                    private int i = 0;

                    @Override
                    public void call(String s) {

                        assertEquals(expected[i], s);
                        assertEquals(0, i);
                        i++;

                        onObservedFinish();

                    }

                });
    }

    private void unlock() {
        synchronized (lock) {
            lock.notifyAll();
        }
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

                        checkThreads();

                        return s;

                    }

                })
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.io())
                .subscribe(createObserver(STRING_VALUES));

    }

    @Override
    public void testSwitchMap() throws Exception {

        final Observable<String> source = createObservable();
        final Observable<String> output1 = Observable.from(MORE_STRING_VALUES);
        final Observable<String> output2 = Observable.from(MORE_ALTERNATIVE_STRINGS);

        source
                .switchMap(new Func1<String, Observable<String>>() {

                    @Override
                    public Observable<String> call(String s) {
                        return checkSwitchCondition(s) ? output1 : output2;
                    }

                })
                .map(new Func1<String, String>() {

                    @Override
                    public String call(String s) {
                        checkThreads();
                        return s;
                    }

                })
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.io())
                .subscribe(createObserver(expectedForSwitchMap()));

    }

    @Override
    public void testJustConcatWith() throws Exception {

        String[] remainingValues = new String[STRING_VALUES.length - 1];
        System.arraycopy(STRING_VALUES, 1, remainingValues, 0, remainingValues.length);

        Observable
                .just(STRING_VALUES[0])
                .concatWith(Observable.from(remainingValues))
                .map(new Func1<String, String>() {

                    @Override
                    public String call(String s) {
                        checkThreads();
                        return s;
                    }

                })
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.io())
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
                .map(new Func1<String, String>() {

                    @Override
                    public String call(String s) {
                        checkThreads();
                        return s;
                    }

                })
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.io())
                .subscribe(createObserver(expected));

    }

    @Override
    protected void onObservedFinish() {
        checkThreadsFromObserver();
        new Thread() {

            @Override
            public void run() {

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    fail();
                }

                unlock();

            }

        }.start();
    }

    @Override
    protected void onObservedError(Throwable e) {
        checkThreadsFromObserver();
    }

    @Override
    protected <T> void onObservedValue(T t) {
        checkThreadsFromObserver();
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        mainThread = Thread.currentThread();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        synchronized (lock) {
            lock.wait();
        }
        assertEquals(1, computationThreads.size());
        computationThreads.clear();
        mainThread = null;
    }

    protected void checkThreads() {
        final Thread computationThread = Thread.currentThread();
        if (!computationThreads.contains(computationThread)) {
            computationThreads.add(computationThread);
        }
        assertNotSame(mainThread, computationThread);
    }

    protected void checkThreadsFromObserver() {
        final Thread expected = Thread.currentThread();
        assertNotSame(expected, mainThread);
        for (Thread thread : computationThreads) {
            assertNotSame(expected, thread);
        }
    }

}
