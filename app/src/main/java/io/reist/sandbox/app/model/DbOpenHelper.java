package io.reist.sandbox.app.model;

import android.content.Context;
import android.support.annotation.NonNull;

import io.reist.sandbox.app.model.local.ReposTable;
import io.reist.sandbox.app.model.local.UserTable;
import io.reist.sandbox.app.model.local.UserWithRepoTable;
import io.reist.sandbox.core.model.local.BaseDbHelper;

public class DbOpenHelper extends BaseDbHelper {

    private static final String DATABASE_NAME = "sandbox";
    private static final int DATABASE_VERSION = 3;

    public DbOpenHelper(@NonNull Context context) {
        super(context, DATABASE_NAME, DATABASE_VERSION);
        addTable(ReposTable.class);
        addTable(UserTable.class);
        addTable(UserWithRepoTable.class);
    }

}