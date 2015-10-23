package io.reist.sandbox.core.rx;

import junit.framework.TestCase;

/**
 * Created by Reist on 10/23/15.
 */
public class RxTest extends TestCase {

    public void testRxOnSingleThread() throws Exception {
        final String[] items = {"one", "two", "three"};
        Observable.from(items).subscribe(new Observer<String>() {

            private int pointer = 0;

            private int onCompletedCallCounter = 0;

            @Override
            public void onNext(String s) {
                assertEquals(items[pointer], s);
                pointer++;
            }

            @Override
            public void onError(Throwable e) {
                fail();
            }

            @Override
            public void onCompleted() {
                onCompletedCallCounter++;
                assertEquals(items.length, pointer);
                assertFalse(onCompletedCallCounter > 1);
            }

        });
    }

}
