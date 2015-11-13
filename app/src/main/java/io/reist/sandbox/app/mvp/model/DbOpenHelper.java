package io.reist.sandbox.app.mvp.model;

import android.content.Context;
import android.support.annotation.NonNull;

import io.reist.sandbox.core.mvp.model.local.BaseDbHelper;
import io.reist.sandbox.repolist.mvp.model.local.ReposTable;

public class DbOpenHelper extends BaseDbHelper {

    private static final String DATABASE_NAME = "sandbox";
    private static final int DATABASE_VERSION = 2;

    public DbOpenHelper(@NonNull Context context) {
        super(context, DATABASE_NAME, DATABASE_VERSION);
        addTable(ReposTable.class);
    }

}