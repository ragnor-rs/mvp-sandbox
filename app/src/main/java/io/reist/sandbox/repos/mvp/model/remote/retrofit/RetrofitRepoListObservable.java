package io.reist.sandbox.repos.mvp.model.remote.retrofit;

import java.util.List;

import io.reist.sandbox.core.mvp.model.remote.retrofit.RetrofitListObservable;
import io.reist.sandbox.repos.mvp.model.Repo;
import retrofit.Call;

/**
 * Created by Reist on 10/23/15.
 */
public class RetrofitRepoListObservable extends RetrofitListObservable<Repo> {

    private final GitHubApi gitHubApi;

    public RetrofitRepoListObservable(GitHubApi gitHubApi) {
        this.gitHubApi = gitHubApi;
    }

    @Override
    protected Call<Integer> getWriteCall() {
        throw new UnsupportedOperationException();
    }

    @Override
    protected Call<List<Repo>> getReadCall() {
        return gitHubApi.listRepos("JackWharton");
    }

}
