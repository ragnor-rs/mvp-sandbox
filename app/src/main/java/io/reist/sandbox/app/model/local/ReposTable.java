package io.reist.sandbox.app.model.local;

import io.reist.sandbox.core.model.local.BaseTable;

/**
 * Created by Reist on 10/16/15.
 */
public class ReposTable extends BaseTable {

    public static final String NAME = "repos";

    public interface Column extends BaseTable.Column {
        String NAME = "name";
        String URL = "url";
        String LIKE_COUNT = "like_count";
        String USER_ID = "user_id";
        String LIKED_BY_ME = "liked_by_me";
    }

    private static final String CREATE_TABLE = "create table " + NAME + "(" +
            Column.ID + " integer not null primary key, " +
            Column.REVISION + " integer," +
            Column.NAME + " text, " +
            Column.URL + " text, " +
            Column.LIKE_COUNT + " integer, " +
            Column.USER_ID + " integer not null, " +
            Column.LIKED_BY_ME + " integer, " +
            "FOREIGN KEY (" + Column.USER_ID + ") " +
            "REFERENCES " + UserTable.NAME + "(" + UserTable.Column.ID + ")" +
            ")";

    @Override
    public String getCreateTableQuery() {
        return CREATE_TABLE;
    }

    @Override
    public String[] getUpgradeTableQueries(int oldVersion) {
        switch (oldVersion) {

            case 2:
                return new String[] {
                        "drop table " + NAME,
                        CREATE_TABLE
                };

            case 1:
                return new String[] {

                        "alter table " +
                                NAME + " " +
                                "add column author text " +
                                "default " +
                                "\"JakeWharton\""

                };

            default:
                return new String[0];

        }
    }

}