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

package io.reist.sandbox.users.model.remote;

import java.util.List;

import io.reist.sandbox.app.model.User;
import io.reist.sandbox.app.model.remote.SandboxApi;
import io.reist.sandbox.users.model.UserService;
import io.reist.visum.model.VisumResponse;
import io.reist.visum.model.remote.RetrofitService;
import rx.Observable;

/**
 * Created by m039 on 11/12/15.
 */
public class RetrofitUserService extends RetrofitService<User> implements UserService {

    protected final SandboxApi sandboxApi;

    public RetrofitUserService(SandboxApi sandboxApi) {
        this.sandboxApi = sandboxApi;
    }

    @Override
    public Observable<? extends VisumResponse<List<User>>> list() {
        return sandboxApi.listUsers();
    }

    @Override
    public VisumResponse<User> saveSync(User user) {
        throw new IllegalStateException("Unsupported");
    }

    @Override
    public Observable<VisumResponse<User>> byId(Long id) {
        throw new IllegalStateException("Unsupported");
    }

    @Override
    public Observable<VisumResponse<Integer>> delete(Long id) {
        throw new IllegalStateException("Unsupported");
    }

    @Override
    public VisumResponse<List<User>> saveSync(List<User> list) {
        throw new IllegalStateException("Unsupported");
    }

}
