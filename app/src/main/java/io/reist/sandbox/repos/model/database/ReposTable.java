package io.reist.sandbox.repos.model.database;

/**
 * Created by Reist on 10/16/15.
 */
public class ReposTable {

    public static final String TABLE_NAME = "repos";

    public static final String TEMPORARY_TABLE_NAME = "tmp_" + TABLE_NAME;

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_URL = "url";
    public static final String COLUMN_AUTHOR = "author";

    public static String getCreateTableQuery(int databaseVersion) {
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

    public static String[] getUpgradeTableQueries(int oldVersion) {
        switch (oldVersion) {

            case 1:
                return new String[] {

                        "create table " + TEMPORARY_TABLE_NAME + "(" +
                                COLUMN_ID + " integer not null primary key, " +
                                COLUMN_NAME + " text not null, " +
                                COLUMN_URL + " text, " +
                                COLUMN_AUTHOR + " text" +
                        ")",

                        "insert into " +
                                TEMPORARY_TABLE_NAME + " " +
                        "select " +
                                COLUMN_ID + ", " +
                                COLUMN_NAME + ", " +
                                COLUMN_URL + ", " +
                                "\"JakeWharton\" " +
                        "from " +
                                TABLE_NAME,

                        "drop table " +
                                TABLE_NAME,

                        getCreateTableQuery(2),

                        "insert into " +
                                TABLE_NAME + " " +
                        "select " +
                                COLUMN_ID + ", " +
                                COLUMN_NAME + ", " +
                                COLUMN_URL + ", " +
                                COLUMN_AUTHOR + " " +
                        "from " +
                                TEMPORARY_TABLE_NAME,

                        "drop table " +
                                TEMPORARY_TABLE_NAME

                };

            default:
                return new String[0];

        }
    }

}
