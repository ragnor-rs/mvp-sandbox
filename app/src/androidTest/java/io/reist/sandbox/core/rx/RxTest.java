package io.reist.sandbox.core.rx;

import junit.framework.TestCase;

/**
 * Created by Reist on 10/23/15.
 */
public class RxTest extends TestCase {

    public static final String[] STRINGS = new String[]{"one", "two", "three"};

    public static final Integer[] STRING_LENGTHS = new Integer[STRINGS.length];

    static {
        for (int i = 0; i < STRING_LENGTHS.length; i++) {
            STRING_LENGTHS[i] = STRINGS[i].length();
        }
    }

    private final Object lock = new Object();

    public void testRxOnSingleThread() throws Exception {
        Observable
                .from(STRINGS)
                .subscribe(new TestObserver<>(STRINGS));
    }

    public void testRxOnTwoThreads() throws Exception {

        final Thread observerThread = Thread.currentThread();

        Observable
                .from(STRINGS)
                .forEach(new Action1<String>() {

                    @Override
                    public void call(String s) {
                        assertNotSame(observerThread, Thread.currentThread());
                    }

                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.immediate())
                .subscribe(new TestObserver<>(STRINGS));

        synchronized (lock) {
            lock.wait();
        }

    }

    public void testConcatMapOnSingleThread() throws Exception {

        Observable
                .from(STRINGS)
                .concatMap(new Func1<String, Integer>() {

                    @Override
                    public Integer call(String s) {
                        return s.length();
                    }

                })
                .subscribe(new TestObserver<>(STRING_LENGTHS));

    }

    public void testConcatMapOnTwoThreads() throws Exception {

        final Thread observerThread = Thread.currentThread();

        Observable
                .from(STRINGS)
                .concatMap(new Func1<String, Integer>() {

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
            assertEquals(STRINGS.length, itemCounter);
            assertFalse(onCompletedCallCounter > 1);
            synchronized (lock) {
                lock.notifyAll();
            }
        }

    }

}
