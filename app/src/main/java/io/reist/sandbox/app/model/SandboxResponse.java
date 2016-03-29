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

package io.reist.sandbox.app.model;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import io.reist.visum.model.VisumResponse;

/**
 * Created by m039 on 11/26/15.
 */
public class SandboxResponse<T> implements VisumResponse<T> {

    @SerializedName("result")
    private T result;

    @SerializedName("error")
    private SandboxError error;

    public SandboxResponse(T result) {
        this.result = result;
    }

    public SandboxResponse(SandboxError error) {
        this.error = error;
    }

    @Nullable
    @Override
    public T getResult() {
        return result;
    }

    @Nullable
    @Override
    public SandboxError getError() {
        return error;
    }

    @Override
    public boolean isSuccessful() {
        return error == null;
    }

}
