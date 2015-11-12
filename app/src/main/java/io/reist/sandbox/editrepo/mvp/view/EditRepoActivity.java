package io.reist.sandbox.editrepo.mvp.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import io.reist.sandbox.R;
import io.reist.sandbox.core.mvp.view.BaseFragment;
import io.reist.sandbox.editrepo.mvp.presenter.EditRepoPresenter;

/**
 * Created by defuera on 12/11/2015.
 */
public class EditRepoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditRepoFragment fragment = new EditRepoFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(EditRepoPresenter.EXTRA_REPO_ID, getIntent().getLongExtra(EditRepoPresenter.EXTRA_REPO_ID, -1));
        fragment.setArguments(bundle);
        showFragment(fragment, false);
    }

    /**
     * @param fragment - fragment to display
     * @param remove   - boolean, stays for whether current fragment should be thrown away or stay in a back stack.
     *                 false to stay in a back stack
     */
    public void showFragment(BaseFragment fragment, boolean remove) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment topmostFragment = findTopmostFragment(fragmentManager);
        if (topmostFragment != null && fragment.getName().equals(topmostFragment.getTag())) {
            return;
        }
        replace(fragmentManager, topmostFragment, fragment, remove);
    }

    private static void replace(FragmentManager fragmentManager, Fragment what, BaseFragment with, boolean remove) {

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        if (what != null) {
            if (remove) {
                transaction.remove(what);
            } else {
                transaction.hide(what);
            }
        }

        String fragmentName = with.getName();

        if (with.isAdded()) {
            transaction.show(with);
        } else {

            transaction.add(R.id.fragment_container, with, fragmentName);

            List<Fragment> fragments = fragmentManager.getFragments();
            with.setFragmentIndex(fragments == null ? 0 : fragments.size());

        }

        transaction.show(with).addToBackStack(fragmentName).commit();

    }

    @Nullable
    private static Fragment findTopmostFragment(FragmentManager fragmentManager) {
        int backStackEntryCount = fragmentManager.getBackStackEntryCount();
        Fragment topmostFragment;
        if (backStackEntryCount > 0) {
            String fragmentName = fragmentManager.getBackStackEntryAt(backStackEntryCount - 1).getName();
            topmostFragment = fragmentManager.findFragmentByTag(fragmentName);
        } else {
            topmostFragment = null;
        }
        return topmostFragment;
    }
}
