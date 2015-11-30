package io.reist.sandbox.core;

import android.app.Instrumentation;
import android.support.test.InstrumentationRegistry;
import android.test.ActivityInstrumentationTestCase2;

import io.reist.sandbox.app.SandboxApplication;
import io.reist.sandbox.app.SandboxComponent;
import io.reist.sandbox.app.SandboxComponentCache;
import io.reist.visum.ComponentCache;
import io.reist.visum.view.BaseActivity;

/**
 * Created by m039 on 11/30/15.
 */
public class ActivityInstrumentationTestCase<T extends BaseActivity> extends ActivityInstrumentationTestCase2<T> {

    public ActivityInstrumentationTestCase(Class<T> clazz) {
        super(clazz);
    }

    private static ComponentCache sDefaultComponentCache = null;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        injectInstrumentation(instrumentation);

        SandboxApplication sandboxApplication = (SandboxApplication) instrumentation
                .getTargetContext()
                .getApplicationContext();

        synchronized (ActivityInstrumentationTestCase.class) {
            if (sDefaultComponentCache == null) {
                sDefaultComponentCache = sandboxApplication.getComponentCache();
            }

            sandboxApplication.setComponentCache(sDefaultComponentCache);
        }
    }
}
