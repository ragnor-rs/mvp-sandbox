package io.reist.sandbox.repolist.model;

import java.util.List;

import io.reist.sandbox.app.model.Repo;
import io.reist.sandbox.app.model.Response;
import io.reist.sandbox.app.model.User;
import io.reist.sandbox.core.model.BaseService;
import rx.Observable;

/**
 * Created by Reist on 11/2/15.
 */
public interface RepoService extends BaseService<Repo> {

    Observable<Response<List<Repo>>> findReposByUser(User user);

    Observable<Response<Repo>> like(Repo repo);

    Observable<Response<Repo>> unlike(Repo repo);

}
