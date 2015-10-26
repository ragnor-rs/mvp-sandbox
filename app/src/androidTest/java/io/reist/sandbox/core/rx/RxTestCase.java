package io.reist.sandbox.core.rx;

import android.support.annotation.NonNull;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Reist on 10/26/15.
 */
public abstract class RxTestCase extends TestCase {

    protected static final String[] STRING_VALUES = new String[] {"one", "", "three"};
    protected static final String[] MORE_STRING_VALUES = new String[] {"one_remote", "two_remote", "three_remote"};
    protected static final String[] MORE_ALTERNATIVE_STRINGS = new String[] {"I", "II", "III"};

    protected static final long PERIOD_VALUE = 1;
    protected static final TimeUnit PERIOD_UNIT = TimeUnit.SECONDS;

    /**
     * Created by Reist on 10/26/15.
     */
    protected static class TestObserver<T> implements Observer<T> {

        private final T[] expected;

        private Object lock;

        private int itemCounter;
        private int onCompletedCallCounter;

        protected TestObserver(T[] expected) {

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
            assertEquals(expected.length, itemCounter);
            assertFalse(onCompletedCallCounter > 1);
            if (lock != null) {
                synchronized (lock) {
                    lock.notifyAll();
                }
            }
        }

        public void setLock(Object lock) {
            this.lock = lock;
        }

    }

    @NonNull
    protected Observable<String> createObservable() {
        return Observable.from(STRING_VALUES);
    }

    protected <T> RxTestCase.TestObserver<T> createObserver(T[] expected) {
        return new TestObserver<>(expected);
    }

    public abstract void testRx() throws Exception;

    public abstract void testForEach() throws Exception;

    public abstract void testMap() throws Exception;

    public abstract void testSample() throws Exception;

    public abstract void testConcatWith() throws Exception;

    public abstract void testSwitchMap() throws Exception;

    @NonNull
    protected static Integer[] expectedForMap() {
        final Integer[] expected = new Integer[STRING_VALUES.length];
        for (int i = 0; i < expected.length; i++) {
            final String s = STRING_VALUES[i];
            expected[i] = s.length();
        }
        return expected;
    }

    protected static long expectedForSample() {
        return TimeUnit.MILLISECONDS.convert(PERIOD_VALUE, PERIOD_UNIT);
    }

    @NonNull
    protected static String[] expectedForConcatWith() {
        String[] expected = new String[STRING_VALUES.length + MORE_STRING_VALUES.length];
        List<String> strings = new ArrayList<>();
        Collections.addAll(strings, STRING_VALUES);
        Collections.addAll(strings, MORE_STRING_VALUES);
        expected = strings.toArray(expected);
        return expected;
    }

    @NonNull
    protected static String[] expectedForSwitchMap() {
        List<String> expectedList = new ArrayList<>();
        int pointerToAlternative1 = 0;
        int pointerToAlternative2 = 0;
        for (String s : STRING_VALUES) {
            if (checkSwitchCondition(s)) {
                expectedList.add(MORE_STRING_VALUES[pointerToAlternative1]);
                pointerToAlternative1++;
            } else {
                expectedList.add(MORE_ALTERNATIVE_STRINGS[pointerToAlternative2]);
                pointerToAlternative2++;
            }
        }
        return expectedList.toArray(new String[expectedList.size()]);
    }

    public static boolean checkSwitchCondition(String s) {
        return s == null || s.isEmpty();
    }

}
