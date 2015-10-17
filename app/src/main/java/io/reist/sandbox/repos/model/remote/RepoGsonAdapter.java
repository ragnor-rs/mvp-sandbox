package io.reist.sandbox.repos.model.remote;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import io.reist.sandbox.repos.model.Repo;

/**
 * Created by Reist on 10/17/15.
 */
public class RepoGsonAdapter implements JsonDeserializer<Repo> {

    private final Gson defaultGson = new Gson();

    @Override
    public Repo deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Repo repo = defaultGson.fromJson(json, Repo.class);

        return repo;
    }

}
