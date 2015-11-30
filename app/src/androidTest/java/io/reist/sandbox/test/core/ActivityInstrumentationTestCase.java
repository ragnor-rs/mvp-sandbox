package io.reist.sandbox.test.core;

import android.test.ActivityInstrumentationTestCase2;

import io.reist.visum.view.BaseActivity;

/**
 * Created by m039 on 11/30/15.
 */
public class ActivityInstrumentationTestCase<T extends BaseActivity> extends ActivityInstrumentationTestCase2<T> {

    public ActivityInstrumentationTestCase(Class<T> clazz) {
        super(clazz);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }
}
