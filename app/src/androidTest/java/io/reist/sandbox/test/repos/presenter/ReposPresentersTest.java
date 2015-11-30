package io.reist.sandbox.test.repos.presenter;

import android.app.Instrumentation;
import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

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
import io.reist.sandbox.test.core.ActivityInstrumentationTestCase;
import io.reist.visum.BaseModule;
import io.reist.visum.model.BaseResponse;
import io.reist.visum.model.Response;
import io.reist.visum.presenter.BasePresenter;
import io.reist.visum.view.BaseFragment;
import rx.Observable;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static io.reist.sandbox.test.core.TestUtils.waitForMs;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by m039 on 11/27/15.
 */
@RunWith(AndroidJUnit4.class)
public class ReposPresentersTest extends ActivityInstrumentationTestCase<MainActivity> {

    public ReposPresentersTest() {
        super(MainActivity.class);
    }

    private MainActivity mMainActivity;

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();

        Instrumentation instrumentation = getInstrumentation();

        SandboxApplication sandboxApplication = (SandboxApplication) instrumentation
                .getTargetContext()
                .getApplicationContext();

        SandboxComponent sandboxComponent = DaggerSandboxComponent
                .builder()
                .reposModule(new TestReposModule())
                .baseModule(new BaseModule(sandboxApplication))
                .build();

        sandboxApplication.setComponentCache(new SandboxComponentCache(sandboxComponent));

        mMainActivity = getActivity();
    }

    public static class TestReposModule extends ReposModule {

        @Override
        protected RepoService repoService(@Named(SandboxModule.LOCAL_SERVICE) RepoService local,
                                          @Named(SandboxModule.REMOTE_SERVICE) RepoService remote) {

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

            final Observable<? extends Response<Repo>> repoResult = Observable.just(new BaseResponse<>(repo1));
            final Observable<? extends Response<List<Repo>>> listReposResult = Observable.just(new BaseResponse<>(repos));

            // FIXME when().then() doesn't work here - needs more investigation
            return new RepoService() {

                @Override
                public Observable<? extends Response<List<Repo>>> list() {
                    return listReposResult;
                }

                @Override
                public Observable<? extends Response<Repo>> byId(Long id) {
                    return repoResult;
                }

                @Override
                public Observable<? extends Response<List<Repo>>> save(List<Repo> list) {
                    throw new UnsupportedOperationException();
                }

                @Override
                public Observable<? extends Response<Repo>> save(Repo repo) {
                    throw new UnsupportedOperationException();
                }

                @Override
                public Observable<? extends Response<Integer>> delete(Long id) {
                    throw new UnsupportedOperationException();
                }

                @Override
                public Response<List<Repo>> saveSync(List<Repo> list) {
                    throw new UnsupportedOperationException();
                }

                @Override
                public Response<Repo> saveSync(Repo repo) {
                    throw new UnsupportedOperationException();
                }

                @Override
                public Observable<? extends Response<List<Repo>>> findReposByUserId(Long userId) {
                    throw new UnsupportedOperationException();
                }

                @Override
                public Observable<? extends Response<Repo>> like(Repo repo) {
                    throw new UnsupportedOperationException();
                }

                @Override
                public Observable<? extends Response<Repo>> unlike(Repo repo) {
                    throw new UnsupportedOperationException();
                }

            };

        }

    }


    @Test
    public void testPresenters() {
        onView(withId(R.id.drawer_layout))
                .perform(DrawerActions.open());

        onView(allOf(isDescendantOfA(withId(R.id.nav_view)), withText(R.string.menu_repos)))
                .perform(click());

        waitForMs(1000);

        assertThat(((RepoListPresenter) getCurrentPresenter()).isDataLoaded()).isTrue();

        waitForMs(1000);

        onView(withId(R.id.daggertest_repo_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        waitForMs(1000);

        assertThat(((RepoEditPresenter) getCurrentPresenter()).isDataLoaded()).isTrue();
    }

    BasePresenter getCurrentPresenter() {
        return ((BaseFragment) mMainActivity.getFragmentManager().findFragmentById(R.id.fragment_container))
                .getPresenter();
    }
}
