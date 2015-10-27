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
        mainThread = null;
    }

    @Override
    public void testRx() throws Exception {
        createObservable()
                .forEach(new Action1<String>() {

                    @Override
                    public void call(String s) {
                        checkThreads();
                    }

                })
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.io())
                .subscribe(createObserver(STRING_VALUES));
    }

    protected void checkThreads() {
        final Thread computationThread = Thread.currentThread();
        if (!computationThreads.contains(computationThread)) {
            computationThreads.add(computationThread);
        }
        assertNotSame(mainThread, computationThread);
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

                        checkThreads();

                    }

                })
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.io())
                .subscribe(createObserver(STRING_VALUES));
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
                .forEach(new Action1<Integer>() {

                    @Override
                    public void call(Integer i) {
                        assertNotSame(mainThread, Thread.currentThread());
                    }

                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.immediate())
                .subscribe(createObserver(expectedForMap()));
    }

    @Override
    public void testSample() throws Exception {

        final long expectedPeriod = expectedForSample();

        createObservable()
                .sample(PERIOD_VALUE, PERIOD_UNIT)
                .forEach(new Action1<String>() {

                    private long startTime = -1;

                    @Override
                    public void call(String s) {

                        final long now = System.currentTimeMillis();
                        if (startTime != -1) {
                            final long realPeriod = now - startTime;
                            assertTrue(Math.abs(realPeriod - expectedPeriod) < 0.1 * expectedPeriod);
                        }
                        startTime = now;

                        assertNotSame(mainThread, Thread.currentThread());

                    }

                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.immediate())
                .subscribe(createObserver(STRING_VALUES));

    }

    @Override
    public void testConcatWith() throws Exception {
        createObservable()
                .concatWith(
                        Observable.from(MORE_STRING_VALUES)
                )
                .forEach(new Action1<String>() {

                    @Override
                    public void call(String i) {
                        assertNotSame(mainThread, Thread.currentThread());
                    }

                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.immediate())
                .subscribe(createObserver(expectedForConcatWith()));
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
                .forEach(new Action1<String>() {

                    @Override
                    public void call(String i) {
                        assertNotSame(mainThread, Thread.currentThread());
                    }

                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.immediate())
                .subscribe(createObserver(expectedForSwitchMap()));

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
                .forEach(new Action1<String>() {

                    @Override
                    public void call(String i) {
                        assertNotSame(mainThread, Thread.currentThread());
                    }

                })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.immediate())
                .subscribe(createObserver(expected));

    }

    @Override
    protected <T> RxTestCase.TestObserver<T> createObserver(T[] expected) {
        final TestObserver<T> observer = super.createObserver(expected);
        observer.setLock(lock);
        observer.setComputationThreads(computationThreads);
        return observer;
    }

}
