package io.reist.sandbox.app.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by defuera on 09/11/2015.
 */
public class ResponseModel<T> {

    @SerializedName(ResponseJson.FIELD_DATA)
    private T data;

    private Error error;

    public ResponseModel(T t) {
        data = t;
    }

    public ResponseModel() {
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

        public Error(String message) {
            this.message = message;
        }
    }
}

