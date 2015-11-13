package io.reist.sandbox.repoedit.di;

import dagger.Subcomponent;
import io.reist.sandbox.repoedit.mvp.view.RepoEditFragment;

@Subcomponent
public interface RepoEditComponent {

    void inject(RepoEditFragment view);

}
