package io.reist.sandbox.repo.model;

import java.util.List;

import io.reist.sandbox.app.model.Repo;
import io.reist.visum.model.BaseService;
import io.reist.visum.model.Response;
import rx.Observable;

/**
 * Created by Reist on 11/2/15.
 */
public interface RepoService extends BaseService<Repo> {

    Observable<? extends Response<List<Repo>>> findReposByUserId(Long userId);

    Observable<? extends Response<Repo>> like(Repo repo);

    Observable<? extends Response<Repo>> unlike(Repo repo);

}
