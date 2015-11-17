package io.reist.sandbox.app.model;

import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteColumn;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteType;

import io.reist.sandbox.app.model.local.LikeTable;

/**
 * Created by m039 on 11/16/15.
 */
@StorIOSQLiteType(table = LikeTable.NAME)
public class Like {

    @StorIOSQLiteColumn(name = LikeTable.Column.REPO_ID, key = true)
    public Long repoId;

    @StorIOSQLiteColumn(name = LikeTable.Column.LIKED_BY_ME)
    public Boolean likedByMe;

    public Like() {
    }

    public Like(Long repoId, Boolean likedByMe) {
        this.repoId = repoId;
        this.likedByMe = likedByMe;
    }

}
