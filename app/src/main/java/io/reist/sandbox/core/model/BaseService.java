package io.reist.sandbox.core.model;

import java.util.List;

import io.reist.sandbox.app.model.ResponseModel;
import rx.Observable;

/**
 * Created by Reist on 12/2/15.
 */
public interface BaseService<T> {

    Observable<ResponseModel<List<T>>> list();

    Observable<ResponseModel<T>> byId(Long id); //cur this is not true. Api will not wrap simple model into a wrapper

    Observable<Integer> save(List<T> list);

    Observable<Boolean> save(T t);

    Observable<Integer> delete(Long id);

    int saveSync(List<T> list);

    boolean saveSync(T t);

}
