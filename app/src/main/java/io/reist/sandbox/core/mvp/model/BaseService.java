package io.reist.sandbox.core.mvp.model;

import java.util.List;

import rx.Observable;

/**
 * Created by Reist on 11/2/15.
 */
public interface BaseService<T> {

    Observable<List<T>> list();

    Observable<T> byId(Long id);

    Observable<Integer> save(List<T> list);

    Observable<Boolean> save(T t);

}
