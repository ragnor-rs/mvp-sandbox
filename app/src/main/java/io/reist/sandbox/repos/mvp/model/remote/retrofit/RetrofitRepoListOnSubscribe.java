package io.reist.sandbox.repos.mvp.model.remote.retrofit;

import java.util.List;

import io.reist.sandbox.core.mvp.model.remote.retrofit.RetrofitListOnSubscribe;
import io.reist.sandbox.repos.mvp.model.Repo;
import retrofit.Call;

/**
 * Created by Reist on 10/23/15.
 */
public class RetrofitRepoListOnSubscribe extends RetrofitListOnSubscribe<Repo> {

    private final GitHubApi gitHubApi;

    public RetrofitRepoListOnSubscribe(GitHubApi gitHubApi) {
        this.gitHubApi = gitHubApi;
    }

    @Override
    protected Call<List<Repo>> getReadCall() {
        return gitHubApi.listRepos("JakeWharton");
    }

}
