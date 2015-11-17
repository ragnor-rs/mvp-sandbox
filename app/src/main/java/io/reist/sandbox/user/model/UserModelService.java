package io.reist.sandbox.user.model;

import com.fernandocejas.frodo.annotation.RxLogObservable;

import java.util.List;

import io.reist.sandbox.app.model.Response;
import io.reist.sandbox.app.model.User;
import io.reist.sandbox.user.model.local.LocalUserService;
import io.reist.sandbox.user.model.remote.RemoteUserService;
import rx.Observable;

/**
 * Created by m039 on 11/12/15.
 */
public class UserModelService implements UserService
{
    LocalUserService local;
    RemoteUserService remote;

    public UserModelService(LocalUserService local, RemoteUserService remote) {
        this.local = local;
        this.remote = remote;
    }

    @RxLogObservable
    protected Observable<Response<List<User>>> remoteListWithSave() {
        return remote
                .list()
                .doOnNext(response -> local.saveSync(response.getData()));
    }

    @RxLogObservable
    @Override
    public final Observable<Response<List<User>>> list() {
        return Observable.merge(
                local.list(),
                remoteListWithSave().onErrorResumeNext((t) -> {
                    Response<List<User>> responseWithError = new Response<>();
                    responseWithError.setError(new Response.Error("network error occured"));
                    return Observable.just(responseWithError);
                }))
                .filter(response -> response.getData() != null && !response.getData().isEmpty() || !response.isSuccessful());

    }

}
