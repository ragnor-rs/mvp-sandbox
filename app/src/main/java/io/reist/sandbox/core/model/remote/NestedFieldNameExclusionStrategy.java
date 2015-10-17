package io.reist.sandbox.core.model.remote;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

import io.reist.sandbox.core.model.NestedFieldName;

/**
 * Created by Reist on 10/17/15.
 */
public class NestedFieldNameExclusionStrategy implements ExclusionStrategy {

    @Override
    public boolean shouldSkipField(FieldAttributes f) {
        return f.getAnnotation(NestedFieldName.class) != null;
    }

    @Override
    public boolean shouldSkipClass(Class<?> clazz) {
        return false;
    }

}
