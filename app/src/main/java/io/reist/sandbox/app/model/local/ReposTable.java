/*
 * Copyright (c) 2015  Zvooq LTD.
 * Authors: Renat Sarymsakov, Dmitriy Mozgin, Denis Volyntsev.
 *
 * This file is part of MVP-Sandbox.
 *
 * MVP-Sandbox is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MVP-Sandbox is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with MVP-Sandbox.  If not, see <http://www.gnu.org/licenses/>.
 */

package io.reist.sandbox.app.model.local;

import io.reist.visum.model.local.BaseTable;

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