package io.reist.sandbox.user.model;

import com.fernandocejas.frodo.annotation.RxLogObservable;

import java.util.List;

import io.reist.sandbox.app.model.Repo;
import io.reist.sandbox.app.model.Response;
import io.reist.sandbox.app.model.User;
import io.reist.sandbox.user.model.local.LocalUserReposService;
import io.reist.sandbox.user.model.remote.RemoteUserReposService;
import rx.Observable;

public class UserReposModelService implements UserReposService
{

    private static final String TAG = UserReposModelService.class.getSimpleName();

    protected final LocalUserReposService local;
    protected final RemoteUserReposService remote;

    public UserReposModelService(LocalUserReposService local, RemoteUserReposService remote) {
        this.local = local;
        this.remote = remote;
    }

    @RxLogObservable
    protected Observable<Response<List<Repo>>> remoteFindReposByUserIdWithSave(User user) {
        return remote
                .findReposByUser(user)
                .filter(response -> response.getData() != null)
                .doOnNext(response -> local.saveSync(user, response.getData()));
    }

    @RxLogObservable
    @Override
    public Observable<Response<List<Repo>>> findReposByUser(final User user) {
//        return Observable.merge(
//                local.findReposByUser(user),
//                remoteFindReposByUserIdWithSave(user).onErrorResumeNext((t) -> {
//                    Log.wtf(TAG, "findReposByUser", t);
//
//                    Response<List<Repo>> responseWithError = new Response<>();
//                    responseWithError.setError(new Response.Error("network error occured"));
//                    return Observable.just(responseWithError);
//                }))
        return local
                .findReposByUser(user)
                .filter(response -> response.getData() != null && !response.getData().isEmpty() || !response.isSuccessful());
    }

    @Override
    public void like(Repo repo) {
        local.like(repo);
    }

    @Override
    public void unlike(Repo repo) {
        local.unlike(repo);
    }
}
