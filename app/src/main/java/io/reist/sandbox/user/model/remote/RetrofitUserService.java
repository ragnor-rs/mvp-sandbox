package io.reist.sandbox.user.model.remote;

import java.util.List;

import io.reist.sandbox.app.model.Response;
import io.reist.sandbox.app.model.User;
import io.reist.sandbox.app.model.remote.GitHubApi;
import rx.Observable;

/**
 * Created by m039 on 11/12/15.
 */
public class RetrofitUserService implements RemoteUserService
{
    protected GitHubApi gitHubApi;

    public RetrofitUserService(GitHubApi gitHubApi) {
        this.gitHubApi = gitHubApi;
    }

    @Override
    public Observable<Response<List<User>>> list() {
        return gitHubApi.listUsers();
    }
}
