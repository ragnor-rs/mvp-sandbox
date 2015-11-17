package io.reist.sandbox.user.model.local;

import java.util.List;

import io.reist.sandbox.app.model.Repo;
import io.reist.sandbox.app.model.User;
import io.reist.sandbox.user.model.UserReposService;

/**
 * Created by m039 on 11/16/15.
 */
public interface LocalUserReposService extends UserReposService {

    boolean saveSync(User user, List<Repo> repos);

}
