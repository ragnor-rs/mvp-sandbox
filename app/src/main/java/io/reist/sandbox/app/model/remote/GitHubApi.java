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
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by Reist on 10/14/15.
 */
public interface GitHubApi {

    @GET("/repos")
    Observable<Response<List<Repo>>> listRepos(@Query("user_id") String userId);

    @GET("/repos/{id}")
    Observable<Response<Repo>> repoById(@Path("id") Long id);

    @POST("/repos")
    Observable<Boolean> save(
            @Body Repo repo
    );

    @DELETE("/repos/{id}")
    Observable<Integer> delete(Long id); //cur not implemented in apiary

    @GET("/users")
    Observable<Response<List<User>>> listUsers();

    @GET("/user/{id}/repos")
    Observable<Response<List<Repo>>> reposByUserId(@Path("id") String userId);
}
