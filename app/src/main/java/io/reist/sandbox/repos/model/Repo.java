package io.reist.sandbox.repos.model;

import com.google.gson.annotations.SerializedName;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteColumn;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteType;

import io.reist.sandbox.repos.model.database.ReposTable;
import io.reist.sandbox.repos.model.network.ReposJson;

@StorIOSQLiteType(table = ReposTable.TABLE_NAME)
public class Repo {

    @SerializedName(ReposJson.FIELD_ID)
    @StorIOSQLiteColumn(name = ReposTable.COLUMN_ID, key = true)
    public Long id;

    @SerializedName(ReposJson.FIELD_NAME)
    @StorIOSQLiteColumn(name = ReposTable.COLUMN_NAME)
    public String name;

    @SerializedName(ReposJson.FIELD_URL)
    @StorIOSQLiteColumn(name = ReposTable.COLUMN_URL)
    public String url;

}
