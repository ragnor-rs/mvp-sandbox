package io.reist.sandbox.repos.mvp.model.local.storio;

import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.queries.Query;

import java.util.List;

import io.reist.sandbox.core.mvp.model.local.storio.StorIoListOnSubscribe;
import io.reist.sandbox.core.rx.Subscriber;
import io.reist.sandbox.repos.mvp.model.Repo;

/**
 * Created by Reist on 10/23/15.
 */
public class StorIoRepoListOnSubscribe extends StorIoListOnSubscribe<Repo> {

    public StorIoRepoListOnSubscribe(StorIOSQLite storIoSqLite) {
        super(storIoSqLite);
    }

    @Override
    public void call(Subscriber<List<Repo>> listSubscriber) {
        listSubscriber.onNext(
                preparedGetBuilder().listOfObjects(Repo.class)
                        .withQuery(
                                Query.builder()
                                        .table(ReposTable.TABLE_NAME)
                                        .build()
                        )
                        .prepare()
                        .executeAsBlocking()
        );
    }

}
