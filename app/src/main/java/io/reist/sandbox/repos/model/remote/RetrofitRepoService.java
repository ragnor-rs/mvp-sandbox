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

package io.reist.sandbox.repos.model.remote;

import com.fernandocejas.frodo.annotation.RxLogObservable;

import java.util.List;

import io.reist.sandbox.app.model.Repo;
import io.reist.sandbox.app.model.remote.GitHubApi;
import io.reist.sandbox.repos.model.RepoService;
import io.reist.visum.model.Response;
import io.reist.visum.model.remote.RetrofitService;
import rx.Observable;

public class RetrofitRepoService extends RetrofitService<Repo> implements RepoService {

    protected final GitHubApi gitHubApi;

    public RetrofitRepoService(GitHubApi gitHubApi) {
        this.gitHubApi = gitHubApi;
    }

    @RxLogObservable
    @Override
    public Observable<? extends Response<List<Repo>>> list() {
        return gitHubApi.listRepos();
    }

    @RxLogObservable
    @Override
    public Observable<? extends Response<Repo>> byId(Long id) {
        return gitHubApi.repoById(id);
    }

    @RxLogObservable
    @Override
    public Observable<? extends Response<List<Repo>>> save(List<Repo> list) { //cur this is not what we really get form api
        throw new UnsupportedOperationException();
    }

    @RxLogObservable
    @Override
    public Observable<? extends Response<Repo>> save(Repo repo) {
        return gitHubApi.save(repo);
    }

    @Override
    public Observable<? extends Response<Integer>> delete(Long id) {
        return gitHubApi.deleteRepo(id);
    }

    @Override
    public Response<List<Repo>> saveSync(List<Repo> list) {
        throw new UnsupportedOperationException("you cannot save make api calls synchronously");
    }

    @Override
    public Response<Repo> saveSync(Repo repo) {
        throw new UnsupportedOperationException("you cannot save make api calls synchronously");
    }

    @Override
    public Observable<? extends Response<Repo>> unlike(Repo repo) {
        return gitHubApi.unlike(repo.id);
    }

    @Override
    public Observable<? extends Response<Repo>> like(Repo repo) {
        return gitHubApi.like(repo.id);
    }

    @Override
    public Observable<? extends Response<List<Repo>>> findReposByUserId(Long userId) {
        return gitHubApi.reposByUserId(userId);
    }

}
