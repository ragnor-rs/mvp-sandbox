package io.reist.sandbox.repos.model;

import java.util.List;

import io.reist.sandbox.core.model.AsyncRequest;
import io.reist.sandbox.core.model.AsyncResponse;

/**
 * Created by Reist on 10/17/15.
 */
public class CombinedRepoService implements RepoService {

    public final RepoService databaseRepoService;
    public final RepoService networkRepoService;

    public CombinedRepoService(RepoService databaseRepoService, RepoService networkRepoService) {
        this.databaseRepoService = databaseRepoService;
        this.networkRepoService = networkRepoService;
    }

    @Override
    public AsyncRequest<List<Repo>> listRepos(final String user) {
        return new AsyncRequest<List<Repo>>() {

            @Override
            public void enqueue(final AsyncResponse<List<Repo>> response) {
                enqueueDatabaseRequest(response, databaseRepoService.listRepos(user), user);
            }

        };
    }

    private void enqueueDatabaseRequest(final AsyncResponse<List<Repo>> response, AsyncRequest<List<Repo>> request, final String user) {
        request.enqueue(new AsyncResponse<List<Repo>>() {

            @Override
            public void onSuccess(List<Repo> result) {
                if (result == null || result.isEmpty()) {
                    enqueueNetworkRequest(response, networkRepoService.listRepos(user));
                } else {
                    response.onSuccess(result);
                }
            }

            @Override
            public void onError(Throwable error) {
                response.onError(error);
            }

        });
    }

    private void enqueueNetworkRequest(final AsyncResponse<List<Repo>> response, AsyncRequest<List<Repo>> request) {
        request.enqueue(new AsyncResponse<List<Repo>>() {

            @Override
            public void onSuccess(List<Repo> result) {
                response.onSuccess(result);
            }

            @Override
            public void onError(Throwable error) {
                response.onError(error);
            }

        });
    }

 }
