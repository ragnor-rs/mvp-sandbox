package io.reist.sandbox.user.model.local;

import com.fernandocejas.frodo.annotation.RxLogObservable;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.operations.put.PutResult;
import com.pushtorefresh.storio.sqlite.queries.RawQuery;

import java.util.ArrayList;
import java.util.List;

import io.reist.sandbox.app.model.Like;
import io.reist.sandbox.app.model.Repo;
import io.reist.sandbox.app.model.Response;
import io.reist.sandbox.app.model.User;
import io.reist.sandbox.app.model.local.LikeTable;
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
                .withQuery(RawQuery
                        .builder()
                        .query("SELECT * FROM " + ReposTable.NAME +
                                " LEFT JOIN " + LikeTable.NAME +
                                " ON " +
                                ReposTable.NAME + "." + ReposTable.Column.ID + " = " +
                                LikeTable.NAME + "." + LikeTable.Column.REPO_ID +
                                " WHERE " + ReposTable.Column.USER_ID + " = '" + user.id + "'")
                        .observesTables(ReposTable.NAME, LikeTable.NAME)
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
    public void unlike(Repo repo) {
        like(repo, false);
    }

    @Override
    public void like(Repo repo) {
        like(repo, true);
    }

    private void like(final Repo repo, boolean likedByMe) {
        if (likedByMe) {
            repo.likeCount += 1;
        } else {
            repo.likeCount -= 1;
        }

        List<Object> objects = new ArrayList<>();

        objects.add(repo);
        objects.add(new Like(repo.id, likedByMe));

        storIOSQLite
                .put()
                .objects(objects)
                .prepare()
                .executeAsBlocking();
    }
}
