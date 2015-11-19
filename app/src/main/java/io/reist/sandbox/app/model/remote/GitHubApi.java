package io.reist.sandbox.app.model.remote;

import java.util.List;

import io.reist.sandbox.app.model.Repo;
import io.reist.sandbox.app.model.Response;
import io.reist.sandbox.app.model.User;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by Reist on 10/14/15.
 */
public interface GitHubApi {

    @GET("/repos")
    Observable<Response<List<Repo>>> listRepos();

    @GET("/repos/{id}")
    Observable<Response<Repo>> repoById(@Path("id") Long id);

    @POST("/repos")
    Observable<Boolean> save(
            @Body Repo repo
    );

    @POST("/repos/{id}/like")
    Observable<Response<Repo>> like(@Path("id") Long repoId);

    @POST("/repos/{id}/unlike")
    Observable<Response<Repo>> unlike(@Path("id") Long repoId);

    @DELETE("/repos/{id}")
    Observable<Integer> delete(Long id); //cur not implemented in apiary

    @GET("/users")
    Observable<Response<List<User>>> listUsers();

    @GET("/users/{id}/repos")
    Observable<Response<List<Repo>>> reposByUserId(@Path("id") Long userId);

}
