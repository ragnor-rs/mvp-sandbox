package io.reist.sandbox.repos.mvp.model;

import io.reist.sandbox.core.mvp.model.CachedListObservable;
import io.reist.sandbox.core.mvp.model.ListObservable;

/**
 * Created by Reist on 10/23/15.
 */
public class CachedRepoListObservable extends CachedListObservable<Repo> {

    public CachedRepoListObservable(
            ListObservable<Repo> localObservable,
            ListObservable<Repo> remoteObservable
    ) {
        super(localObservable, remoteObservable);
    }

}
