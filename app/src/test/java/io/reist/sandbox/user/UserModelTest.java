package io.reist.sandbox.user;

import com.pushtorefresh.storio.sqlite.StorIOSQLite;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Component;
import dagger.Module;
import dagger.Provides;
import io.reist.sandbox.BuildConfig;
import io.reist.sandbox.app.SandboxApplication;
import io.reist.sandbox.app.SandboxComponent;
import io.reist.sandbox.app.SandboxModule;
import io.reist.sandbox.app.model.User;
import io.reist.sandbox.app.model.remote.GitHubApi;
import io.reist.sandbox.repo.RepoModule;
import io.reist.sandbox.repo.model.RepoService;
import io.reist.sandbox.user.model.UserCachedService;
import io.reist.sandbox.user.model.UserService;
import io.reist.sandbox.user.model.local.StorIoUserService;
import io.reist.sandbox.user.model.remote.RetrofitUserService;
import io.reist.visum.BaseModule;
import io.reist.visum.model.Response;
import rx.Observable;
import rx.observers.TestSubscriber;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

/**
 * Created by m039 on 11/27/15.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class UserModelTest {

    @Inject
    UserService userService;

    TestComponent testComponent;

    @Before
    public void setup() {
        SandboxApplication sandboxApplication = (SandboxApplication) RuntimeEnvironment.application;

        testComponent = DaggerUserModelTest_TestComponent
                .builder()
                .baseModule(new BaseModule(sandboxApplication))
                .userModule(new TestUserModule())
                .build();

        testComponent.inject(this);
        assertThat(userService).isNotNull();
    }

    @Singleton
    @Component(modules = SandboxModule.class)
    public interface TestComponent extends SandboxComponent {
        void inject(UserModelTest baseTest);
    }

    public static class TestUserModule extends UserModule {

        @Override
        UserService userService(GitHubApi ignored, StorIOSQLite storIOSQLite) {
            RetrofitUserService retrofitUserService = spy(new RetrofitUserService(sMockedGitHubApi));

            doReturn(Observable.empty()).when(retrofitUserService).byId(any());

            return new UserCachedService(new StorIoUserService(storIOSQLite), retrofitUserService);
        }

    }

    private static final GitHubApi sMockedGitHubApi = mock(GitHubApi.class);
    private static final long USER_ID = -(new Random().nextLong());

    @Test
    public void testUserService() {
        firstTestCase();
        testIfUsersExist();
        testIfUserWithUserIdExist();

        secondTestCase();
        testIfUserWithUserIdExist();
    }

    void testIfUsersExist() {
        TestSubscriber<Response<List<User>>> testSubscriber = new TestSubscriber<>();
        userService.list().subscribe(testSubscriber);

        testSubscriber.awaitTerminalEventAndUnsubscribeOnTimeout(100, TimeUnit.MILLISECONDS);

        assertThat(testSubscriber.getOnNextEvents().get(0).getResult().isEmpty())
                .isFalse();
    }

    void testIfUserWithUserIdExist() {
        TestSubscriber<Response<User>> testSubscriber = new TestSubscriber<>();
        userService.byId(USER_ID).subscribe(testSubscriber);

        testSubscriber.awaitTerminalEventAndUnsubscribeOnTimeout(100, TimeUnit.MILLISECONDS);

        assertThat(testSubscriber.getOnNextEvents().get(0).getResult().id)
                .isEqualTo(USER_ID);
    }

    void firstTestCase() {
        List<User> users = new ArrayList<>();

        User user = new User();

        user.id = USER_ID;

        users.add(user);

        when(sMockedGitHubApi.listUsers())
                .thenReturn(Observable.just(new Response<>(users)));
    }

    void secondTestCase() {
        when(sMockedGitHubApi.listUsers())
                .thenReturn(Observable.empty());
    }

}
