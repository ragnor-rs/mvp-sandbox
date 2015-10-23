package io.reist.sandbox.core.rx;

import junit.framework.TestCase;

/**
 * Created by Reist on 10/23/15.
 */
public class RxTest extends TestCase {

    public static final String[] ITEMS = new String[]{"one", "two", "three"};

    private final Object lock = new Object();

    public void testRxOnSingleThread() throws Exception {
        Observable
                .from(ITEMS)
                .subscribe(new TestStringObserver());
    }

    public void testRxOnTwoThreads() throws Exception {

        final Thread observerThread = Thread.currentThread();

        Observable
                .from(ITEMS)
                .forEach(new Action1<String>() {

                    @Override
                    public void call(String s) {
                        assertNotSame(observerThread, Thread.currentThread());
                    }

                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.immediate())
                .subscribe(new TestStringObserver());

        synchronized (lock) {
            lock.wait();
        }

    }

    private class TestStringObserver implements Observer<String> {

        private int pointer;

        private int onCompletedCallCounter;

        public TestStringObserver() {
            pointer = 0;
            onCompletedCallCounter = 0;
        }

        @Override
        public void onNext(String s) {
            assertEquals(ITEMS[pointer], s);
            pointer++;
        }

        @Override
        public void onError(Throwable e) {
            throw new RuntimeException(e);
        }

        @Override
        public void onCompleted() {
            onCompletedCallCounter++;
            assertEquals(ITEMS.length, pointer);
            assertFalse(onCompletedCallCounter > 1);
            synchronized (lock) {
                lock.notifyAll();
            }
        }

    }

}
