package io.reist.sandbox.core.rx;

import android.support.annotation.NonNull;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Reist on 10/26/15.
 */
public class ExperimentalTest extends TestCase {

    public void testCacheOnOneThread() throws Exception {

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
                .subscribe(new RxTestCase.TestObserver<>(expected));

    }

    @NonNull
    protected static String[] expectedForCache() {
        List<String> expectedList = new ArrayList<>();
        int pointerToAlternative = 0;
        for (String s : RxTestCase.STRING_VALUES) {
            if (RxTestCase.checkSwitchCondition(s)) {
                expectedList.add(RxTestCase.MORE_STRING_VALUES[pointerToAlternative]);
                pointerToAlternative++;
            } else {
                expectedList.add(s);
            }
        }
        return expectedList.toArray(new String[expectedList.size()]);
    }

}
