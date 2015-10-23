package io.reist.sandbox.repos.model.local.storio;

import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.queries.Query;

import java.util.List;

import io.reist.sandbox.core.model.AsyncRequest;
import io.reist.sandbox.core.model.BackgroundOp;
import io.reist.sandbox.core.model.EntityService;
import io.reist.sandbox.core.model.local.storio.StorIoService;
import io.reist.sandbox.repos.model.Repo;
import io.reist.sandbox.repos.model.RepoService;

/**
 * Created by Reist on 10/16/15.
 */
public class StorIoRepoService extends StorIoService<Repo> implements EntityService<Repo>, RepoService {

    public StorIoRepoService(StorIOSQLite storIoSqLite) {
        super(storIoSqLite);
    }

    @Override
    public AsyncRequest<List<Repo>> listRepos(String user) {
        return createRequest(new BackgroundOp<List<Repo>>() {

            @Override
            public List<Repo> execute() {
                return get()
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
