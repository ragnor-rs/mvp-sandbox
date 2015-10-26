package io.reist.sandbox.core.mvp.model.local.storio;

import android.support.annotation.NonNull;

import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.operations.get.PreparedGet;

import java.util.List;

import io.reist.sandbox.core.rx.Observable;

/**
 * Created by Reist on 10/23/15.
 */
public abstract class StorIoListObservable<I> extends Observable<List<I>> {

    private final StorIOSQLite storIoSqLite;

    public StorIoListObservable(StorIOSQLite storIoSqLite) {
        super(null);
        this.storIoSqLite = storIoSqLite;
    }

    @NonNull
    protected PreparedGet.Builder preparedGetBuilder() {
        return storIoSqLite.get();
    }

}
