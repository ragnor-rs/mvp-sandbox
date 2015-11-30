package io.reist.sandbox.test.core;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.SystemClock;
import android.support.annotation.IdRes;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by m039 on 11/27/15.
 */
public class TestUtils {

    static public void waitForMs(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (Exception e) {
        }
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

    static public ViewAction clickOnId(final @IdRes int resId) {
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
