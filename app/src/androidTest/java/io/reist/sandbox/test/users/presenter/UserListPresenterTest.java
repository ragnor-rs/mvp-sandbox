package io.reist.sandbox.test.users.presenter;

import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityUnitTestCase;

<<<<<<< HEAD:app/src/androidTest/java/io/reist/sandbox/test/user/UsersPresenterTest.java
import com.pushtorefresh.storio.sqlite.StorIOSQLite;

=======
>>>>>>> origin/task/sharpened-dagger:app/src/androidTest/java/io/reist/sandbox/users/presenter/UserListPresenterTest.java
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import dagger.Component;
import dagger.Module;
import dagger.Provides;
import io.reist.sandbox.app.DaggerSandboxComponent;
import dagger.Subcomponent;
import io.reist.sandbox.app.SandboxApplication;
import io.reist.sandbox.app.SandboxModule;
import io.reist.sandbox.app.model.User;
import io.reist.sandbox.repos.ReposModule;
import io.reist.sandbox.users.UsersComponent;
import io.reist.sandbox.users.model.UserService;
import io.reist.sandbox.users.presenter.UserListPresenter;
import io.reist.sandbox.test.users.view.UserListActivity;
import io.reist.sandbox.users.view.UserListFragment;
import io.reist.visum.BaseModule;
import io.reist.visum.ComponentCache;
import io.reist.visum.model.Response;
import io.reist.visum.view.BaseView;
import rx.Observable;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by m039 on 11/25/15.
 */
@RunWith(AndroidJUnit4.class)
public class UserListPresenterTest extends ActivityUnitTestCase<UserListActivity> {

    public UserListPresenterTest() {
        super(UserListActivity.class);
    }

    private UserListActivity mTestUsersActivity;

    @Before
    public void setUp() throws Exception {

        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();

        SandboxApplication sandboxApplication = (SandboxApplication) instrumentation
                .getTargetContext()
                .getApplicationContext();

<<<<<<< HEAD:app/src/androidTest/java/io/reist/sandbox/test/user/UsersPresenterTest.java
        SandboxComponent sandboxComponent = DaggerSandboxComponent
                .builder()
                .userModule(new TestUserModule())
                .baseModule(new BaseModule(sandboxApplication))
                .build();

        sandboxApplication.setSandboxComponent(sandboxComponent);
=======
        sandboxApplication.setComponentCache(new TestComponentCache(sandboxApplication));
>>>>>>> origin/task/sharpened-dagger:app/src/androidTest/java/io/reist/sandbox/users/presenter/UserListPresenterTest.java

        injectInstrumentation(instrumentation);
        setApplication(sandboxApplication);

        super.setUp();

        instrumentation.runOnMainSync(() -> {
            Intent intent = new Intent(sandboxApplication, UserListActivity.class);
            startActivity(intent, null, null);
        });

        try { Thread.sleep(1000); } catch (Exception ignored) {}

        mTestUsersActivity = getActivity();

    }

<<<<<<< HEAD:app/src/androidTest/java/io/reist/sandbox/test/user/UsersPresenterTest.java
    public static class TestUserModule extends UserModule {

        @Override
        public UserService userService(GitHubApi gitHubApi, StorIOSQLite storIOSQLite) {
=======
    @Singleton
    @Component(modules = SandboxModule.class)
    public interface TestComponent {

        TestUsersComponent usersComponent();

    }

    @Singleton
    @Subcomponent(modules = TestUsersModule.class)
    public interface TestUsersComponent extends UsersComponent {

        void inject(UserListFragment userFragment);

    }

    @Module(includes = ReposModule.class)
    public static class TestUsersModule {

        @Provides
        @Singleton
        UserService userService() {

>>>>>>> origin/task/sharpened-dagger:app/src/androidTest/java/io/reist/sandbox/users/presenter/UserListPresenterTest.java
            UserService mockedUserService = Mockito.mock(UserService.class);

            List<User> users = new ArrayList<>();

            User user1 = new User();
            user1.id = 1L;
            user1.name = "Alfred";
            users.add(user1);

            User user2 = new User();
            user2.id = 2L;
            user2.name = "Frank";
            users.add(user2);

            Mockito.when(mockedUserService.list())
                    .thenReturn(Observable.just(new Response<>(users)));

            return mockedUserService;

        }

    }

    @Test
    public void testPresenter() {

        UserListFragment userListFragment = (UserListFragment) mTestUsersActivity
                .getFragmentManager()
                .findFragmentById(android.R.id.content);

        final UserListPresenter presenter = userListFragment.getPresenter();

        assertThat(presenter).isNotNull();
        assertTrue(presenter.isDataLoaded());

    }

    private static class TestComponentCache extends ComponentCache {

        private final TestComponent testComponent;

        public TestComponentCache(Context context) {
            this.testComponent  = DaggerUserListPresenterTest_TestComponent.builder()
                    .sandboxModule(new SandboxModule())
                    .baseModule(new BaseModule(context))
                    .build();
        }

        @Override
        public Object buildComponentFor(Class<? extends BaseView> viewClass) {
            if (UserListFragment.class.isAssignableFrom(viewClass)) {
                return testComponent.usersComponent();
            } else {
                throw new RuntimeException("Unknown view class: " + viewClass.getName());
            }
        }

    }

}
