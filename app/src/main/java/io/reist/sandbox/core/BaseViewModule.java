package io.reist.sandbox.core;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Reist on 10/17/15.
 */
@Module(includes = BaseModule.class)
public class BaseViewModule {

    @Provides
    LayoutInflater layoutInflater(Context context) {
        return (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Provides
    LinearLayoutManager linearLayoutManager(Context context) {
        return new LinearLayoutManager(context);
    }

}
