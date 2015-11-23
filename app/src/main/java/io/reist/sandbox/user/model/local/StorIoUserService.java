package io.reist.sandbox.user.model.local;

import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.queries.Query;

import java.util.List;

import io.reist.visum.model.Response;
import io.reist.sandbox.app.model.User;
import io.reist.sandbox.app.model.local.UserTable;
import io.reist.visum.model.local.StorIoService;
import io.reist.sandbox.user.model.UserService;
import rx.Observable;

/**
 * Created by m039 on 11/12/15.
 */
public class StorIoUserService extends StorIoService<User>
    implements UserService
{

    public StorIoUserService(StorIOSQLite storIoSqLite) {
        super(storIoSqLite);
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
    public Observable<Response<List<User>>> list() {
        return preparedGetBuilder(User.class)
                .withQuery(Query
                        .builder()
                        .table(UserTable.NAME)
                        .orderBy(UserTable.Column.ID)
                        .build())
                .prepare()
                .createObservable()
                .map(Response::new);
    }
}
