package io.reist.sandbox.core.mvp.model;

import java.util.List;

import io.reist.sandbox.core.rx.Observable;

/**
 * Created by Reist on 11/2/15.
 */
public interface BaseService<T> {

    Observable<List<T>> list();

    Observable<T> byId(Long id);

    int save(List<T> list);

    boolean save(T t);

}
