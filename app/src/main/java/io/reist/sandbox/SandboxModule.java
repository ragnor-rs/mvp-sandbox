package io.reist.sandbox;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pushtorefresh.storio.sqlite.SQLiteTypeMapping;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.impl.DefaultStorIOSQLite;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reist.sandbox.core.BaseModule;
import io.reist.sandbox.core.model.remote.retrofit.NestedFieldNameAdapter;
import io.reist.sandbox.repos.model.CachedRepoService;
import io.reist.sandbox.repos.model.Repo;
import io.reist.sandbox.repos.model.RepoService;
import io.reist.sandbox.repos.model.RepoStorIOSQLiteDeleteResolver;
import io.reist.sandbox.repos.model.RepoStorIOSQLiteGetResolver;
import io.reist.sandbox.repos.model.RepoStorIOSQLitePutResolver;
import io.reist.sandbox.repos.model.local.storio.StorIoRepoService;
import io.reist.sandbox.repos.model.remote.retrofit.GitHubApi;
import io.reist.sandbox.repos.model.remote.retrofit.RetrofitRepoService;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

@Module(includes = BaseModule.class)
public class SandboxModule {

    private static final String GIT_HUB_BASE_URL = "https://api.github.com";

    private RepoService remoteRepoService() {

        Gson gson = new GsonBuilder()
                .registerTypeHierarchyAdapter(Object.class, new NestedFieldNameAdapter())
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(GIT_HUB_BASE_URL)
                .build();

        GitHubApi gitHubApi = retrofit.create(GitHubApi.class);

        return new RetrofitRepoService(gitHubApi);

    }

    private RepoService localRepoService(Context context) {

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

        return new StorIoRepoService(storIoSqLite);

    }

    @Provides @Singleton
    RepoService repoService(Context context) {
        return new CachedRepoService(localRepoService(context), remoteRepoService());
    }

}
