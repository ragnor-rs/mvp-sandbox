package io.reist.sandbox;

import android.content.Context;

import com.pushtorefresh.storio.sqlite.SQLiteTypeMapping;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.impl.DefaultStorIOSQLite;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reist.sandbox.repos.model.Repo;
import io.reist.sandbox.repos.model.RepoService;
import io.reist.sandbox.repos.model.RepoStorIOSQLiteDeleteResolver;
import io.reist.sandbox.repos.model.RepoStorIOSQLiteGetResolver;
import io.reist.sandbox.repos.model.RepoStorIOSQLitePutResolver;
import io.reist.sandbox.repos.model.sqlite.StorIoRepoService;

/**
 * Created by Reist on 10/16/15.
 */
@Module
public class ApplicationModule {

    //private static final String GIT_HIB_BASE_URL = "https://api.github.com";

    private final Context context;

    public ApplicationModule(Context context) {
        this.context = context;
    }

    /*
    @Provides @Singleton
    RepoService repoService() {

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(GIT_HIB_BASE_URL)
                .build();

        GitHubApi gitHubApi = retrofit.create(GitHubApi.class);

        return new GitHubRepoService(gitHubApi);

    }
    */

    /*
    @Provides @Singleton
    RepoService repoService() {
        return new DummyRepoService();
    }
    */

    @Provides @Singleton
    RepoService repoService() {

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

}
