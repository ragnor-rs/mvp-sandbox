package io.reist.sandbox.app;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.Instrumentation;
import android.os.SystemClock;
import android.support.annotation.IdRes;
import android.support.test.InstrumentationRegistry;
import android.support.test.annotation.UiThreadTest;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.TextView;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.reist.sandbox.R;
import io.reist.sandbox.app.view.MainActivity;
import io.reist.sandbox.user.view.UserReposFragment;
import io.reist.sandbox.user.view.UsersFragment;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.registerIdlingResources;
import static android.support.test.espresso.Espresso.registerLooperAsIdlingResource;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by m039 on 11/20/15.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    MainActivity mMainActivity;

    public MainActivityTest() {
        super(MainActivity.class);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        mMainActivity = getActivity();
    }

    @Test
    public void testLike() throws Throwable {
        onView(withId(R.id.drawer_layout))
                .perform(DrawerActions.open());

        onView(allOf(isDescendantOfA(withId(R.id.nav_view)), withText(R.string.menu_users)))
                .perform(click());

        waitForMs(1000);

        onView(withId(R.id.recycler))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        waitForMs(1000);

        boolean isLiked = isLiked(R.id.recycler, 0);

        onView(withId(R.id.recycler))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, clickOnId(R.id.like)));

        waitForMs(1000);

        Assert.assertEquals(isLiked, !isLiked(R.id.recycler, 0));

        onView(withId(R.id.recycler))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, clickOnId(R.id.like)));

        waitForMs(1000);

        Assert.assertEquals(isLiked, isLiked(R.id.recycler, 0));
    }

    boolean isLiked(@IdRes int recyclerViewId, int position) {
        Activity activity = mMainActivity;

        TextView like = (TextView) ((RecyclerView) activity.findViewById(recyclerViewId))
                .findViewHolderForAdapterPosition(position)
                .itemView
                .findViewById(R.id.like);

        return like.getText().equals(activity.getString(R.string.repo_button_like));
    }

    void waitForFragment(Class<? extends Fragment> fragmentClass) {
        waitForFragment(mMainActivity, fragmentClass, R.id.fragment_container, 1000);
    }

    static void waitForFragment(Activity activity, Class<? extends Fragment> fragmentClass, @IdRes int fragmentResId, long timeOutMillis) {
        FragmentManager fm = activity.getFragmentManager();

        long end = SystemClock.uptimeMillis() + timeOutMillis;

        while (SystemClock.uptimeMillis() <= end) {
            Fragment fragment = fm.findFragmentById(fragmentResId);
            if (fragment != null && fragmentClass.isAssignableFrom(fragment.getClass())) {
                return;
            }

            try { Thread.sleep(10); } catch (InterruptedException e) {}
        }

        throw new RuntimeException("waitForFragment: timeout for " + fragmentClass);
    }

    static void waitForMs(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (Exception e) {
        }
    }

    static ViewAction clickOnId(final @IdRes int resId) {
        return new ViewAction() {
            @Override
            public org.hamcrest.Matcher<View> getConstraints() {
                return null;
            }

            @Override
            public String getDescription() {
                return "Click on a child view with " + resId;
            }

            @Override
            public void perform(UiController uiController, View view) {
                view.findViewById(resId).performClick();
            }
        };
    }

}
