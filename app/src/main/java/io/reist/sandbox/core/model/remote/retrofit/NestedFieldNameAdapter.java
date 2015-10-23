package io.reist.sandbox.core.model.remote.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Reist on 10/17/15.
 */
public class NestedFieldNameAdapter implements JsonDeserializer<Object> {

    private final Gson defaultGson;

    public NestedFieldNameAdapter() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.addDeserializationExclusionStrategy(new NestedFieldNameExclusionStrategy());
        this.defaultGson = gsonBuilder.create();
    }

    @Override
    public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Object o = defaultGson.fromJson(json, typeOfT);
        Class rootClass = (Class) ((ParameterizedType) typeOfT).getRawType();
        if (rootClass.equals(List.class)) {
            JsonArray jsonArray = json.getAsJsonArray();
            for (int i = 0; i < jsonArray.size(); i++) {
                Object entity = ((List) o).get(i);
                if (entity == null) {
                    continue;
                }
                JsonObject entityJson = jsonArray.get(i).getAsJsonObject();
                injectNestedFields(entityJson, entity);
            }
        } else if (o != null) {
            injectNestedFields(json.getAsJsonObject(), o);
        }
        return o;
    }

    private void injectNestedFields(JsonObject entityJson, Object entity) {
        Class entityClass = entity.getClass();
        Field[] fields = entityClass.getFields();
        for (Field f : fields) {

            NestedFieldName annotation = f.getAnnotation(NestedFieldName.class);
            if (annotation == null) {
                continue;
            }
            String path = annotation.value();

            JsonElement fieldElement = null;
            String[] pathSegments = path.split("\\.");
            JsonObject parentJson = entityJson;
            for (String segment : pathSegments) {
                if (fieldElement != null) {
                    parentJson = fieldElement.getAsJsonObject();
                }
                fieldElement = parentJson.get(segment);
            }

            Object v = defaultGson.fromJson(fieldElement, f.getType());

            try {
                f.set(entity, v);
            } catch (IllegalAccessException e) {
                throw new JsonParseException(e);
            }

        }
    }

}
