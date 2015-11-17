package io.reist.sandbox.app.model.local.resolvers;

import android.database.Cursor;
import android.support.annotation.NonNull;

import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.operations.get.GetResolver;
import com.pushtorefresh.storio.sqlite.queries.Query;
import com.pushtorefresh.storio.sqlite.queries.RawQuery;

import io.reist.sandbox.app.model.Repo;
import io.reist.sandbox.app.model.RepoStorIOSQLiteGetResolver;
import io.reist.sandbox.app.model.User;
import io.reist.sandbox.app.model.local.LikeTable;
import io.reist.sandbox.app.model.local.UserTable;

/**
 * Created by m039 on 11/17/15.
 */
public class RepoGetResolver extends GetResolver<Repo> {

    private final ThreadLocal<StorIOSQLite> storIOSQLiteFromPerformGet = new ThreadLocal<>();
    private final GetResolver<Repo> defaultGetResolver = new RepoStorIOSQLiteGetResolver();

    @NonNull
    @Override
    public Repo mapFromCursor(@NonNull Cursor cursor) {
        Repo repo = defaultGetResolver.mapFromCursor(cursor);

        int likedByMeIndex = cursor.getColumnIndex(LikeTable.Column.LIKED_BY_ME);
        if (likedByMeIndex != -1) {
            repo.likedByMe = cursor.getInt(likedByMeIndex) == 1;
        }

        final StorIOSQLite storIOSQLite = storIOSQLiteFromPerformGet.get();

        User owner = storIOSQLite
                .get()
                .listOfObjects(User.class)
                .withQuery(Query
                        .builder()
                        .table(UserTable.NAME)
                        .where(UserTable.Column.ID + " = ?")
                        .whereArgs(repo.userId)
                        .limit(1)
                        .build())
                .prepare()
                .executeAsBlocking()
                .get(0);

        repo.owner = owner;

        return repo;
    }

    @NonNull
    @Override
    public Cursor performGet(@NonNull StorIOSQLite storIOSQLite, @NonNull RawQuery rawQuery) {
        storIOSQLiteFromPerformGet.set(storIOSQLite);
        return storIOSQLite.internal().rawQuery(rawQuery);
    }

    @NonNull
    @Override
    public Cursor performGet(@NonNull StorIOSQLite storIOSQLite, @NonNull Query query) {
        storIOSQLiteFromPerformGet.set(storIOSQLite);
        return storIOSQLite.internal().query(query);
    }

}
