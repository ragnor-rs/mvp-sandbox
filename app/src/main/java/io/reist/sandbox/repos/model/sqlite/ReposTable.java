package io.reist.sandbox.repos.model.sqlite;

/**
 * Created by Reist on 10/16/15.
 */
public class ReposTable {

    public static final String TABLE_NAME = "repos";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_URL = "url";

    public static String getCreateTableQuery(int databaseVersion) {
        switch (databaseVersion) {

            case 1:
                return
                        "create table " + TABLE_NAME + "(" +
                                COLUMN_ID + " integer not null primary key, " +
                                COLUMN_NAME + " text not null, " +
                                COLUMN_URL + " text" +
                        ")";

            default:
                throw new RuntimeException("Unknown database version: " + databaseVersion);

        }
    }

}
