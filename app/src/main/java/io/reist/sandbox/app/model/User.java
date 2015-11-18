package io.reist.sandbox.app.model;

import com.google.gson.annotations.SerializedName;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteColumn;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteType;

import org.parceler.Parcel;

import io.reist.sandbox.app.model.local.UserTable;

/**
 * Created by m039 on 11/12/15.
 */

@Parcel
@StorIOSQLiteType(table = UserTable.NAME)
public class User {

    @SerializedName(JsonField.ID)
    @StorIOSQLiteColumn(name = UserTable.Column.ID, key = true)
    public Long id;

    @SerializedName(JsonField.NAME)
    @StorIOSQLiteColumn(name = UserTable.Column.NAME)
    public String name;

    @SerializedName(JsonField.LOGIN)
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
