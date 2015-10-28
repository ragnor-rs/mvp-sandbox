package io.reist.sandbox.app.di;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pushtorefresh.storio.sqlite.SQLiteTypeMapping;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.impl.DefaultStorIOSQLite;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reist.sandbox.app.mvp.model.DbOpenHelper;
import io.reist.sandbox.core.di.BaseModule;
import io.reist.sandbox.core.mvp.model.remote.retrofit.NestedFieldNameAdapter;
import io.reist.sandbox.core.rx.Action1;
import io.reist.sandbox.core.rx.AndroidSchedulers;
import io.reist.sandbox.core.rx.Func1;
import io.reist.sandbox.core.rx.Observable;
import io.reist.sandbox.core.rx.Schedulers;
import io.reist.sandbox.repos.mvp.model.Repo;
import io.reist.sandbox.repos.mvp.model.RepoStorIOSQLiteDeleteResolver;
import io.reist.sandbox.repos.mvp.model.RepoStorIOSQLiteGetResolver;
import io.reist.sandbox.repos.mvp.model.RepoStorIOSQLitePutResolver;
import io.reist.sandbox.repos.mvp.model.local.storio.StorIoRepoListOnSubscribe;
import io.reist.sandbox.repos.mvp.model.remote.retrofit.GitHubApi;
import io.reist.sandbox.repos.mvp.model.remote.retrofit.RetrofitRepoListOnSubscribe;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

@Module(includes = BaseModule.class)
public class SandboxModule {

    private static final String GIT_HUB_BASE_URL = "https://api.github.com";

    public static final String LOCAL_SERVICE = "local";
    public static final String REMOTE_SERVICE = "remote";

    @Provides @Singleton
    DbOpenHelper dbOpenHelper(Context context) {
        return new DbOpenHelper(context);
    }

    @Provides @Singleton
    StorIOSQLite storIoSqLite(DbOpenHelper dbOpenHelper) {
        return DefaultStorIOSQLite.builder()
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
    }

    @Provides @Singleton @Named(REMOTE_SERVICE)
    Observable<List<Repo>> remoteRepoListObservable(final StorIOSQLite storIoSqLite) {

        Gson gson = new GsonBuilder()
                .registerTypeHierarchyAdapter(Object.class, new NestedFieldNameAdapter())
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(GIT_HUB_BASE_URL)
                .build();

        GitHubApi gitHubApi = retrofit.create(GitHubApi.class);

        return Observable
                .create(new RetrofitRepoListOnSubscribe(gitHubApi))
                .forEach(new Action1<List<Repo>>() {

                    @Override
                    public void call(List<Repo> repos) {
                        storIoSqLite.put()
                                .objects(repos)
                                .prepare()
                                .executeAsBlocking();
                    }

                });

    }

    @Provides @Singleton @Named(LOCAL_SERVICE)
    Observable<List<Repo>> localRepoListObservable(StorIOSQLite storIoSqLite) {
        return Observable.create(new StorIoRepoListOnSubscribe(storIoSqLite));
    }

    @Provides @Singleton
    Observable<List<Repo>> repoListObservable(
            @Named(LOCAL_SERVICE) final Observable<List<Repo>> local,
            @Named(REMOTE_SERVICE) final Observable<List<Repo>> remote
    ) {

        return local
                .switchMap(new Func1<List<Repo>, Observable<List<Repo>>>() {

                    @Override
                    public Observable<List<Repo>> call(List<Repo> repos) {
                        return repos == null || repos.isEmpty() ?
                                remote :
                                Observable.just(repos).concatWith(local);
                    }

                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

}
