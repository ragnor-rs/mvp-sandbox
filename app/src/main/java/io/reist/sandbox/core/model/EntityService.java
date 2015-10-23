package io.reist.sandbox.core.model;

import java.util.List;

/**
 * Created by Reist on 10/17/15.
 */
public interface EntityService<R> {

    Observable<Integer> storeList(List<R> data);

    Observable<Boolean> store(R data);

}
