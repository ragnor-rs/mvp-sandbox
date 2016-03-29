/*
 * Copyright (c) 2015  Zvooq LTD.
 * Authors: Renat Sarymsakov, Dmitriy Mozgin, Denis Volyntsev.
 *
 * This file is part of Visum.
 *
 * Visum is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Visum is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Visum.  If not, see <http://www.gnu.org/licenses/>.
 */

package io.reist.sandbox.app.model.local;

import android.support.annotation.NonNull;

import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.operations.get.PreparedGetListOfObjects;
import com.pushtorefresh.storio.sqlite.operations.put.PreparedPut;
import com.pushtorefresh.storio.sqlite.queries.Query;

import java.util.List;

import io.reist.sandbox.app.model.SandboxResponse;
import io.reist.visum.model.VisumResponse;
import io.reist.visum.model.VisumService;
import rx.Observable;

/**
 * Created by Reist on 10/23/15.
 */
public abstract class StorIoService<T> implements VisumService<T> {

    protected final StorIOSQLite storIoSqLite;

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
                                .where(BaseTable.Column.ID + " = ?")
                                .whereArgs(id)
                                .build()
                )
                .prepare()
                .createObservable()
                .map(list -> list == null || list.isEmpty() ?
                        null :
                        list.get(0));
    }

    @Override
    public final VisumResponse<List<T>> saveSync(List<T> list) {

        preparedPutBuilder()
                .objects(list)
                .prepare()
                .executeAsBlocking();

        return new SandboxResponse<>(list);
    }

    @Override
    public final VisumResponse<T> saveSync(T t) {

        preparedPutBuilder()
                .object(t)
                .prepare()
                .executeAsBlocking();

        return new SandboxResponse<>(t);
    }

    @Override
    public Observable<? extends VisumResponse<List<T>>> save(List<T> list) {
        return Observable.just(saveSync(list));
    }

    @Override
    public Observable<? extends VisumResponse<T>> save(T t) {
        return Observable.just(saveSync(t));
    }

}
