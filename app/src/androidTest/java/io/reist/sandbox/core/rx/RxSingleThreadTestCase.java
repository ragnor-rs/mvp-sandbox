package io.reist.sandbox.core.rx;

import rx.Observable;
import rx.Subscription;
import rx.functions.Func1;

/**
 * Created by Reist on 10/23/15.
 */
public class RxSingleThreadTestCase extends RxTestCase {

    @Override
    public Subscription doTestRx() throws Exception {
        return createObservable()
                .subscribe(createObserver(STRING_VALUES));
    }

    @Override
    public Subscription doTestDoNext() throws Exception {
        return createObservable()
                .doOnNext(createForEachAction(STRING_VALUES))
                .subscribe(createObserver(STRING_VALUES));
    }

    @Override
    public Subscription doTestMap() throws Exception {
        return createObservable()
                .map(new Func1<String, Integer>() {

                    @Override
                    public Integer call(String s) {
                        return s.length();
                    }

                })
                .subscribe(createObserver(expectedForMap()));
    }

    @Override
    public Subscription doTestConcatWith() throws Exception {
        return createObservable()
                .concatWith(
                        Observable.from(MORE_STRING_VALUES)
                )
                .subscribe(createObserver(expectedForConcatWith()));
    }

    @Override
    public Subscription doTestFirst() throws Exception {
        final String[] expected = expectedForFirst();
        return createObservable()
                .take(1)
                .doOnNext(createForEachAction(expected))
                .subscribe(createObserver(expected));
    }

    @Override
    public Subscription doTestSwitchMap() throws Exception {

        final Observable<String> source = createObservable();
        final Observable<String> output1 = Observable.from(MORE_STRING_VALUES);
        final Observable<String> output2 = Observable.from(MORE_ALTERNATIVE_STRINGS);

        final String[] expected = expectedForSwitchMap();

        return source
                .switchMap(new Func1<String, Observable<String>>() {

                    @Override
                    public Observable<String> call(String s) {
                        return (checkSwitchCondition(s) ? output1 : output2).take(1);
                    }


                })
                .subscribe(createObserver(expected));

    }

    @Override
    public Subscription doTestJustConcatWith() throws Exception {

        String[] remainingValues = new String[STRING_VALUES.length - 1];
        System.arraycopy(STRING_VALUES, 1, remainingValues, 0, remainingValues.length);

        return Observable
                .just(STRING_VALUES[0])
                .concatWith(Observable.from(remainingValues))
                .subscribe(createObserver(STRING_VALUES));

    }

    @Override
    public Subscription doTestCache() throws Exception {

        final Observable<String> local = Observable.from(RxTestCase.STRING_VALUES);
        final Observable<String> remote = Observable.from(RxTestCase.MORE_STRING_VALUES);

        String[] expected = expectedForCache();

        return local
                .switchMap(new Func1<String, Observable<String>>() {

                    @Override
                    public Observable<String> call(String s) {
                        return RxTestCase.checkSwitchCondition(s) ? remote.take(1) : Observable.just(s);
                    }

                })
                .subscribe(createObserver(expected));

    }

}
