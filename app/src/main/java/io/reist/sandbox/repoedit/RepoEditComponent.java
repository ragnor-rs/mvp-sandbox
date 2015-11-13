package io.reist.sandbox.repoedit;

import dagger.Subcomponent;
import io.reist.sandbox.repoedit.view.RepoEditFragment;

@Subcomponent
public interface RepoEditComponent {

    void inject(RepoEditFragment view);

}
