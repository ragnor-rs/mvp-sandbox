package io.reist.sandbox.app.di;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pushtorefresh.storio.sqlite.SQLiteTypeMapping;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.impl.DefaultStorIOSQLite;

import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reist.sandbox.app.mvp.model.DbOpenHelper;
import io.reist.sandbox.core.di.BaseModule;
import io.reist.sandbox.core.mvp.model.ListObservable;
import io.reist.sandbox.core.mvp.model.remote.retrofit.NestedFieldNameAdapter;
import io.reist.sandbox.core.rx.AndroidSchedulers;
import io.reist.sandbox.core.rx.Func1;
import io.reist.sandbox.core.rx.Observer;
import io.reist.sandbox.core.rx.Schedulers;
import io.reist.sandbox.repos.mvp.model.Repo;
import io.reist.sandbox.repos.mvp.model.RepoStorIOSQLiteDeleteResolver;
import io.reist.sandbox.repos.mvp.model.RepoStorIOSQLiteGetResolver;
import io.reist.sandbox.repos.mvp.model.RepoStorIOSQLitePutResolver;
import io.reist.sandbox.repos.mvp.model.local.storio.StorIoRepoListObservable;
import io.reist.sandbox.repos.mvp.model.remote.retrofit.GitHubApi;
import io.reist.sandbox.repos.mvp.model.remote.retrofit.RetrofitRepoListObservable;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

@Module(includes = BaseModule.class)
public class SandboxModule {

    private static final String GIT_HUB_BASE_URL = "https://api.github.com";

    private static final String TAG = SandboxModule.class.getName();

    private ListObservable<Repo> remoteRepoListObservable() {

        Gson gson = new GsonBuilder()
                .registerTypeHierarchyAdapter(Object.class, new NestedFieldNameAdapter())
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(GIT_HUB_BASE_URL)
                .build();

        GitHubApi gitHubApi = retrofit.create(GitHubApi.class);

        return new RetrofitRepoListObservable(gitHubApi);

    }

    private ListObservable<Repo> localRepoListObservable(Context context) {

        DbOpenHelper dbOpenHelper = new DbOpenHelper(context);

        StorIOSQLite storIoSqLite = DefaultStorIOSQLite.builder()
                .sqliteOpenHelper(dbOpenHelper)
                .addTypeMapping(
                        Repo.class,
                        SQLiteTypeMapping.<Repo>builder()
                            .putResolver(new RepoStorIOSQLitePutResolver())
                            .getResolver(new RepoStorIOSQLiteGetResolver())
                            .deleteResolver(new RepoStorIOSQLiteDeleteResolver())
                            .build()
                )
                .build();

        return new StorIoRepoListObservable(storIoSqLite);

    }

    @Provides @Singleton
    ListObservable<Repo> repoListObservable(Context context) {

        final ListObservable<Repo> local = localRepoListObservable(context);
        final ListObservable<Repo> remote = remoteRepoListObservable();

        remote
                .concatMap(new Func1<List<Repo>, Integer>() {

                    @Override
                    public Integer call(List<Repo> repos) {
                        return local.put(repos);
                    }

                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Observer<Integer>() {

                    @Override
                    public void onNext(Integer integer) {
                        Log.i(TAG, "Saving to cache " + integer.toString() + " object(s)");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Error saving objects to cache", e);
                    }

                    @Override
                    public void onCompleted() {
                        Log.i(TAG, "Finished queue");
                    }

                });

        return local.switchIfListEmpty(remote);

    }

}
