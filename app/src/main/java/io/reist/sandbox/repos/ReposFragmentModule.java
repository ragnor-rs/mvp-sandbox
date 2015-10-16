package io.reist.sandbox.repos;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reist.sandbox.repos.model.RepoService;
import io.reist.sandbox.repos.presenter.RepoListPresenter;

/**
 * Created by Reist on 10/15/15.
 */
@Module
public class ReposFragmentModule {

    private final Context context;

    public ReposFragmentModule(Context context) {
        this.context = context;
    }

    @Provides
    RecyclerView.LayoutManager layoutManager() {
        return new LinearLayoutManager(context);
    }

    @Provides @Singleton
    RepoListPresenter repoListPresenter(RepoService repoService) {
        return new RepoListPresenter(repoService);
    }

}
