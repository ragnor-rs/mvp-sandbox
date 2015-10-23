package io.reist.sandbox.repos.model.local.storio;

import io.reist.sandbox.core.model.local.storio.BaseTable;

/**
 * Created by Reist on 10/16/15.
 */
public class ReposTable extends BaseTable {

    public static final String TABLE_NAME = "repos";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_URL = "url";
    public static final String COLUMN_AUTHOR = "author";

    @Override
    public String getCreateTableQuery(int databaseVersion) {
        switch (databaseVersion) {

            case 1:
                return
                        "create table " + TABLE_NAME + "(" +
                                COLUMN_ID + " integer not null primary key, " +
                                COLUMN_NAME + " text not null, " +
                                COLUMN_URL + " text" +
                        ")";

            case 2:
                return
                        "create table " + TABLE_NAME + "(" +
                                COLUMN_ID + " integer not null primary key, " +
                                COLUMN_NAME + " text not null, " +
                                COLUMN_URL + " text, " +
                                COLUMN_AUTHOR + " text not null" +
                        ")";

            default:
                throw new RuntimeException("Unknown database version: " + databaseVersion);

        }
    }

    @Override
    public String[] getUpgradeTableQueries(int oldVersion) {
        switch (oldVersion) {

            case 1:
                return new String[] {

                        "alter table " +
                                TABLE_NAME + " " +
                        "add column " +
                                COLUMN_AUTHOR + " text " +
                        "default " +
                                "\"JakeWharton\""

                };

            default:
                return new String[0];

        }
    }

}
