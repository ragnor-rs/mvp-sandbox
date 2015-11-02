package io.reist.sandbox.repos.mvp.model.remote.retrofit;

import java.io.IOException;
import java.util.List;

import io.reist.sandbox.core.mvp.model.remote.retrofit.RetrofitOnSubscribe;
import io.reist.sandbox.core.mvp.model.remote.retrofit.RetrofitService;
import io.reist.sandbox.core.rx.Observable;
import io.reist.sandbox.repos.mvp.model.Repo;
import io.reist.sandbox.repos.mvp.model.RepoService;

public class RetrofitRepoService extends RetrofitService<Repo> implements RepoService {

    private final GitHubApi gitHubApi;

    public RetrofitRepoService(GitHubApi gitHubApi) {
        this.gitHubApi = gitHubApi;
    }

    @Override
    public Observable<List<Repo>> list() {
        return Observable.create(new RetrofitOnSubscribe<>(gitHubApi.listRepos()));
    }

    @Override
    public Observable<Repo> byId(Long id) {
        return Observable.create(new RetrofitOnSubscribe<>(gitHubApi.repoById(id)));
    }

    @Override
    public int save(List<Repo> list) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean save(Repo repo) {
        try {
            return gitHubApi.save(repo).execute().body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
