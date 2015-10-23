package io.reist.sandbox.core.di;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;

import dagger.Module;
import dagger.Provides;

@Module
public class BaseViewModule {

    private final Context context;

    public BaseViewModule(Context context) {
        this.context = context;
    }

    @Provides
    Context context() {
        return context;
    }

    @Provides
    LayoutInflater layoutInflater() {
        return (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Provides
    LinearLayoutManager linearLayoutManager() {
        return new LinearLayoutManager(context);
    }

}
