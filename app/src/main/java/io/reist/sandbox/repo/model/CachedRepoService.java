package io.reist.sandbox.repo.model;

import com.fernandocejas.frodo.annotation.RxLogObservable;

import java.util.List;

import io.reist.sandbox.app.model.Repo;
import io.reist.visum.model.CachedService;
import io.reist.visum.model.Response;
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
    @Override
    public Observable<Response<List<Repo>>> findReposByUserId(final Long userId) {
        return Observable.merge(
                local.findReposByUserId(userId),
                remote.findReposByUserId(userId).compose(new SaveAndEmitErrorsListTransformer<>(local)))
                .filter(new FilterListResponse<>());
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
                (like ? local.like(repo) : local.unlike(repo)),
                (like ? remote.like(repo) : remote.unlike(repo))
                        .compose(new SaveAndEmitErrorsTransformer<>(local)))
                .filter(new FilterResponse<>());
    }

}
