/*
 * Copyright (c) 2015  Zvooq LTD.
 * Authors: Renat Sarymsakov, Dmitriy Mozgin, Denis Volyntsev.
 *
 * This file is part of MVP-Sandbox.
 *
 * MVP-Sandbox is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MVP-Sandbox is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with MVP-Sandbox.  If not, see <http://www.gnu.org/licenses/>.
 */

package io.reist.sandbox.repos.model.local;

import com.fernandocejas.frodo.annotation.RxLogObservable;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.queries.DeleteQuery;
import com.pushtorefresh.storio.sqlite.queries.Query;

import java.util.List;

import io.reist.sandbox.app.model.Repo;
import io.reist.sandbox.repos.model.RepoService;
import io.reist.sandbox.app.model.SandboxResponse;
import io.reist.visum.model.VisumResponse;
import io.reist.sandbox.app.model.local.StorIoService;
import rx.Observable;

public class StorIoRepoService extends StorIoService<Repo> implements RepoService {

    public StorIoRepoService(StorIOSQLite storIoSqLite) {
        super(storIoSqLite);
    }

    @RxLogObservable
    @Override
    public Observable<VisumResponse<List<Repo>>> list() {
        return preparedGetBuilder(Repo.class)
                .withQuery(Query.builder().table(RepoTable.NAME).build())
                .prepare()
                .createObservable()
                .map(SandboxResponse::new);
    }

    @RxLogObservable
    @Override
    public Observable<VisumResponse<Repo>> byId(Long id) {
        return unique(Repo.class, RepoTable.NAME, id)
                .map(SandboxResponse::new);
    }

    @Override
    public Observable<VisumResponse<Integer>>  delete(Long id) {
        return storIoSqLite        //cur if that's fine to make storIoSqLite protected?
                .delete()
                .byQuery(
                        DeleteQuery.builder()
                                .table(RepoTable.NAME)
                                .where(RepoTable.Column.ID + " = ?")
                                .whereArgs(id)
                                .build()
                )
                .prepare() // BTW: it will use transaction!
                .createObservable()
                .map(t -> new SandboxResponse<>(t.numberOfRowsDeleted()));
    }

    @RxLogObservable
    @Override
    public Observable<VisumResponse<List<Repo>>> findReposByUserId(Long userId) {
        return preparedGetBuilder(Repo.class)
                .withQuery(
                        Query
                                .builder()
                                .table(RepoTable.NAME)
                                .where(RepoTable.Column.USER_ID + " = ?")
                                .whereArgs(userId)
                                .orderBy(RepoTable.Column.ID)
                                .build())
                .prepare()
                .createObservable()
                .map(SandboxResponse::new);
    }

    @Override
    public Observable<VisumResponse<Repo>> unlike(Repo repo) {
        return like(repo, false);
    }

    @Override
    public Observable<VisumResponse<Repo>> like(Repo repo) {
        return like(repo, true);
    }

    private Observable<VisumResponse<Repo>> like(final Repo repo, boolean likedByMe) {

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

        return Observable.just(repo).map(SandboxResponse::new);

    }

}
