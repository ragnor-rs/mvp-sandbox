package io.reist.sandbox.user.model.local;

import com.fernandocejas.frodo.annotation.RxLogObservable;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.operations.put.PutResult;
import com.pushtorefresh.storio.sqlite.queries.Query;

import java.util.List;

import io.reist.sandbox.app.model.Repo;
import io.reist.sandbox.app.model.Response;
import io.reist.sandbox.app.model.User;
import io.reist.sandbox.app.model.local.ReposTable;
import rx.Observable;

public class StorIoUserReposService
        implements LocalUserReposService
{

    protected final StorIOSQLite storIOSQLite;

    public StorIoUserReposService(StorIOSQLite storIoSqLite) {
        this.storIOSQLite = storIoSqLite;
    }

    @RxLogObservable
    @Override
    public Observable<Response<List<Repo>>> findReposByUser(User user) {
        return storIOSQLite
                .get()
                .listOfObjects(Repo.class)
                .withQuery(Query
                        .builder()
                        .table(ReposTable.NAME)
                        .where(ReposTable.Column.USER_ID + " =?")
                        .whereArgs(user.id)
                        .build())
                .prepare()
                .createObservable()
                .map(Response::new);
    }

    @RxLogObservable
    @Override
    public boolean saveSync(User user, List<Repo> repos) {
        PutResult putResult = storIOSQLite
                .put()
                .object(repos)
                .prepare()
                .executeAsBlocking();

        return putResult.wasInserted() || putResult.wasUpdated();
    }

    @Override
    public void unlike(User user, Repo repo) {
        like(user, repo, 0);
    }

    @Override
    public void like(User user, Repo repo) {
        like(user, repo, 1);
    }

    private void like(final User user, final Repo repo, int likedByMe) {
//        ContentValues contentValues = new ContentValues();
//
//        contentValues.put(UserWithRepoTable.Column.LIKED_BY_ME, likedByMe);
//
//        storIoSqLite
//                .put()
//                .object(contentValues)
//                .withPutResolver(new UserWithRepoContentValuesPutResolver(user.id, repo.id))
//                .prepare()
//                .executeAsBlocking();
    }
}
