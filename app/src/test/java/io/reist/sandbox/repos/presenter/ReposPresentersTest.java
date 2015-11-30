package io.reist.sandbox.repos.presenter;

import android.app.Instrumentation;
import android.os.Bundle;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Component;
import io.reist.sandbox.BuildConfig;
import io.reist.sandbox.R;
import io.reist.sandbox.app.DaggerSandboxComponent;
import io.reist.sandbox.app.SandboxApplication;
import io.reist.sandbox.app.SandboxComponent;
import io.reist.sandbox.app.SandboxComponentCache;
import io.reist.sandbox.app.SandboxModule;
import io.reist.sandbox.app.model.Repo;
import io.reist.sandbox.app.model.User;
import io.reist.sandbox.app.view.MainActivity;
import io.reist.sandbox.repos.ReposModule;
import io.reist.sandbox.repos.model.RepoService;
import io.reist.sandbox.repos.presenter.RepoEditPresenter;
import io.reist.sandbox.repos.presenter.RepoListPresenter;
import io.reist.sandbox.repos.view.RepoEditView;
import io.reist.sandbox.repos.view.RepoListView;
import io.reist.sandbox.users.presenter.DaggerUserListPresenterTest_TestComponent;
import io.reist.sandbox.users.presenter.UserListPresenter;
import io.reist.sandbox.users.view.UserListView;
import io.reist.visum.BaseModule;
import io.reist.visum.model.BaseResponse;
import io.reist.visum.model.Response;
import io.reist.visum.presenter.BasePresenter;
import io.reist.visum.view.BaseFragment;
import rx.Observable;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by m039 on 11/27/15.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class ReposPresentersTest {

    @Inject
    RepoListPresenter mRepoListPresenter;

    @Inject
    RepoEditPresenter mRepoEditPresenter;

    @Before
    public void setUp() throws Exception {
        DaggerReposPresentersTest_TestComponent
                .builder()
                .reposModule(new TestReposModule())
                .baseModule(new BaseModule(RuntimeEnvironment.application))
                .build()
                .inject(this);
    }

    @Singleton
    @Component(modules = SandboxModule.class)
    public interface TestComponent {

        void inject(ReposPresentersTest reposPresentersTest);

    }

    public static class TestReposModule extends ReposModule {

        @Override
        protected RepoService repoService(@Named(SandboxModule.LOCAL_SERVICE) RepoService local,
                                          @Named(SandboxModule.REMOTE_SERVICE) RepoService remote) {
            RepoService mockedRepoService = mock(RepoService.class);

            List<Repo> repos = new ArrayList<>();

            User owner = new User();

            owner.id = 1L;
            owner.name = "Vasya";

            Repo repo1 = new Repo();

            repo1.id = 2L;
            repo1.owner = owner;
            repo1.name = "Cracky";

            Repo repo2 = new Repo();

            repo2.id = 3L;
            repo2.owner = owner;
            repo2.name = "Vakio";

            repos.add(repo1);
            repos.add(repo2);

            doReturn(Observable.just(new BaseResponse<>(repos)))
                    .when(mockedRepoService)
                    .list();

            doReturn(Observable.just(new BaseResponse<>(repo1)))
                    .when(mockedRepoService)
                    .byId(any());

            return mockedRepoService;
        }

    }

    @Test
    public void testRepoListPresenter() throws InterruptedException {
        assertThat(mRepoListPresenter).isNotNull();

        mRepoListPresenter.setView(mock(RepoListView.class));
        mRepoListPresenter.onViewAttached();

        Thread.sleep(1000);

        assertThat(mRepoListPresenter.isDataLoaded()).isTrue();
    }

    @Test
    public void testRepoEditPresenter() throws InterruptedException {
        assertThat(mRepoEditPresenter).isNotNull();

        RepoEditView mockedRepoEditView = mock(RepoEditView.class);

        Bundle extras = new Bundle();

        extras.putLong(RepoEditPresenter.EXTRA_REPO_ID, 1L);

        when(mockedRepoEditView.extras()).thenReturn(extras);

        mRepoEditPresenter.setView(mockedRepoEditView);
        mRepoEditPresenter.onViewAttached();

        Thread.sleep(1000);

        assertThat(mRepoEditPresenter.isDataLoaded()).isTrue();
    }
}
