package io.reist.sandbox.app.model.local;

import io.reist.sandbox.core.model.local.BaseTable;

/**
 * Created by m039 on 11/12/15.
 */
public class UserTable extends BaseTable {

    public static final String NAME = "user";

    public interface Column extends BaseTable.Column {
        String NAME = "name";
        String LOGIN = "login";
    }

    private static final String CREATE_TABLE = "create table " + NAME + "(" +
            Column.ID + " text not null primary key, " +
            Column.NAME + " text, " +
            Column.LOGIN + " text" +
            ")";

    @Override
    public String[] getUpgradeTableQueries(int oldVersion) {
        switch (oldVersion) {
            case 2:
                return new String[] { CREATE_TABLE };
            default:
                return null;
        }
    }

    @Override
    public String getCreateTableQuery() {
        return CREATE_TABLE;
    }
}
