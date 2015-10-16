package io.reist.sandbox.repos.model.database;

import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.queries.Query;

import java.util.List;

import io.reist.sandbox.core.model.AsyncRequest;
import io.reist.sandbox.core.model.BackgroundOp;
import io.reist.sandbox.core.model.BackgroundService;
import io.reist.sandbox.repos.model.Repo;
import io.reist.sandbox.repos.model.RepoService;

/**
 * Created by Reist on 10/16/15.
 */
public class StorIoRepoService extends BackgroundService implements RepoService {

    private final StorIOSQLite storIoSqLite;

    public StorIoRepoService(StorIOSQLite storIoSqLite) {
        this.storIoSqLite = storIoSqLite;
    }

    @Override
    public AsyncRequest<List<Repo>> listRepos(String user) {
        return createRequest(new BackgroundOp<List<Repo>>() {

            @Override
            public List<Repo> execute() {
                return storIoSqLite.get()
                        .listOfObjects(Repo.class)
                        .withQuery(
                                Query.builder()
                                        .table(ReposTable.TABLE_NAME)
                                        .build()
                        )
                        .prepare()
                        .executeAsBlocking();
            }

        });
    }

}
