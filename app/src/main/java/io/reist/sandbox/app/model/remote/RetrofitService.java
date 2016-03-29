/*
 * Copyright (c) 2015  Zvooq LTD.
 * Authors: Renat Sarymsakov, Dmitriy Mozgin, Denis Volyntsev.
 *
 * This file is part of Visum.
 *
 * Visum is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Visum is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Visum.  If not, see <http://www.gnu.org/licenses/>.
 */

package io.reist.sandbox.app.model.remote;

import io.reist.visum.model.VisumService;

/**
 * Created by Reist on 11/2/15.
 */
public abstract class RetrofitService<T> implements VisumService<T> {

    protected final SandboxApi sandboxApi;

    public RetrofitService(SandboxApi sandboxApi) {
        this.sandboxApi = sandboxApi;
    }

}
