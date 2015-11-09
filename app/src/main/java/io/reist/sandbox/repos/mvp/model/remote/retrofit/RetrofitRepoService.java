package io.reist.sandbox.repos.mvp.model.remote.retrofit;

import com.fernandocejas.frodo.annotation.RxLogObservable;

import java.util.List;

import io.reist.sandbox.app.mvp.model.ResponseModel;
import io.reist.sandbox.core.mvp.model.remote.retrofit.RetrofitService;
import io.reist.sandbox.repos.mvp.model.Repo;
import io.reist.sandbox.repos.mvp.model.RepoService;
import rx.Observable;

public class RetrofitRepoService extends RetrofitService<Repo> implements RepoService {

    private final GitHubApi gitHubApi;

    public RetrofitRepoService(GitHubApi gitHubApi) {
        this.gitHubApi = gitHubApi;
    }

    @RxLogObservable
    @Override
    public Observable<ResponseModel<List<Repo>>> list() {
        return gitHubApi.listRepos();
    }

    @RxLogObservable
    @Override
    public Observable<ResponseModel<Repo>> byId(Long id) {
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
    public int saveSync(List<Repo> list) {
        throw new UnsupportedOperationException("you cannot save make api calls synchronously");
    }

    @Override
    public boolean saveSync(Repo repo) {
        throw new UnsupportedOperationException("you cannot save make api calls synchronously");
    }

}
