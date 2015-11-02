package io.reist.sandbox.core.mvp.model.remote.retrofit;

import com.pushtorefresh.storio.sqlite.operations.get.PreparedGetListOfObjects;

import java.io.IOException;
import java.util.List;

import io.reist.sandbox.core.mvp.model.local.storio.StorIoListOnSubscribe;
import io.reist.sandbox.core.rx.Func0;
import io.reist.sandbox.core.rx.Observable;
import io.reist.sandbox.core.rx.impl.AbstractOnSubscribe;
import retrofit.Call;

/**
 * Created by Reist on 10/23/15.
 */
public class RetrofitOnSubscribe<T> extends AbstractOnSubscribe<T> {

    private final Call<T> readCall;

    public RetrofitOnSubscribe(Call<T> readCall) {
        this.readCall = readCall;
    }

    @Override
    protected final void emit() throws Exception {
        doOnNext(readCall.execute().body());
    }

}
