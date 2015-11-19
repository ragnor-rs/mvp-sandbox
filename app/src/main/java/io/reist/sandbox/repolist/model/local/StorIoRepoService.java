package io.reist.sandbox.repolist.model.local;

import com.fernandocejas.frodo.annotation.RxLogObservable;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.operations.delete.DeleteResult;
import com.pushtorefresh.storio.sqlite.queries.DeleteQuery;
import com.pushtorefresh.storio.sqlite.queries.Query;

import java.util.List;

import io.reist.sandbox.app.model.Repo;
import io.reist.sandbox.app.model.Response;
import io.reist.sandbox.app.model.local.ReposTable;
import io.reist.sandbox.core.model.local.StorIoService;
import io.reist.sandbox.repolist.model.RepoService;
import rx.Observable;

public class StorIoRepoService extends StorIoService<Repo> implements RepoService {

    public StorIoRepoService(StorIOSQLite storIoSqLite) {
        super(storIoSqLite);
    }

    @RxLogObservable
    @Override
    public Observable<Response<List<Repo>>> list() {
        return preparedGetBuilder(Repo.class)
                .withQuery(Query.builder().table(ReposTable.NAME).build())
                .prepare()
                .createObservable()
                .map(Response::new);
    }

    @RxLogObservable
    @Override
    public Observable<Response<Repo>> byId(Long id) {
        return unique(Repo.class, ReposTable.NAME, id)
                .map(Response<Repo>::new);
    }

    @Override
    public Observable<Integer> delete(Long id) {
        return storIoSqLite        //cur if that's fine to make storIoSqLite protected?
                .delete()
                .byQuery(
                        DeleteQuery.builder()
                                .table(ReposTable.NAME)
                                .where(ReposTable.Column.ID + " = ?")
                                .whereArgs(id)
                                .build()
                )
                .prepare() // BTW: it will use transaction!
                .createObservable()
                .map(DeleteResult::numberOfRowsDeleted);
    }

    @RxLogObservable
    @Override
    public Observable<Response<List<Repo>>> findReposByUserId(Long userId) {
        return preparedGetBuilder(Repo.class)
                .withQuery(
                        Query
                                .builder()
                                .table(ReposTable.NAME)
                                .where(ReposTable.Column.USER_ID + " = ?")
                                .whereArgs(userId)
                                .orderBy(ReposTable.Column.ID)
                                .build())
                .prepare()
                .createObservable()
                .map(Response::new);
    }

    @Override
    public Observable<Response<Repo>> unlike(Repo repo) {
        return like(repo, false);
    }

    @Override
    public Observable<Response<Repo>> like(Repo repo) {
        return like(repo, true);
    }

    private Observable<Response<Repo>> like(final Repo repo, boolean likedByMe) {
        if (likedByMe) {
            repo.likeCount += 1;
        } else {
            repo.likeCount -= 1;
        }

        repo.likedByMe = likedByMe;

        preparedPutBuilder()
                .object(repo)
                .prepare()
                .executeAsBlocking();

        return Observable.just(repo).map(Response::new);
    }

}
