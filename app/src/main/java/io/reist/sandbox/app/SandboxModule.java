package io.reist.sandbox.app;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pushtorefresh.storio.sqlite.SQLiteTypeMapping;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.impl.DefaultStorIOSQLite;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reist.sandbox.app.model.DbOpenHelper;
import io.reist.sandbox.app.model.Repo;
import io.reist.sandbox.app.model.RepoStorIOSQLiteDeleteResolver;
import io.reist.sandbox.app.model.User;
import io.reist.sandbox.app.model.UserStorIOSQLiteDeleteResolver;
import io.reist.sandbox.app.model.UserStorIOSQLiteGetResolver;
import io.reist.sandbox.app.model.UserStorIOSQLitePutResolver;
import io.reist.sandbox.app.model.local.resolvers.RepoGetResolver;
import io.reist.sandbox.app.model.local.resolvers.RepoPutResolver;
import io.reist.sandbox.app.model.remote.GitHubApi;
import io.reist.visum.BaseModule;
import io.reist.visum.model.remote.NestedFieldNameAdapter;
import io.reist.sandbox.repolist.model.CachedRepoService;
import io.reist.sandbox.repolist.model.RepoService;
import io.reist.sandbox.repolist.model.local.StorIoRepoService;
import io.reist.sandbox.repolist.model.remote.RetrofitRepoService;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

@Module(includes = BaseModule.class)
public class SandboxModule {

    public static final String REMOTE_SERVICE = "remote";
    public static final String LOCAL_SERVICE = "local";

    public static final String GIT_HUB_BASE_URL = "https://safe-reaches-4393.herokuapp.com";

    private static final String TAG = SandboxModule.class.getName();

    @Provides @Singleton
    StorIOSQLite storIoSqLite(Context context) {

        DbOpenHelper dbOpenHelper = new DbOpenHelper(context);

        return DefaultStorIOSQLite
                .builder()
                .sqliteOpenHelper(dbOpenHelper)
                .addTypeMapping(
                        Repo.class,
                        SQLiteTypeMapping.<Repo>builder()
                                .putResolver(new RepoPutResolver())
                                .getResolver(new RepoGetResolver())
                                .deleteResolver(new RepoStorIOSQLiteDeleteResolver())
                                .build()
                )
                .addTypeMapping(
                        User.class,
                        SQLiteTypeMapping.<User>builder()
                                .putResolver(new UserStorIOSQLitePutResolver())
                                .getResolver(new UserStorIOSQLiteGetResolver())
                                .deleteResolver(new UserStorIOSQLiteDeleteResolver())
                                .build()
                )
                .build();

    }

    @Provides @Singleton
    GitHubApi gitHubApi() {

        Gson gson = new GsonBuilder()
                .registerTypeHierarchyAdapter(Object.class, new NestedFieldNameAdapter())
                .create();

        OkHttpClient httpClient = new OkHttpClient();

        httpClient.interceptors().add(chain -> {
            Request request = chain.request();
            HttpUrl httpUrl = request
                    .httpUrl()
                    .newBuilder()
                    .addQueryParameter("user_id", "1")
                    .build();

            request = request.newBuilder().url(httpUrl).build();

            Log.i(TAG, request.toString());

            //print request body
//            Buffer buffer = new Buffer();
//            request.body().writeTo(buffer);
//            Log.i(TAG, buffer.readUtf8());

            return chain.proceed(request);
        });

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(GIT_HUB_BASE_URL)
                .client(httpClient)
                .build();


        return retrofit.create(GitHubApi.class);

    }

    @Provides @Singleton @Named(SandboxModule.LOCAL_SERVICE)
    RepoService localRepoService(StorIOSQLite storIoSqLite) {
        return new StorIoRepoService(storIoSqLite);
    }

    @Provides @Singleton @Named(SandboxModule.REMOTE_SERVICE)
    RepoService remoteRepoService(GitHubApi gitHubApi) {
        return new RetrofitRepoService(gitHubApi);
    }

    @Provides @Singleton
    RepoService repoService(
            @Named(SandboxModule.LOCAL_SERVICE) RepoService local,
            @Named(SandboxModule.REMOTE_SERVICE) RepoService remote
    ) {
        return new CachedRepoService(local, remote);
    }

}
