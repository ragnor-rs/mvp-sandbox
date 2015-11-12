package io.reist.sandbox.editrepo.di;

import dagger.Subcomponent;
import io.reist.sandbox.editrepo.mvp.view.RepoEditFragment;

@Subcomponent
public interface EditRepoComponent {

    void inject(RepoEditFragment view);

}
