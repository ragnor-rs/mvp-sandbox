package io.reist.sandbox.repolist.model.remote;

import com.fernandocejas.frodo.annotation.RxLogObservable;

import java.util.List;

import io.reist.sandbox.app.model.Repo;
import io.reist.sandbox.app.model.Response;
import io.reist.sandbox.app.model.remote.GitHubApi;
import io.reist.sandbox.core.model.remote.RetrofitService;
import io.reist.sandbox.repolist.model.RepoService;
import rx.Observable;

public class RetrofitRepoService extends RetrofitService<Repo> implements RepoService {

    public RetrofitRepoService(GitHubApi gitHubApi) {
        super(gitHubApi);
    }

    @RxLogObservable
    @Override
    public Observable<Response<List<Repo>>> list() {
        return gitHubApi.listRepos();
    }

    @RxLogObservable
    @Override
    public Observable<Response<Repo>> byId(Long id) {
        return gitHubApi.repoById(id);
    }

    @RxLogObservable
    @Override
    public Observable<Integer> save(List<Repo> list) { //cur this is not what we really get form api
        throw new UnsupportedOperationException();
    }

    @RxLogObservable
    @Override
    public Observable<Boolean> save(Repo repo) {
        return gitHubApi.save(repo);
    }

    @Override
    public Observable<Integer> delete(Long id) {
        return gitHubApi.deleteRepo(id);
    }

    @Override
    public int saveSync(List<Repo> list) {
        throw new UnsupportedOperationException("you cannot save make api calls synchronously");
    }

    @Override
    public boolean saveSync(Repo repo) {
        throw new UnsupportedOperationException("you cannot save make api calls synchronously");
    }

    @Override
    public Observable<Response<Repo>> unlike(Repo repo) {
        return gitHubApi.unlike(repo.id);
    }

    @Override
    public Observable<Response<Repo>> like(Repo repo) {
        return gitHubApi.like(repo.id);
    }

    @Override
    public Observable<Response<List<Repo>>> findReposByUserId(Long userId) {
        return gitHubApi.reposByUserId(userId);
    }

}
