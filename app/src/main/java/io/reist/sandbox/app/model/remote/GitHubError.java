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

package io.reist.sandbox.app.model.remote;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import io.reist.visum.model.Error;

/**
 * Created by m039 on 11/26/15.
 */
public class GitHubError implements Error {

    @SerializedName("message")
    private String message;

    @Nullable
    @Override
    public Throwable getThrowable() {
        return null;
    }

    @Nullable
    @Override
    public String getMessage() {
        return message;
    }
}
