package io.reist.sandbox.app.model.local;

import io.reist.sandbox.core.model.local.BaseTable;

/**
 * Created by m039 on 11/16/15.
 */
public class LikeTable extends BaseTable {

    public static final String NAME = "like";

    public interface Column {
        String REPO_ID = "repo_id";
        String LIKED_BY_ME = "liked_by_me";
    }

    private static final String CREATE_TABLE = "CREATE TABLE " + NAME + " (" +
            Column.REPO_ID + " integer not null primary key, " +
            Column.LIKED_BY_ME + " integer default 0 " +
            ")";

    @Override
    public String getCreateTableQuery() {
        return CREATE_TABLE;
    }

    @Override
    public String[] getUpgradeTableQueries(int oldVersion) {
        switch (oldVersion) {
            case 2:
                return new String[] { CREATE_TABLE };
            default:
                return null;
        }
    }
}
