package io.reist.sandbox.app.model.remote;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import io.reist.visum.model.Error;
import io.reist.visum.model.Response;

/**
 * Created by m039 on 11/26/15.
 */
public class GitHubResponse<T> implements Response<T> {

    @SerializedName("result")
    private T result;

    @SerializedName("error")
    private GitHubError error;

    @Nullable
    @Override
    public T getResult() {
        return result;
    }

    @Nullable
    @Override
    public Error getError() {
        return error;
    }

    @Override
    public boolean isSuccessful() {
        return error == null;
    }
}
