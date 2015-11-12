package io.reist.sandbox.editrepo.di;

import dagger.Subcomponent;
import io.reist.sandbox.editrepo.mvp.view.EditRepoFragment;

@Subcomponent
public interface EditRepoComponent {

    void inject(EditRepoFragment view);

}
