package io.reist.sandbox.core;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Reist on 10/17/15.
 */
@Module
public class BaseModule {

    private final Context context;

    public BaseModule(Context context) {
        this.context = context;
    }

    @Provides
    Context context() {
        return context;
    }

}
