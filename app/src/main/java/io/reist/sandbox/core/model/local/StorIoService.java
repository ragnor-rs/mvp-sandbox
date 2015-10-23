package io.reist.sandbox.core.model.local;

import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.operations.get.PreparedGet;
import com.pushtorefresh.storio.sqlite.operations.put.PreparedPut;
import com.pushtorefresh.storio.sqlite.operations.put.PutResult;
import com.pushtorefresh.storio.sqlite.operations.put.PutResults;

import java.util.List;

import io.reist.sandbox.core.model.AsyncRequest;
import io.reist.sandbox.core.model.BackgroundOp;
import io.reist.sandbox.core.model.BackgroundService;
import io.reist.sandbox.core.model.EntityService;

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
    public AsyncRequest<Integer> storeList(final List<R> data) {
        return createRequest(new BackgroundOp<Integer>() {

            @Override
            public Integer execute() {
                PutResults<R> putResults = put()
                        .objects(data)
                        .prepare()
                        .executeAsBlocking();
                return putResults.numberOfInserts() + putResults.numberOfUpdates();
            }

        });
    }

    @Override
    public AsyncRequest<Boolean> store(final R data) {
        return createRequest(new BackgroundOp<Boolean>() {

            @Override
            public Boolean execute() {
                PutResult putResult = put()
                        .object(data)
                        .prepare()
                        .executeAsBlocking();
                return putResult.wasInserted() || putResult.wasUpdated();
            }

        });
    }

}
