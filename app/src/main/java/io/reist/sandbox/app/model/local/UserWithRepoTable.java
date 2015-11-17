package io.reist.sandbox.app.model.local;

import io.reist.sandbox.core.model.local.BaseTable;

/**
 * Created by m039 on 11/16/15.
 */
public class UserWithRepoTable extends BaseTable {

    public static final String NAME = "users_with_repo";

    public interface Column {
        String USER_ID = "user_id";
        String REPO_ID = "repo_id";
        String LIKED_BY_ME = "liked_by_me";
    }

    private static final String CREATE_TABLE = "CREATE TABLE " + NAME + " (" +
            Column.USER_ID + " text not null, " +
            Column.REPO_ID + " integer not null, " +
            Column.LIKED_BY_ME + " integer default 0, " +
            "primary key (" + Column.USER_ID + "," + Column.REPO_ID + ")" +
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
