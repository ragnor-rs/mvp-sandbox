package io.reist.sandbox.app.model.remote;

import android.support.annotation.Nullable;

import io.reist.visum.model.Error;

/**
 * Created by m039 on 11/26/15.
 */
public class GitHubError implements Error {

    private Throwable throwable;
    private String message;

    public GitHubError() {
    }

    public GitHubError(Error error) {
        this.throwable = error.getThrowable();
        this.message = error.getMessage();
    }

    @Override
    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    @Nullable
    @Override
    public Throwable getThrowable() {
        return throwable;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    @Nullable
    @Override
    public String getMessage() {
        return message;
    }
}
