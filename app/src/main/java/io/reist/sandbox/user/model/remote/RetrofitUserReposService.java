package io.reist.sandbox.user.model.remote;

import java.util.List;

import io.reist.sandbox.app.Const;
import io.reist.sandbox.app.model.Repo;
import io.reist.sandbox.app.model.Response;
import io.reist.sandbox.app.model.User;
import io.reist.sandbox.app.model.remote.GitHubApi;
import rx.Observable;

public class RetrofitUserReposService implements RemoteUserReposService
{
    protected final GitHubApi gitHubApi;

    public RetrofitUserReposService(GitHubApi gitHubApi) {
        this.gitHubApi = gitHubApi;
    }

    @Override
    public Observable<Response<List<Repo>>> findReposByUser(User user) {
        return gitHubApi.reposByUserId(user.id, Const.DEFAULT_USER_ID);
    }

    @Override
    public void unlike(User user, Repo repo) {
        throw new RuntimeException("Unsupported function");
    }

    @Override
    public void like(User user, Repo repo) {
        throw new RuntimeException("Unsupported function");
    }
}
