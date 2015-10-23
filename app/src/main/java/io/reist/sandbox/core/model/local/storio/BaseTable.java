package io.reist.sandbox.core.model.local.storio;

/**
 * Created by Reist on 10/23/15.
 */
public abstract class BaseTable {

    public abstract String getCreateTableQuery(int databaseVersion);

    public abstract String[] getUpgradeTableQueries(int oldVersion);

}
