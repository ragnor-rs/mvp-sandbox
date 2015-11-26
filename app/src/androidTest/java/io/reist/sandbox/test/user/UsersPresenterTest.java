package io.reist.sandbox.test.user;

import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityUnitTestCase;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Component;
import dagger.Module;
import dagger.Provides;
import io.reist.sandbox.app.SandboxApplication;
import io.reist.sandbox.app.SandboxComponent;
import io.reist.sandbox.app.SandboxModule;
import io.reist.sandbox.app.model.User;
import io.reist.sandbox.repo.model.RepoService;
import io.reist.sandbox.user.UserFragmentComponent;
import io.reist.sandbox.user.model.UserService;
import io.reist.sandbox.user.view.UsersFragment;
import io.reist.visum.BaseModule;
import io.reist.visum.model.Response;
import io.reist.visum.model.VisumResponse;
import rx.Observable;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by m039 on 11/25/15.
 */
@RunWith(AndroidJUnit4.class)
public class UsersPresenterTest extends ActivityUnitTestCase<UsersTestActivity> {

    public UsersPresenterTest() {
        super(UsersTestActivity.class);
    }

    UsersTestActivity mTestUsersActivity;

    @Before
    public void setUp() throws Exception {
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();

        SandboxApplication sandboxApplication = (SandboxApplication) instrumentation
                .getTargetContext()
                .getApplicationContext();

        TestComponent modelComponent = DaggerUsersPresenterTest_TestComponent
                .builder()
                .sandboxModule(new SandboxModule())
                .baseModule(new BaseModule(sandboxApplication))
                .build();

        sandboxApplication.setSandboxComponent(modelComponent);

        injectInstrumentation(instrumentation);
        setApplication(sandboxApplication);

        super.setUp();

        instrumentation.runOnMainSync(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(sandboxApplication, UsersTestActivity.class);
                startActivity(intent, null, null);
            }
        });

        try { Thread.sleep(1000); } catch (Exception e) {}

        mTestUsersActivity = getActivity();
    }

    @Singleton
    @Component(modules = SandboxModule.class)
    public static abstract class TestComponent implements SandboxComponent {

        @Override
        public UserFragmentComponent userFragmentComponent() {
            TestUserModule testUserModule = new TestUserModule();

            inject(testUserModule);

            return DaggerUsersPresenterTest_TestUserFragmentComponent
                    .builder()
                    .testUserModule(testUserModule)
                    .build();
        }

        public abstract void inject(TestUserModule testUserModule);
    }

    @Singleton
    @Component(modules = TestUserModule.class)
    public interface TestUserFragmentComponent extends UserFragmentComponent {
    }

    @Module
    public static class TestUserModule {

        @Inject RepoService repoService;

        @Provides
        RepoService repoService() {
            return repoService;
        }

        @Singleton
        @Provides
        UserService userService() {
            UserService mockedUserService = Mockito.mock(UserService.class);

            User user1 = new User();

            user1.id = 1L;
            user1.name = "Alfred";

            User user2 = new User();

            user2.id = 2L;
            user2.name = "Frank";

            List<User> users = new ArrayList<>();

            users.add(user1);
            users.add(user2);

            Mockito.doReturn(Observable.just(new VisumResponse<>(users))).when(mockedUserService).list();

            return mockedUserService;
        }

    }

    @Test
    public void testPresenter() {
        UsersFragment usersFragment = (UsersFragment) mTestUsersActivity
                .getFragmentManager()
                .findFragmentById(android.R.id.content);

        assertThat(usersFragment.getPresenter()).isNotNull();
        Assert.assertTrue(usersFragment.getPresenter().isDataLoaded());
    }

}
