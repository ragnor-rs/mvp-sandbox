package io.reist.sandbox.repolist.model;

import com.fernandocejas.frodo.annotation.RxLogObservable;

import java.util.List;

import io.reist.sandbox.app.model.Repo;
import io.reist.sandbox.app.model.Response;
import io.reist.sandbox.app.model.User;
import io.reist.sandbox.core.model.CachedService;
import rx.Observable;

public class CachedRepoService extends CachedService<Repo> implements RepoService {

    protected final RepoService local;
    protected final RepoService remote;

    public CachedRepoService(RepoService local, RepoService remote) {
        super(local, remote);

        this.local = local;
        this.remote = remote;
    }

    @RxLogObservable
    protected Observable<Response<List<Repo>>> remoteFindReposByUserIdWithSave(User user) {
        return remote
                .findReposByUser(user)
                .filter(r -> r.getData() != null)
                .doOnNext(r -> local.saveSync(r.getData()))
                .filter(r -> !r.isSuccessful());
    }

    @RxLogObservable
    @Override
    public Observable<Response<List<Repo>>> findReposByUser(final User user) {
        return Observable.merge(
                local.findReposByUser(user),
                remoteFindReposByUserIdWithSave(user).onErrorResumeNext((t) -> {
                    Response<List<Repo>> responseWithError = new Response<>();
                    responseWithError.setError(new Response.Error("network error occured"));
                    return Observable.just(responseWithError);
                }))
                .filter(response -> response.getData() != null && !response.getData().isEmpty() || !response.isSuccessful());
    }

    @RxLogObservable
    @Override
    public Observable<Response<Repo>> like(Repo repo) {
        return like(repo, true);
    }

    @RxLogObservable
    @Override
    public Observable<Response<Repo>> unlike(Repo repo) {
        return like(repo, false);
    }

    @RxLogObservable
    private Observable<Response<Repo>> like(Repo repo, boolean like) {
        return Observable.merge(
                (like? local.like(repo) : local.unlike(repo)),
                (like? remote.like(repo) : remote.unlike(repo))
                        .doOnNext(r -> { if (r.getData() != null) local.saveSync(r.getData()); })
                        .filter(r -> !r.isSuccessful())
                        .onErrorResumeNext((t) -> {
                            Response<Repo> responseWithError = new Response<>();
                            responseWithError.setError(new Response.Error("network error occured"));
                            return Observable.just(responseWithError);
                        })
        );
    }

}
