package io.reist.sandbox.core.rx;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Reist on 10/23/15.
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
    public void tearDown() throws Exception {
        synchronized (lock) {
            lock.wait();
        }
        super.tearDown();
    }

    @Override
    protected void onObservedError(Throwable e) {
        super.onObservedError(e);
        synchronized (lock) {
            lock.notify();
        }
    }

    @Override
    protected void onObservedFinish() {
        super.onObservedFinish();
        assertNotSame(mainThread, Thread.currentThread());
        synchronized (lock) {
            lock.notify();
        }
    }

    @Override
    protected <T> void onObservedValue(T expected, T t) {
        super.onObservedValue(expected, t);
        assertNotSame(mainThread, Thread.currentThread());
    }

    private <T> Action1<T> createThreadTestAction() {
        return new Action1<T>() {

            @Override
            public void call(T t) {
                final Thread thread = Thread.currentThread();
                if (!computationThreads.contains(thread)) {
                    computationThreads.add(thread);
                }
                assertNotSame(mainThread, thread);
            }

        };
    }

    @Override
    public Subscription doTestRx() throws Exception {
        return createObservable()
                .doOnNext(this.<String>createThreadTestAction())
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.io())
                .subscribe(createObserver(STRING_VALUES));
    }

    @Override
    public Subscription doTestDoNext() throws Exception {
        return createObservable()
                .doOnNext(createForEachAction(STRING_VALUES))
                .doOnNext(this.<String>createThreadTestAction())
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.io())
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
                .doOnNext(this.<Integer>createThreadTestAction())
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.io())
                .subscribe(createObserver(expectedForMap()));
    }

    @Override
    public Subscription doTestConcatWith() throws Exception {
        return createObservable()
                .concatWith(
                        Observable.from(MORE_STRING_VALUES)
                )
                .doOnNext(this.<String>createThreadTestAction())
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.io())
                .subscribe(createObserver(expectedForConcatWith()));
    }

    @Override
    public Subscription doTestTake() throws Exception {
        return createObservable()
                .take(ELEMENTS_TO_TAKE)
                .doOnNext(this.<String>createThreadTestAction())
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.io())
                .subscribe(createObserver(expectedForTake()));
    }


    @Override
    public Subscription doTestFirst() throws Exception {
        final String[] expected = expectedForFirst();
        return createObservable()
                .first()
                .take(ELEMENTS_TO_TAKE)
                .doOnNext(this.<String>createThreadTestAction())
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.io())
                .subscribe(createObserver(expected));
    }

    @Override
    public Subscription doTestLast() throws Exception {
        final String[] expected = expectedForLast();
        return createObservable()
                .last()
                .take(ELEMENTS_TO_TAKE)
                .doOnNext(this.<String>createThreadTestAction())
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.io())
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
                .doOnNext(this.<String>createThreadTestAction())
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.io())
                .subscribe(createObserver(expected));

    }

    @Override
    public Subscription doTestJustConcatWith() throws Exception {

        String[] remainingValues = new String[STRING_VALUES.length - 1];
        System.arraycopy(STRING_VALUES, 1, remainingValues, 0, remainingValues.length);

        return Observable
                .just(STRING_VALUES[0])
                .concatWith(Observable.from(remainingValues))
                .doOnNext(this.<String>createThreadTestAction())
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.io())
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
                .doOnNext(this.<String>createThreadTestAction())
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.io())
                .subscribe(createObserver(expected));

    }

}
