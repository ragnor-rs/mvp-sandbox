package io.reist.sandbox.repo;

import javax.inject.Singleton;

import dagger.Subcomponent;
import io.reist.sandbox.repo.view.RepoEditFragment;

@Singleton
@Subcomponent
public interface RepoEditComponent {

    void inject(RepoEditFragment view);

}
