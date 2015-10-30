package io.reist.sandbox.core.rx;

import android.support.annotation.NonNull;

import junit.framework.TestCase;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Reist on 10/26/15.
 */
public abstract class RxTestCase extends TestCase {

    protected static final String[] STRING_VALUES = new String[] {"one", "", "three"};
    protected static final String[] MORE_STRING_VALUES = new String[] {"one_remote", "two_remote", "three_remote"};
    protected static final String[] MORE_ALTERNATIVE_STRINGS = new String[] {"I", "II", "III"};

    protected static final int ELEMENTS_TO_TAKE = 2;

    static {
        assertTrue(STRING_VALUES.length > ELEMENTS_TO_TAKE);
        assertTrue(MORE_STRING_VALUES.length > ELEMENTS_TO_TAKE);
        assertTrue(MORE_ALTERNATIVE_STRINGS.length > ELEMENTS_TO_TAKE);
    }

    private boolean anItemObserved;
    private int onCompletedCallCounter;
    private final List<Throwable> errors = new ArrayList<>();
    private Subscription subscription;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        errors.clear();
        anItemObserved = false;
        onCompletedCallCounter = 0;
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
        subscription.unsubscribe();
        if (!errors.isEmpty()) {
            StringWriter writer = new StringWriter();
            writer.append("\n\n---\n\n");
            for (Throwable e : errors) {
                e.printStackTrace(new PrintWriter(writer));
            }
            writer.append("\n---\n");
            fail(writer.toString());
        }
        assertTrue("anItemObserved == false", anItemObserved);
        assertTrue("onCompletedCallCounter == 0", onCompletedCallCounter > 0);
    }

    protected void onObservedError(Throwable e) {
        errors.add(e);
    }

    protected void onObservedFinish() {
        onCompletedCallCounter++;
    }

    protected <T> void onObservedValue(T expected, T t) {
        assertEquals(expected, t);
        anItemObserved = true;
    }

    @NonNull
    protected final Observable<String> createObservable() {
        return Observable.from(STRING_VALUES);
    }

    @NonNull
    protected final <T> TestObserver<T> createObserver(T[] expected) {
        return new TestObserver<>(expected, this);
    }

    @NonNull
    protected final <T> Action1<T> createForEachAction(final T[] expected) {
        return new Action1<T>() {

            private int itemCounter = 0;

            @Override
            public void call(T t) {
                assertEquals(expected[itemCounter], t);
                itemCounter++;
            }

        };
    }

    public final void testRx() throws Exception {
        subscription = doTestRx();
    }

    public final void testDoNext() throws Exception {
        subscription = doTestDoNext();
    }

    public final void testMap() throws Exception {
        subscription = doTestMap();
    }

    public final void testConcatWith() throws Exception {
        subscription = doTestConcatWith();
    }

    public final void testTake() throws Exception {
        subscription = doTestTake();
    }

    public final void testFirst() throws Exception {
        subscription = doTestFirst();
    }

    public final void testLast() throws Exception {
        subscription = doTestLast();
    }

    public final void testSwitchMap() throws Exception {
        subscription = doTestSwitchMap();
    }

    public final void testCache() throws Exception {
        subscription = doTestCache();
    }

    public final void testJustConcatWith() throws Exception {
        subscription = doTestJustConcatWith();
    }

    public abstract Subscription doTestRx() throws Exception;

    public abstract Subscription doTestDoNext() throws Exception;

    public abstract Subscription doTestMap() throws Exception;

    public abstract Subscription doTestConcatWith() throws Exception;

    public abstract Subscription doTestTake() throws Exception;

    public abstract Subscription doTestFirst() throws Exception;

    public abstract Subscription doTestLast() throws Exception;

    public abstract Subscription doTestSwitchMap() throws Exception;

    public abstract Subscription doTestCache() throws Exception;

    public abstract Subscription doTestJustConcatWith() throws Exception;

    @NonNull
    protected static Integer[] expectedForMap() {
        final Integer[] expected = new Integer[STRING_VALUES.length];
        for (int i = 0; i < expected.length; i++) {
            final String s = STRING_VALUES[i];
            expected[i] = s.length();
        }
        return expected;
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
        for (String s : STRING_VALUES) {
            if (checkSwitchCondition(s)) {
                expectedList.add(MORE_STRING_VALUES[0]);
            } else {
                expectedList.add(MORE_ALTERNATIVE_STRINGS[0]);
            }
        }
        return expectedList.toArray(new String[expectedList.size()]);
    }

    @NonNull
    protected static String[] expectedForCache() {
        List<String> expectedList = new ArrayList<>();
        for (String s : STRING_VALUES) {
            if (checkSwitchCondition(s)) {
                expectedList.add(MORE_STRING_VALUES[0]);
            } else {
                expectedList.add(s);
            }
        }
        return expectedList.toArray(new String[expectedList.size()]);
    }

    @NonNull
    protected static String[] expectedForTake() {
        String[] expected = new String[ELEMENTS_TO_TAKE];
        System.arraycopy(STRING_VALUES, 0, expected, 0, ELEMENTS_TO_TAKE);
        return expected;
    }

    @NonNull
    protected static String[] expectedForFirst() {
        final String firstValue = STRING_VALUES[0];
        String[] expected = new String[ELEMENTS_TO_TAKE];
        for (int i = 0; i < expected.length; i++) {
            expected[i] = firstValue;
        }
        return expected;
    }

    @NonNull
    protected static String[] expectedForLast() {
        final String lastValue = STRING_VALUES[STRING_VALUES.length - 1];
        String[] expected = new String[ELEMENTS_TO_TAKE];
        for (int i = 0; i < expected.length; i++) {
            expected[i] = lastValue;
        }
        return expected;
    }

    public static boolean checkSwitchCondition(String s) {
        return s == null || s.isEmpty();
    }

}
