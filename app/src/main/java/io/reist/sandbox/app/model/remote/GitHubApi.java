package io.reist.sandbox.app.model.remote;

import java.util.List;

import io.reist.sandbox.app.model.Repo;
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
    Observable<GitHubResponse<List<Repo>>> listRepos();

    @GET("/repos/{id}")
    Observable<GitHubResponse<Repo>> repoById(@Path("id") Long id);

    @POST("/repos")
    Observable<GitHubResponse<Repo>> save(@Body Repo repo);

    @DELETE("/repos/{id}")
    Observable<GitHubResponse<Integer>> deleteRepo(@Path("id") Long id);

    @POST("/repos/{id}/like")
    Observable<GitHubResponse<Repo>> like(@Path("id") Long repoId);

    @POST("/repos/{id}/unlike")
    Observable<GitHubResponse<Repo>> unlike(@Path("id") Long repoId);

    @GET("/users")
    Observable<GitHubResponse<List<User>>> listUsers();

    @GET("/users/{id}/repos")
    Observable<GitHubResponse<List<Repo>>> reposByUserId(@Path("id") Long userId);

}
