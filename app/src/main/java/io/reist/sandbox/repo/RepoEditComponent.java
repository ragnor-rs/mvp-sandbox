package io.reist.sandbox.repo;

import dagger.Subcomponent;
import io.reist.sandbox.repo.view.RepoEditFragment;

@Subcomponent
public interface RepoEditComponent {

    void inject(RepoEditFragment view);

}
