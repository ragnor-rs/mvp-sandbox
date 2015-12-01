package io.reist.sandbox.app.model.remote;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import io.reist.visum.model.Error;

/**
 * Created by m039 on 11/26/15.
 */
public class GitHubError implements Error {

    @SerializedName("message")
    private String message;

    @Nullable
    @Override
    public Throwable getThrowable() {
        return null;
    }

    @Nullable
    @Override
    public String getMessage() {
        return message;
    }
}
