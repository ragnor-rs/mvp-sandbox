package io.reist.sandbox.repos.model.remote;

import java.util.List;

import io.reist.sandbox.repos.model.Repo;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Reist on 10/14/15.
 */
public interface GitHubApi {

    @GET("/users/{user}/repos")
    Call<List<Repo>> listRepos(@Path("user") String user);

}
