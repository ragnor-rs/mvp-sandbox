package io.reist.sandbox.user.model;

import java.util.List;

import io.reist.sandbox.app.model.Repo;
import io.reist.sandbox.app.model.Response;
import io.reist.sandbox.app.model.User;
import rx.Observable;

/**
 * Created by Reist on 11/2/15.
 */
public interface UserReposService {

    Observable<Response<List<Repo>>> findReposByUser(User user);

    void like(User user, Repo repo);

    void unlike(User user, Repo repo);

}
