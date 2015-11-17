package io.reist.sandbox.app.model;

import com.google.gson.annotations.SerializedName;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteColumn;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteType;

import io.reist.sandbox.app.model.local.ReposTable;

@StorIOSQLiteType(table = ReposTable.NAME)
public class Repo {

    @SerializedName(JsonField.ID)
    @StorIOSQLiteColumn(name = ReposTable.Column.ID, key = true)
    public Long id;

    @SerializedName(JsonField.NAME)
    @StorIOSQLiteColumn(name = ReposTable.Column.NAME)
    public String name;

    @SerializedName(JsonField.HTML_URL)
    @StorIOSQLiteColumn(name = ReposTable.Column.URL)
    public String url;

    @SerializedName(JsonField.LIKE_COUNT)
    @StorIOSQLiteColumn(name = ReposTable.Column.LIKE_COUNT)
    public Integer likeCount;

    @SerializedName(JsonField.OWNER)
    public User owner;

    @SerializedName(JsonField.LIKED_BY_ME)
    public Boolean likedByMe;

    @StorIOSQLiteColumn(name = ReposTable.Column.USER_ID)
    public String userId;

    public boolean isLiked() {
        return likedByMe != null? likedByMe : false;
    }

    @Override
    public String toString() {
        return name;
    }
}
