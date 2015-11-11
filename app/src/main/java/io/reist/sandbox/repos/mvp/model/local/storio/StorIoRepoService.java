package io.reist.sandbox.repos.mvp.model.local.storio;

import com.fernandocejas.frodo.annotation.RxLogObservable;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.operations.delete.DeleteResult;
import com.pushtorefresh.storio.sqlite.queries.DeleteQuery;
import com.pushtorefresh.storio.sqlite.queries.Query;

import java.util.List;

import io.reist.sandbox.app.mvp.model.ResponseModel;
import io.reist.sandbox.core.mvp.model.local.storio.StorIoService;
import io.reist.sandbox.repos.mvp.model.Repo;
import io.reist.sandbox.repos.mvp.model.RepoService;
import rx.Observable;

public class StorIoRepoService extends StorIoService<Repo> implements RepoService {

    public StorIoRepoService(StorIOSQLite storIoSqLite) {
        super(storIoSqLite);
    }

    @RxLogObservable
    @Override
    public Observable<ResponseModel<List<Repo>>> list() {
        return preparedGetBuilder(Repo.class)
                .withQuery(Query.builder().table(ReposTable.TABLE_NAME).build())
                .prepare()
                .createObservable()
                .map(ResponseModel::new);
    }

    @RxLogObservable
    @Override
    public Observable<ResponseModel<Repo>> byId(Long id) {
        return unique(Repo.class, ReposTable.TABLE_NAME, id)
                .map(ResponseModel<Repo>::new);
    }

    @Override
    public Observable<Integer> delete(Long id) {
        return storIoSqLite        //cur if that's fine to make storIoSqLite protected?
                .delete()
                .byQuery(
                        DeleteQuery.builder()
                                .table(ReposTable.TABLE_NAME)
                                .where(ReposTable.COLUMN_ID + " = ?")
                                .whereArgs(id)
                                .build()
                )
                .prepare() // BTW: it will use transaction!
                .createObservable()
                .map(DeleteResult::numberOfRowsDeleted);
    }

}
