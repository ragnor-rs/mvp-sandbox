package io.reist.sandbox;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import io.reist.sandbox.repos.model.sqlite.ReposTable;

public class DbOpenHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    public DbOpenHelper(@NonNull Context context) {
        super(context, "sandbox", null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(@NonNull SQLiteDatabase db) {
        db.execSQL(ReposTable.getCreateTableQuery(DATABASE_VERSION));
    }

    @Override
    public void onUpgrade(@NonNull SQLiteDatabase db, int oldVersion, int newVersion) {}

}