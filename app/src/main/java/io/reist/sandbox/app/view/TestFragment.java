package io.reist.sandbox.app.view;

import io.reist.sandbox.R;
import io.reist.sandbox.core.presenter.BasePresenter;
import io.reist.sandbox.core.view.BaseFragment;

/**
 * Created by defuera on 18/11/2015.
 */
public class TestFragment extends BaseFragment {

    public TestFragment() {
        super(R.layout.fragment_test);
    }

    @Override
    protected void inject(Object from) {

    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }
}
