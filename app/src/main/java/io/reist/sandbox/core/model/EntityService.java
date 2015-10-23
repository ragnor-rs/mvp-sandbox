package io.reist.sandbox.core.model;

import java.util.List;

/**
 * Created by Reist on 10/17/15.
 */
public interface EntityService<R> {

    AsyncRequest<Integer> storeList(List<R> data);

    AsyncRequest<Boolean> store(R data);

}
