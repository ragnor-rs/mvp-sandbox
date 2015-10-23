package io.reist.sandbox.core.mvp.model;

import java.util.List;

/**
 * Created by Reist on 10/23/15.
 */
public class CachedListObservable<I> extends ListObservable<I> {

    private static final String TAG = CachedListObservable.class.getName();

    public final ListObservable<I> localObservable;
    public final ListObservable<I> remoteObservable;

    public CachedListObservable(
            ListObservable<I> localObservable,
            ListObservable<I> remoteObservable
    ) {
        this.localObservable = localObservable;
        this.remoteObservable = remoteObservable;
    }

    @Override
    protected Integer put(List<I> list) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected List<I> get() {
        return localObservable.switchIfListEmpty(remoteObservable).get();
    }

}
