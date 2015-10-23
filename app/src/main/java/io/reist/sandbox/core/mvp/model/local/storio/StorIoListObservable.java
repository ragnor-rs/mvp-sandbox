package io.reist.sandbox.core.mvp.model.local.storio;

import android.support.annotation.NonNull;

import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.operations.get.PreparedGet;
import com.pushtorefresh.storio.sqlite.operations.put.PreparedPut;
import com.pushtorefresh.storio.sqlite.operations.put.PutResults;

import java.util.List;

import io.reist.sandbox.core.mvp.model.ListObservable;

/**
 * Created by Reist on 10/23/15.
 */
public abstract class StorIoListObservable<I> extends ListObservable<I> {

    private final StorIOSQLite storIoSqLite;

    protected StorIoListObservable(StorIOSQLite storIoSqLite) {
        this.storIoSqLite = storIoSqLite;
    }

    @Override
    protected Integer put(List<I> list) {
        PutResults<I> putResults =  preparedPutBuilder()
                .objects(list)
                .prepare()
                .executeAsBlocking();
        return putResults.numberOfInserts() + putResults.numberOfUpdates();
    }

    @NonNull
    protected PreparedPut.Builder preparedPutBuilder() {
        return storIoSqLite.put();
    }

    @NonNull
    protected PreparedGet.Builder preparedGetBuilder() {
        return storIoSqLite.get();
    }

}
