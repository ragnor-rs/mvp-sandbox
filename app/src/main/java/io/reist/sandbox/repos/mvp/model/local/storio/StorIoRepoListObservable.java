package io.reist.sandbox.repos.mvp.model.local.storio;

import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.queries.Query;

import java.util.List;

import io.reist.sandbox.core.mvp.model.local.storio.StorIoListObservable;
import io.reist.sandbox.repos.mvp.model.Repo;

/**
 * Created by Reist on 10/23/15.
 */
public class StorIoRepoListObservable extends StorIoListObservable<Repo> {

    public StorIoRepoListObservable(StorIOSQLite storIoSqLite) {
        super(storIoSqLite);
    }

    @Override
    protected List<Repo> get() {
        return preparedGetBuilder().listOfObjects(Repo.class)
                .withQuery(
                        Query.builder()
                                .table(ReposTable.TABLE_NAME)
                                .build()
                )
                .prepare()
                .executeAsBlocking();
    }

}
