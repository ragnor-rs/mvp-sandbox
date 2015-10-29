package io.reist.sandbox.repos.mvp.model.remote.retrofit;

import com.pushtorefresh.storio.sqlite.StorIOSQLite;

import java.util.List;

import io.reist.sandbox.core.mvp.model.remote.retrofit.RetrofitListOnSubscribe;
import io.reist.sandbox.repos.mvp.model.Repo;
import retrofit.Call;

/**
 * Created by Reist on 10/23/15.
 */
public class RetrofitRepoListOnSubscribe extends RetrofitListOnSubscribe<Repo> {

    private final GitHubApi gitHubApi;
    private final StorIOSQLite storIoSqLite;

    public RetrofitRepoListOnSubscribe(GitHubApi gitHubApi, StorIOSQLite storIoSqLite) {
        this.gitHubApi = gitHubApi;
        this.storIoSqLite = storIoSqLite;
    }

    @Override
    protected void cache(List<Repo> list) {
        storIoSqLite.put()
                .objects(list)
                .prepare()
                .executeAsBlocking();
    }

    @Override
    protected Call<List<Repo>> getReadCall() {
        return gitHubApi.listRepos("JakeWharton");
    }

}
