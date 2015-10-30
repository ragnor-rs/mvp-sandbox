package io.reist.sandbox.repos.mvp.model.remote.retrofit;

import java.util.List;

import io.reist.sandbox.repos.mvp.model.Repo;
import retrofit.Call;
import retrofit.http.GET;

/**
 * Created by Reist on 10/14/15.
 */
public interface GitHubApi {

    @GET("/repos")
    Call<List<Repo>> listRepos();

}
