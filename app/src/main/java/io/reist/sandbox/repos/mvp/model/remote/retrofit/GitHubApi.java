package io.reist.sandbox.repos.mvp.model.remote.retrofit;

import java.util.List;

import io.reist.sandbox.repos.mvp.model.Repo;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Part;
import rx.Observable;

/**
 * Created by Reist on 10/14/15.
 */
public interface GitHubApi {

    @GET("/repos")
    Observable<List<Repo>> listRepos();

    @GET("/repos/{id}")
    Observable<Repo> repoById(@Part("id") Long id);

    @POST("/repos/edit")
    boolean save(Repo repo);

}
