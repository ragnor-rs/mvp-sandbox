package io.reist.sandbox.core.rx;

import junit.framework.TestCase;

import java.util.concurrent.TimeUnit;

/**
 * Created by Reist on 10/23/15.
 */
public class RxTest extends TestCase {

    public static final String[] STRING_VALUES = new String[]{"one", "two", "three"};
    public static final Integer[] STRING_LENGTHS = new Integer[STRING_VALUES.length];

    private static final long PERIOD_VALUE = 1;
    private static final TimeUnit PERIOD_UNIT = TimeUnit.SECONDS;

    static {
        for (int i = 0; i < STRING_LENGTHS.length; i++) {
            STRING_LENGTHS[i] = STRING_VALUES[i].length();
        }
    }

    private final Object lock = new Object();

    public void testRxOnSingleThread() throws Exception {
        Observable
                .from(STRING_VALUES)
                .subscribe(new TestObserver<>(STRING_VALUES));
    }

    public void testRxOnTwoThreads() throws Exception {

        final Thread observerThread = Thread.currentThread();

        Observable
                .from(STRING_VALUES)
                .forEach(new Action1<String>() {

                    @Override
                    public void call(String s) {
                        assertNotSame(observerThread, Thread.currentThread());
                    }

                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.immediate())
                .subscribe(new TestObserver<>(STRING_VALUES));

        synchronized (lock) {
            lock.wait();
        }

    }

    public void testForEachOnSingleThread() throws Exception {
        Observable
                .from(STRING_VALUES)
                .forEach(new Action1<String>() {

                    private int i = 0;

                    @Override
                    public void call(String s) {
                        assertEquals(STRING_VALUES[i], s);
                        i++;
                    }

                })
                .subscribe(new TestObserver<>(STRING_VALUES));
    }

    public void testForEachOnTwoThreads() throws Exception {

        final Thread observerThread = Thread.currentThread();

        Observable
                .from(STRING_VALUES)
                .forEach(new Action1<String>() {

                    private int i = 0;

                    @Override
                    public void call(String s) {

                        assertEquals(STRING_VALUES[i], s);
                        i++;

                        assertNotSame(observerThread, Thread.currentThread());

                    }

                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.immediate())
                .subscribe(new TestObserver<>(STRING_VALUES));

        synchronized (lock) {
            lock.wait();
        }

    }

    public void testMapOnSingleThread() throws Exception {
        Observable
                .from(STRING_VALUES)
                .map(new Func1<String, Integer>() {

                    @Override
                    public Integer call(String s) {
                        return s.length();
                    }

                })
                .subscribe(new TestObserver<>(STRING_LENGTHS));
    }

    public void testMapOnTwoThreads() throws Exception {

        final Thread observerThread = Thread.currentThread();

        Observable
                .from(STRING_VALUES)
                .map(new Func1<String, Integer>() {

                    @Override
                    public Integer call(String s) {
                        return s.length();
                    }

                })
                .forEach(new Action1<Integer>() {

                    @Override
                    public void call(Integer i) {
                        assertNotSame(observerThread, Thread.currentThread());
                    }

                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.immediate())
                .subscribe(new TestObserver<>(STRING_LENGTHS));

        synchronized (lock) {
            lock.wait();
        }

    }

    public void testSampleOnSingleThread() throws Exception {

        final long expectedPeriod = TimeUnit.MILLISECONDS.convert(PERIOD_VALUE, PERIOD_UNIT);

        Observable
                .from(STRING_VALUES)
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
                    }

                })
                .subscribe(new TestObserver<>(STRING_VALUES));

    }

    public void testSampleOnTwoThreads() throws Exception {

        final Thread observerThread = Thread.currentThread();

        final long expectedPeriod = TimeUnit.MILLISECONDS.convert(PERIOD_VALUE, PERIOD_UNIT);

        Observable
                .from(STRING_VALUES)
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

                        assertNotSame(observerThread, Thread.currentThread());

                    }

                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.immediate())
                .subscribe(new TestObserver<>(STRING_VALUES));

        synchronized (lock) {
            lock.wait();
        }

    }

    private class TestObserver<T> implements Observer<T> {

        private final T[] expected;

        private int itemCounter;

        private int onCompletedCallCounter;

        public TestObserver(T[] expected) {
            this.expected = expected;
            itemCounter = 0;
            onCompletedCallCounter = 0;
        }

        @Override
        public void onNext(T t) {
            assertEquals(expected[itemCounter], t);
            itemCounter++;
        }

        @Override
        public void onError(Throwable e) {
            throw new RuntimeException(e);
        }

        @Override
        public void onCompleted() {
            onCompletedCallCounter++;
            assertEquals(STRING_VALUES.length, itemCounter);
            assertFalse(onCompletedCallCounter > 1);
            synchronized (lock) {
                lock.notifyAll();
            }
        }

    }

}
