package io.reist.sandbox.core.mvp.model.local.storio;

import android.support.annotation.NonNull;

import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.operations.get.PreparedGetListOfObjects;
import com.pushtorefresh.storio.sqlite.operations.put.PreparedPut;
import com.pushtorefresh.storio.sqlite.operations.put.PutResult;
import com.pushtorefresh.storio.sqlite.operations.put.PutResults;
import com.pushtorefresh.storio.sqlite.queries.Query;

import java.util.List;

import io.reist.sandbox.core.mvp.model.BaseService;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Reist on 10/23/15.
 */
public abstract class StorIoService<T> implements BaseService<T> {

    private final StorIOSQLite storIoSqLite;

    public StorIoService(StorIOSQLite storIoSqLite) {
        this.storIoSqLite = storIoSqLite;
    }

    @NonNull
    protected PreparedGetListOfObjects.Builder<T> preparedGetBuilder(Class<T> entityClass) {
        return storIoSqLite.get().listOfObjects(entityClass);
    }

    @NonNull
    protected PreparedPut.Builder preparedPutBuilder() {
        return storIoSqLite.put();
    }

    @NonNull
    protected final Observable<T> unique(Class<T> entityClass, String tableName, Long id) {
        return preparedGetBuilder(entityClass)
                .withQuery(
                        Query.builder()
                                .table(tableName)
                                .where("id = ?")
                                .whereArgs(id)
                                .build()
                )
                .prepare()
                .createObservable()
                .map(new Func1<List<T>, T>() {

                    @Override
                    public T call(List<T> list) {
                        return list == null || list.isEmpty() ?
                                null :
                                list.get(0);
                    }

                });
    }

    @Override
    public final int save(List<T> list) {

        final PutResults<T> putResults = preparedPutBuilder()
                .objects(list)
                .prepare()
                .executeAsBlocking();

        return putResults.numberOfUpdates() + putResults.numberOfInserts();

    }

    @Override
    public final boolean save(T t) {

        final PutResult putResult = preparedPutBuilder()
                .object(t)
                .prepare()
                .executeAsBlocking();

        return putResult.wasInserted() || putResult.wasUpdated();

    }

}