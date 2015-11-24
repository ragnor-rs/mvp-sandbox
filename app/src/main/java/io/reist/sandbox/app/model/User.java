package io.reist.sandbox.app.model;

import com.google.gson.annotations.SerializedName;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteColumn;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteType;

import io.reist.sandbox.app.model.local.UserTable;
import io.reist.visum.model.local.BaseTable;

/**
 * Created by m039 on 11/12/15.
 */

@StorIOSQLiteType(table = UserTable.NAME)
public class User {

    @SerializedName("id")
    @StorIOSQLiteColumn(name = UserTable.Column.ID, key = true)
    public Long id;

    @SerializedName("revision")
    @StorIOSQLiteColumn(name = BaseTable.Column.REVISION)
    public int revision;

    @SerializedName("name")
    @StorIOSQLiteColumn(name = UserTable.Column.NAME)
    public String name;

    @SerializedName("login")
    @StorIOSQLiteColumn(name = UserTable.Column.LOGIN)
    public String login;

    public String getName() {
        return login; // WTF?
    }

    @Override
    public String toString() {
        return User.class.getSimpleName() + "{name = \"" + name + "\", id = " + id + "}";
    }

}
