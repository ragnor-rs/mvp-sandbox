package io.reist.sandbox.app.model.local.resolvers;

import android.content.ContentValues;
import android.support.annotation.NonNull;

import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.operations.put.PutResolver;
import com.pushtorefresh.storio.sqlite.operations.put.PutResult;

import java.util.HashSet;
import java.util.Set;

import io.reist.sandbox.app.model.Repo;
import io.reist.sandbox.app.model.RepoStorIOSQLitePutResolver;
import io.reist.sandbox.app.model.local.ReposTable;
import io.reist.sandbox.app.model.local.UserTable;

/**
 * Created by m039 on 11/17/15.
 */
public class RepoPutResolver extends PutResolver<Repo> {

    PutResolver<Repo> defaultPutResolver = new RepoStorIOSQLitePutResolver() {

        @NonNull
        @Override
        public ContentValues mapToContentValues(@NonNull Repo object) {
            ContentValues contentValues = super.mapToContentValues(object);

            contentValues.put(ReposTable.Column.USER_ID, object.owner.id);

            return contentValues;
        }

    };

    @NonNull
    @Override
    public PutResult performPut(StorIOSQLite storIOSQLite, Repo object) {
        if (object.owner == null || object.owner.id == null) {
            return PutResult.newUpdateResult(0, new HashSet<>());
        }

        storIOSQLite
                .put()
                .object(object.owner)
                .prepare()
                .executeAsBlocking();

        PutResult repoResult = defaultPutResolver.performPut(storIOSQLite, object);

        Set<String> affectedTables = new HashSet<>(2);

        affectedTables.add(UserTable.NAME);
        affectedTables.add(ReposTable.NAME);

        if (repoResult.wasInserted()) {
            return PutResult.newInsertResult(repoResult.insertedId(), affectedTables);
        } else {
            return PutResult.newUpdateResult(1, affectedTables);
        }
    }

}
