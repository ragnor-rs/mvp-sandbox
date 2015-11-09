package io.reist.sandbox.core.mvp.model;

import com.fernandocejas.frodo.annotation.RxLogObservable;

import java.util.List;

import rx.Observable;

/**
 * Created by Reist on 12/2/15.
 */
public abstract class AbstractBaseService<T> implements BaseService<T> {

    public abstract Observable<List<T>> list();

    public abstract Observable<T> byId(Long id);

    @RxLogObservable
    public Observable<Integer> save(List<T> list) {
        return Observable.create(subscriber -> saveSync(list));
    }

    @RxLogObservable
    public Observable<Boolean> save(T t) {
        return Observable.create(subscriber -> saveSync(t));
    }

    public abstract int saveSync(List<T> list);

    public abstract boolean saveSync(T t);

}
