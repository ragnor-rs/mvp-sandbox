package io.reist.sandbox.core.mvp.model.local.storio;

import com.pushtorefresh.storio.sqlite.operations.get.PreparedGetListOfObjects;

import java.util.List;

import io.reist.sandbox.core.rx.impl.AbstractOnSubscribe;

/**
 * Created by Reist on 10/23/15.
 */
public class StorIoListOnSubscribe<I> extends AbstractOnSubscribe<List<I>> {

    private final PreparedGetListOfObjects<I> preparedGetListOfObjects;

    public StorIoListOnSubscribe(PreparedGetListOfObjects<I> preparedGetListOfObjects) {
        this.preparedGetListOfObjects = preparedGetListOfObjects;
    }

    @Override
    protected final void emit() throws Exception {
        doOnNext(preparedGetListOfObjects.executeAsBlocking());
    }

}
