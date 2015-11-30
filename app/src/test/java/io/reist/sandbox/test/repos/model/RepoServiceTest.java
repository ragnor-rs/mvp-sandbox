package io.reist.sandbox.test.repos.model;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Component;
import io.reist.sandbox.BuildConfig;
import io.reist.sandbox.app.SandboxComponent;
import io.reist.sandbox.app.SandboxModule;
import io.reist.sandbox.app.model.Repo;
import io.reist.sandbox.app.model.User;
import io.reist.sandbox.app.model.remote.GitHubApi;

import io.reist.sandbox.repos.ReposModule;
import io.reist.sandbox.test.repos.model.DaggerRepoServiceTest_TestComponent;
import io.reist.sandbox.repos.model.RepoService;
import io.reist.visum.BaseModule;
import io.reist.visum.model.Response;
import rx.Observable;
import rx.observers.TestSubscriber;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


/**
 * Created by m039 on 11/19/15.
 *
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 *
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class RepoServiceTest {

    @Inject
    RepoService repoService;

    @Before
    public void setup() {
        TestComponent modelComponent = DaggerRepoServiceTest_TestComponent
                .builder()
                .reposModule(new TestReposModule())
                .baseModule(new BaseModule(RuntimeEnvironment.application))
                .build();

        modelComponent.inject(this);
    }

    @Singleton
    @Component(modules = SandboxModule.class)
    public interface TestComponent extends SandboxComponent {
        void inject(RepoServiceTest baseTest);
    }

    private static class TestReposModule extends ReposModule {

        @Override
        protected RepoService remoteRepoService(GitHubApi gitHubApi) {
            RepoService mockedRepoService = mock(RepoService.class);

            when(mockedRepoService.like(any()))
                    .thenReturn(Observable.empty());

            when(mockedRepoService.unlike(any()))
                    .thenReturn(Observable.empty());

            when(mockedRepoService.byId(any()))
                    .thenReturn(Observable.empty());

            return mockedRepoService;
        }

    }

    @Test
    public void testCachedService() {
        testOfflineLike(false);
        testOfflineLike(true);
    }

    private void testOfflineLike(boolean like) {
        TestSubscriber<Response<Repo>> subscriber;

        subscriber = new TestSubscriber<>();

        if (like) {
            repoService.like(newRepo()).subscribe(subscriber);
        } else {
            repoService.unlike(newRepo()).subscribe(subscriber);
        }

        subscriber.awaitTerminalEventAndUnsubscribeOnTimeout(100, TimeUnit.MILLISECONDS);

        subscriber = new TestSubscriber<>();

        repoService.byId(REPO_ID).subscribe(subscriber);

        subscriber.awaitTerminalEventAndUnsubscribeOnTimeout(100, TimeUnit.MILLISECONDS);

        assertThat(subscriber.getOnErrorEvents())
                .isEmpty();

        Repo repo = subscriber.getOnNextEvents().get(0).getResult();

        assertThat(repo.id).isEqualTo(REPO_ID);
        assertThat(repo.likedByMe).isEqualTo(like);
    }

    private static final long REPO_ID = 12345L;
    private static final long USER_ID = 43215L;

    private static Repo newRepo() {
        Repo repo = new Repo();

        repo.id = REPO_ID;

        User owner = new User();

        owner.id = USER_ID;
        repo.owner = owner;

        return repo;
    }
}
