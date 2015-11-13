package io.reist.sandbox.repolist.mvp.model.remote;

import java.util.List;

import io.reist.sandbox.app.mvp.model.ResponseModel;
import io.reist.sandbox.repolist.mvp.model.Repo;
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
    Observable<ResponseModel<List<Repo>>> listRepos();

    @GET("/repos/{id}")
    Observable<ResponseModel<Repo>> repoById(@Path("id") Long id);

    @POST("/repos")
    Observable<Boolean> save(
            @Body Repo repo
    );

    @DELETE("/repos/{id}")
    Observable<Integer> delete(Long id); //cur not implemented in apiary
}
