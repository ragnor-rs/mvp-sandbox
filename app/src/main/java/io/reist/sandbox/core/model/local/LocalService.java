package io.reist.sandbox.core.model.local;

import java.util.List;

import io.reist.sandbox.core.model.AsyncRequest;

/**
 * Created by Reist on 10/17/15.
 */
public interface LocalService<R> {

    AsyncRequest<Integer> storeList(List<R> data);

    AsyncRequest<Boolean> store(R data);

}
