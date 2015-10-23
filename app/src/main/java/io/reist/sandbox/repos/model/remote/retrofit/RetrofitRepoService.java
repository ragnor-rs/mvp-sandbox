package io.reist.sandbox.repos.model.remote.retrofit;

import java.util.List;

import io.reist.sandbox.core.model.Observable;
import io.reist.sandbox.core.model.remote.retrofit.RetrofitService;
import io.reist.sandbox.repos.model.Repo;
import io.reist.sandbox.repos.model.RepoService;

/**
 * Created by Reist on 10/14/15.
 */
public class RetrofitRepoService extends RetrofitService<Repo> implements RepoService {

    private final GitHubApi gitHubApi;

    public RetrofitRepoService(GitHubApi gitHubApi) {
        this.gitHubApi = gitHubApi;
    }

    @Override
    public Observable<List<Repo>> reposList(final String user) {
        return toListObservable(gitHubApi.listRepos(user));
    }

    @Override
    public Observable<Integer> storeList(List<Repo> data) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Observable<Boolean> store(Repo data) {
        throw new UnsupportedOperationException();
    }

}
