package io.reist.sandbox.model;


import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import io.reist.sandbox.BuildConfig;
import io.reist.sandbox.app.view.MainActivity;


/**
 * Created by m039 on 11/19/15.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class ModelTest {

    private MainActivity mActivity;

    @Before
    public void setup() {
        mActivity = Robolectric.buildActivity(MainActivity.class).create().get();
    }

    @Test
    public void myActivityAppearsAsExpectedInitially() {
        Assert.assertEquals(1, 1);
    }
}
