package io.reist.sandbox.core.mvp.model;

import com.fernandocejas.frodo.annotation.RxLogObservable;

import java.util.List;

import rx.Observable;

/**
 * Created by Reist on 11/2/15.
 * Combines local and remote services
 */
public abstract class CachedService<T> extends AbstractBaseService<T> {

    private final BaseService<T> local;
    private final BaseService<T> remote;

    public CachedService(BaseService<T> local, BaseService<T> remote) {
        this.local = local;
        this.remote = remote;
    }

    @RxLogObservable
    protected Observable<List<T>> remoteListWithSave() {
        return remote
                .list()
                .doOnNext(local::saveSync);
    }

    @RxLogObservable
    protected Observable<T> remoteByIdWithSave(Long id) {
        return remote
                .byId(id)
                .doOnNext(local::saveSync);
    }

    /**
     * @return - data from local or remote service.
     * The data is provided by the observer, which emitted first
     */
    @RxLogObservable
    @Override
    public final Observable<List<T>> list() {
        return Observable.concat(local.list().first(), remoteListWithSave().first())
                .first(list -> !list.isEmpty());
    }

    /**
     * @return - data from local or remote service.
     * The data is provided by the one emitted first
     */
    @RxLogObservable
    @Override
    public final Observable<T> byId(Long id) {
        return Observable.concat(local.byId(id).first(), remoteByIdWithSave(id).first())
                .first(t -> t != null);
    }

    /**
     * Puts data to local and then to remote services sequentially
     *
     * @param list - to save
     * @return num of updated items
     */
    @RxLogObservable
    @Override
    public final Observable<Integer> save(List<T> list) { //cur we are getting num of updated items, but what about rest response?
        return Observable.concat(local.save(list), remote.save(list));
    }

    /**
     * Puts data to local and then to remote services sequentially
     *
     * @param t - object to save
     * @return boolean - whether data saved successfully
     */
    @RxLogObservable
    @Override
    public final Observable<Boolean> save(T t) {
        return Observable.concat(local.save(t), remote.save(t));
    }

    @Override
    public int saveSync(List<T> list) {
        return local.saveSync(list);
    }

    @Override
    public boolean saveSync(T t) {
        return local.saveSync(t);// && remote.saveSync(t); //todo remote.saveSync unsupported
    }

}
