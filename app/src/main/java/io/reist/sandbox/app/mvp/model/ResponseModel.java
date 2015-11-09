package io.reist.sandbox.app.mvp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by defuera on 09/11/2015.
 */
public class ResponseModel<T> {

    @SerializedName(ResponseJson.FIELD_DATA)
    public T data;

    public ResponseModel(T t) {
        data = t;
    }

    public ResponseModel() {
    }

}

