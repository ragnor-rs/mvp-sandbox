package io.reist.sandbox.user.model.local;

import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.operations.put.PutResults;
import com.pushtorefresh.storio.sqlite.queries.Query;

import java.util.List;

import io.reist.sandbox.app.model.Response;
import io.reist.sandbox.app.model.User;
import io.reist.sandbox.app.model.local.UserTable;
import rx.Observable;

/**
 * Created by m039 on 11/12/15.
 */
public class StorIoUserService implements LocalUserService
{

    protected StorIOSQLite storIoSqLite;

    public StorIoUserService(StorIOSQLite storIoSqLite) {
        this.storIoSqLite = storIoSqLite;
    }

    @Override
    public Observable<Response<List<User>>> list() {
        return storIoSqLite
                .get()
                .listOfObjects(User.class)
                .withQuery(Query.builder().table(UserTable.NAME).build())
                .prepare()
                .createObservable()
                .map(Response::new);
    }

    @Override
    public int saveSync(List<User> list) {
        final PutResults<User> putResults = storIoSqLite
                .put()
                .objects(list)
                .prepare()
                .executeAsBlocking();

        return putResults.numberOfUpdates() + putResults.numberOfInserts();
    }
}
