package io.reist.sandbox.test.user;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.SystemClock;
import android.support.annotation.IdRes;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.reist.sandbox.R;
import io.reist.sandbox.app.view.MainActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

/**
 * Created by m039 on 11/20/15.
 */
@RunWith(AndroidJUnit4.class)
public class UserUiTest extends ActivityInstrumentationTestCase2<MainActivity> {

    /**
     * Should take into consideration delays during network operations
     */
    public static final int ACTION_TIMEOUT = 5000;

    MainActivity mMainActivity;

    public UserUiTest() {
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

        // open sidebar
        onView(withId(R.id.drawer_layout))
                .perform(DrawerActions.open());

        // go to users section
        onView(allOf(isDescendantOfA(withId(R.id.nav_view)), withText(R.string.menu_users)))
                .perform(click());

        waitForMs(ACTION_TIMEOUT);

        // click on user
        onView(withId(R.id.recycler))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        waitForMs(ACTION_TIMEOUT);

        // is first repo liked?
        boolean isLiked = isRepoLiked(R.id.recycler, 0);

        Log.i(UserUiTest.class.getName(), "isRepoLiked = " + isLiked);

        // click on like button
        onView(withId(R.id.recycler))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, clickOnId(R.id.like)));

        waitForMs(ACTION_TIMEOUT);

        // check if like status changed
        Assert.assertEquals(!isLiked, isRepoLiked(R.id.recycler, 0));

        // click on like button (should be an opposite of first button state)
        onView(withId(R.id.recycler))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, clickOnId(R.id.like)));

        waitForMs(ACTION_TIMEOUT);

        // check if like status reverted
        Assert.assertEquals(isLiked, isRepoLiked(R.id.recycler, 0));

    }

    boolean isRepoLiked(@IdRes int recyclerViewId, int position) {
        Activity activity = mMainActivity;

        TextView like = (TextView) ((RecyclerView) activity.findViewById(recyclerViewId))
                .findViewHolderForAdapterPosition(position)
                .itemView
                .findViewById(R.id.like);

        final String label = like.getText().toString();
        if (label.equalsIgnoreCase(activity.getString(R.string.repo_button_unlike))) {
            return true;
        } else if (label.equalsIgnoreCase(activity.getString(R.string.repo_button_like))) {
            return false;
        } else {
            fail("Like button label: " + label);
            return false;
        }
    }

    void waitForItems(@IdRes int recylerViewId, long timeOutMillis) {
        waitForItems((RecyclerView) mMainActivity.findViewById(recylerViewId), timeOutMillis);
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

    static void waitForItems(RecyclerView recyclerView, long timeoutMillis) {
        long end = SystemClock.uptimeMillis() + timeoutMillis;

        while (end >= SystemClock.uptimeMillis()) {
            RecyclerView.Adapter adapter = recyclerView.getAdapter();
            if (adapter != null && adapter.getItemCount() > 0) {
                return;
            }

            try { Thread.sleep(10); } catch (InterruptedException e) {}
        }
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
