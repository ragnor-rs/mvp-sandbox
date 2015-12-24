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

package io.reist.sandbox.app;

import android.content.Context;

import io.reist.sandbox.repos.view.RepoEditFragment;
import io.reist.sandbox.repos.view.RepoListFragment;
import io.reist.sandbox.users.view.UserListFragment;
import io.reist.sandbox.users.view.UserReposFragment;
import io.reist.visum.BaseModule;
import io.reist.visum.ComponentCache;
import io.reist.visum.view.BaseView;

/**
 * Created by Reist on 29.11.15.
 */
public class SandboxComponentCache extends ComponentCache {

    private final SandboxComponent sandboxComponent;

    public SandboxComponentCache(Context context) {
        this(DaggerSandboxComponent.builder().baseModule(new BaseModule(context)).build());
    }

    public SandboxComponentCache(SandboxComponent sandboxComponent) {
        this.sandboxComponent = sandboxComponent;
    }

    @Override
    public Object buildComponentFor(Class<? extends BaseView> viewClass) {
        if (
                RepoListFragment.class.isAssignableFrom(viewClass) ||
                RepoEditFragment.class.isAssignableFrom(viewClass)
        ) {
            return sandboxComponent.reposComponent();
        } else if (
                UserListFragment.class.isAssignableFrom(viewClass) ||
                UserReposFragment.class.isAssignableFrom(viewClass)
        ) {
            return sandboxComponent.usersComponent();
        } else {
            throw new RuntimeException("Unknown view class: " + viewClass.getName());
        }
    }

}
