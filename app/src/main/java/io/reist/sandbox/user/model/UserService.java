package io.reist.sandbox.user.model;

import java.util.List;

import io.reist.sandbox.app.model.Response;
import io.reist.sandbox.app.model.User;
import rx.Observable;

/**
 * Created by m039 on 11/12/15.
 */
public interface UserService {

    Observable<Response<List<User>>> list();

}
