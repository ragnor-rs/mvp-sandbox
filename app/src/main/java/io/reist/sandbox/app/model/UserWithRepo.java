package io.reist.sandbox.app.model;

import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteColumn;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteType;

import io.reist.sandbox.app.model.local.UserWithRepoTable;

/**
 * Created by m039 on 11/16/15.
 */
@StorIOSQLiteType(table = UserWithRepoTable.NAME)
public class UserWithRepo {

    @StorIOSQLiteColumn(name = UserWithRepoTable.Column.USER_ID, key = true)
    public String userId;

    @StorIOSQLiteColumn(name = UserWithRepoTable.Column.REPO_ID, key = true)
    public Long repoId;

    @StorIOSQLiteColumn(name = UserWithRepoTable.Column.LIKED_BY_ME)
    public Integer likedByMe;

}
