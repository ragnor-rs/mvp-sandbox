package io.reist.sandbox.core.model.local.storio;

import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.operations.get.PreparedGet;
import com.pushtorefresh.storio.sqlite.operations.put.PreparedPut;
import com.pushtorefresh.storio.sqlite.operations.put.PutResult;
import com.pushtorefresh.storio.sqlite.operations.put.PutResults;

import java.util.List;

import io.reist.sandbox.core.model.BackgroundService;
import io.reist.sandbox.core.model.EntityService;
import io.reist.sandbox.core.model.Func0;
import io.reist.sandbox.core.model.Observable;

/**
 * Created by Reist on 10/17/15.
 */
public abstract class StorIoService<R> extends BackgroundService implements EntityService<R> {

    private final StorIOSQLite storIoSqLite;

    public StorIoService(StorIOSQLite storIoSqLite) {
        this.storIoSqLite = storIoSqLite;
    }

    protected PreparedGet.Builder get() {
        return storIoSqLite.get();
    }

    protected PreparedPut.Builder put() {
        return storIoSqLite.put();
    }

    @Override
    public Observable<Integer> storeList(final List<R> data) {
        return createObservable(new Func0<Integer>() {

            @Override
            public Integer call() {
                PutResults<R> putResults = put()
                        .objects(data)
                        .prepare()
                        .executeAsBlocking();
                return putResults.numberOfInserts() + putResults.numberOfUpdates();
            }

        });
    }

    @Override
    public Observable<Boolean> store(final R data) {
        return createObservable(new Func0<Boolean>() {

            @Override
            public Boolean call() {
                PutResult putResult = put()
                        .object(data)
                        .prepare()
                        .executeAsBlocking();
                return putResult.wasInserted() || putResult.wasUpdated();
            }

        });
    }

}
