package io.reist.sandbox.app.model;

import com.google.gson.annotations.SerializedName;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteColumn;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteType;

import io.reist.sandbox.app.model.local.ReposTable;
import io.reist.visum.model.local.BaseTable;

@StorIOSQLiteType(table = ReposTable.NAME)
public class Repo {

    @SerializedName("id")
    @StorIOSQLiteColumn(name = ReposTable.Column.ID, key = true)
    public Long id;

    @SerializedName("revision")
    @StorIOSQLiteColumn(name = BaseTable.Column.REVISION)
    public int revision;

    @SerializedName("name")
    @StorIOSQLiteColumn(name = ReposTable.Column.NAME)
    public String name;

    @SerializedName("html_url")
    @StorIOSQLiteColumn(name = ReposTable.Column.URL)
    public String url;

    @SerializedName("like_count")
    @StorIOSQLiteColumn(name = ReposTable.Column.LIKE_COUNT)
    public int likeCount;

    @SerializedName("liked_by_me")
    @StorIOSQLiteColumn(name = ReposTable.Column.LIKED_BY_ME)
    public boolean likedByMe;

    @SerializedName("owner")
    public User owner;

    public boolean isLiked() {
        return likedByMe;
    }

    @Override
    public String toString() {
        return name;
    }
}
