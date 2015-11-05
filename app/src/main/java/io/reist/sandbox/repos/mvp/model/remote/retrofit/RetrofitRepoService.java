package io.reist.sandbox.repos.mvp.model.remote.retrofit;

import java.util.List;

import io.reist.sandbox.core.mvp.model.remote.retrofit.RetrofitService;
import io.reist.sandbox.repos.mvp.model.Repo;
import io.reist.sandbox.repos.mvp.model.RepoService;
import rx.Observable;

public class RetrofitRepoService extends RetrofitService<Repo> implements RepoService {

    private final GitHubApi gitHubApi;

    public RetrofitRepoService(GitHubApi gitHubApi) {
        this.gitHubApi = gitHubApi;
    }

    @Override
    public Observable<List<Repo>> list() {
        return gitHubApi.listRepos();
    }

    @Override
    public Observable<Repo> byId(Long id) {
        return gitHubApi.repoById(id);
    }

    @Override
    public Observable<Integer> save(List<Repo> list) { //cur this is not what we really get form api
        throw new UnsupportedOperationException();
    }

    @Override
    public Observable<Boolean> save(Repo repo) {
        return gitHubApi.save(repo);
    }

}
