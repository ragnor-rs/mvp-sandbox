package io.reist.sandbox.users.presenter;

import android.app.Instrumentation;
import android.content.Intent;
import android.test.ActivityUnitTestCase;

import com.pushtorefresh.storio.sqlite.StorIOSQLite;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Component;
import io.reist.sandbox.BuildConfig;
import io.reist.sandbox.app.DaggerSandboxComponent;
import io.reist.sandbox.app.SandboxApplication;
import io.reist.sandbox.app.SandboxComponent;
import io.reist.sandbox.app.SandboxComponentCache;
import io.reist.sandbox.app.SandboxModule;
import io.reist.sandbox.app.model.User;
import io.reist.sandbox.app.model.remote.GitHubApi;
import io.reist.sandbox.users.UsersModule;
import io.reist.sandbox.users.model.UserService;
import io.reist.sandbox.users.presenter.UserListPresenter;
import io.reist.sandbox.users.view.UserListFragment;
import io.reist.sandbox.users.view.UserListView;
import io.reist.visum.BaseModule;
import io.reist.visum.model.BaseResponse;
import rx.Observable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

/**
 * Created by m039 on 11/25/15.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class UserListPresenterTest {

    @Inject
    UserListPresenter mUserListPresenter;

    @Before
    public void setUp() throws Exception {
        DaggerUserListPresenterTest_TestComponent
                .builder()
                .usersModule(new TestUsersModule())
                .baseModule(new BaseModule(RuntimeEnvironment.application))
                .build()
                .inject(this);
    }

    @Singleton
    @Component(modules = SandboxModule.class)
    public interface TestComponent {

        void inject(UserListPresenterTest userListPresenterTest);

    }

    public static class TestUsersModule extends UsersModule {

        @Override
        protected UserService userService(GitHubApi gitHubApi, StorIOSQLite storIOSQLite) {
            UserService mockedUserService = mock(UserService.class);

            List<User> users = new ArrayList<>();

            User user1 = new User();
            user1.id = 1L;
            user1.name = "Alfred";
            users.add(user1);

            User user2 = new User();
            user2.id = 2L;
            user2.name = "Frank";
            users.add(user2);

            doReturn(Observable.just(new BaseResponse<>(users))).when(mockedUserService).list();

            return mockedUserService;
        }

    }

    @Test
    public void testPresenter() throws InterruptedException {
        assertThat(mUserListPresenter).isNotNull();

        mUserListPresenter.setView(mock(UserListView.class));
        mUserListPresenter.onViewAttached();

        Thread.sleep(1000);

        assertThat(mUserListPresenter.isDataLoaded()).isTrue();
    }

}
