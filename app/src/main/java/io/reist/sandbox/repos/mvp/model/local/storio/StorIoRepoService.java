package io.reist.sandbox.repos.mvp.model.local.storio;

import com.fernandocejas.frodo.annotation.RxLogObservable;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.queries.Query;

import java.util.List;

import io.reist.sandbox.core.mvp.model.local.storio.StorIoService;
import io.reist.sandbox.repos.mvp.model.Repo;
import rx.Observable;

public class StorIoRepoService extends StorIoService<Repo> {

    public StorIoRepoService(StorIOSQLite storIoSqLite) {
        super(storIoSqLite);
    }

    @RxLogObservable
    @Override
    public Observable<List<Repo>> list() {
        return preparedGetBuilder(Repo.class)
                .withQuery(Query.builder().table(ReposTable.TABLE_NAME).build())
                .prepare()
                .createObservable();
    }

    @RxLogObservable
    @Override
    public Observable<Repo> byId(Long id) {
        return unique(Repo.class, ReposTable.TABLE_NAME, id);
    }

}
