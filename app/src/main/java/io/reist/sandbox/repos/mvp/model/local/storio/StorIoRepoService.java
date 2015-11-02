package io.reist.sandbox.repos.mvp.model.local.storio;

import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.queries.Query;

import java.util.List;

import io.reist.sandbox.core.mvp.model.local.storio.StorIoService;
import io.reist.sandbox.core.rx.Observable;
import io.reist.sandbox.repos.mvp.model.Repo;
import io.reist.sandbox.repos.mvp.model.RepoService;

public class StorIoRepoService extends StorIoService<Repo> implements RepoService {

    public StorIoRepoService(StorIOSQLite storIoSqLite) {
        super(storIoSqLite);
    }

    @Override
    public Observable<List<Repo>> list() {
        return toObservable(
                        preparedGetBuilder(Repo.class)
                        .withQuery(
                                Query.builder()
                                        .table(ReposTable.TABLE_NAME)
                                        .build()
                        )
                );
    }

    @Override
    public Observable<Repo> byId(Long id) {
        return unique(Repo.class, ReposTable.TABLE_NAME, id);
    }

}
