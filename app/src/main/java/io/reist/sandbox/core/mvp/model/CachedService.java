package io.reist.sandbox.core.mvp.model;

import java.util.List;

import io.reist.sandbox.core.rx.Action1;
import io.reist.sandbox.core.rx.Func1;
import io.reist.sandbox.core.rx.Observable;

/**
 * Created by Reist on 11/2/15.
 */
public abstract class CachedService<T> implements BaseService<T> {

    private final BaseService<T> local;
    private final BaseService<T> remote;

    public CachedService(BaseService<T> local, BaseService<T> remote) {
        this.local = local;
        this.remote = remote;
    }

    protected Observable<List<T>> remoteListWithSave() {
        return remote
                .list()
                .doOnNext(new Action1<List<T>>() {

                    @Override
                    public void call(List<T> list) {
                        local.save(list);
                    }

                });
    }

    protected Observable<T> remoteByIdWithSave(Long id) {
        return remote
                .byId(id)
                .doOnNext(new Action1<T>() {

                    @Override
                    public void call(T t) {
                        local.save(t);
                    }

                });
    }

    @Override
    public final Observable<List<T>> list() {
        return Observable.concat(local.list().first(), remoteListWithSave().first())
                .first(new Func1<List<T>, Boolean>() {

                    @Override
                    public Boolean call(List<T> list) {
                        return !list.isEmpty();
                    }

                });
    }

    @Override
    public final Observable<T> byId(Long id) {
        return Observable.concat(local.byId(id).first(), remoteByIdWithSave(id).first())
                .first(new Func1<T, Boolean>() {

                    @Override
                    public Boolean call(T t) {
                        return t != null;
                    }

                });
    }

    @Override
    public final int save(List<T> list) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final boolean save(T t) {
        throw new UnsupportedOperationException();
    }

}
