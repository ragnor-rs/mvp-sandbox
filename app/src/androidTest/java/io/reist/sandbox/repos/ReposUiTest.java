package io.reist.sandbox.repos;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;

import io.reist.sandbox.R;
import io.reist.sandbox.app.view.MainActivity;
import io.reist.sandbox.core.ActivityInstrumentationTestCase;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static io.reist.sandbox.core.TestUtils.waitForMs;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by m039 on 11/27/15.
 */
@RunWith(AndroidJUnit4.class)
public class ReposUiTest extends ActivityInstrumentationTestCase<MainActivity> {

    public ReposUiTest() {
        super(MainActivity.class);
    }

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();

        getActivity();
    }

    @Test
    public void testRepo() throws InterruptedException {
        onView(withId(R.id.drawer_layout))
                .perform(DrawerActions.open());

        onView(allOf(isDescendantOfA(withId(R.id.nav_view)), withText(R.string.menu_repos)))
                .perform(click());

        Thread.sleep(1000);

        onView(withId(R.id.daggertest_repo_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        Thread.sleep(1000);

        String generatedRepoName = generateRepoName();

        onView(withId(R.id.repo_name))
                .perform(clearText())
                .perform(typeText(generatedRepoName));

        onView(withId(R.id.save)).perform(click());

        onView(withContentDescription(android.support.v7.appcompat.R.string.abc_action_bar_up_description))
                .perform(click());

        Thread.sleep(1000);

        onView(withText(generatedRepoName)).check(matches(isDisplayed()));
    }

    public static String generateRepoName() {
        return "cracky" + (new Random().nextInt(10000));
    }
}
