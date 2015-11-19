package io.reist.sandbox.app.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by defuera on 09/11/2015.
 */
public class Response<T> {

    @SerializedName("data")
    private T data;

    private Error error;

    public Response(T t) {
        data = t;
    }

    public Response() {
    }

    public void setError(Error error) {
        this.error = error;
    }

    public boolean isSuccessful() {
        return error == null;
    }

    public Error getError() {
        return error;
    }

    public T getData() {
        return data;
    }

    public static class Error {
        private final String message;
        private final Throwable throwable;

        public Error(String message) {
            this.message = message;
            this.throwable = null;
        }

        public Error(Throwable throwable) {
            this.throwable = throwable;
            this.message = null;
        }
    }
}

