package io.reist.sandbox.user.model.remote;

import java.util.List;

import io.reist.visum.model.Response;
import io.reist.sandbox.app.model.User;
import io.reist.sandbox.app.model.remote.GitHubApi;
import io.reist.visum.model.remote.RetrofitService;
import io.reist.sandbox.user.model.UserService;
import rx.Observable;

/**
 * Created by m039 on 11/12/15.
 */
public class RetrofitUserService extends RetrofitService<User> implements UserService {

    protected final GitHubApi gitHubApi;

    public RetrofitUserService(GitHubApi gitHubApi) {
        this.gitHubApi = gitHubApi;
    }

    @Override
    public Observable<Response<List<User>>> list() {
        return gitHubApi.listUsers();
    }

    @Override
    public Response<User> saveSync(User user) {
        throw new IllegalStateException("Unsupported");
    }

    @Override
    public Observable<Response<User>> byId(Long id) {
        throw new IllegalStateException("Unsupported");
    }

    @Override
    public Observable<Integer> delete(Long id) {
        throw new IllegalStateException("Unsupported");
    }

    @Override
    public Response<List<User>> saveSync(List<User> list) {
        throw new IllegalStateException("Unsupported");
    }

}
