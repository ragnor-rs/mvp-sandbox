package io.reist.sandbox.core.view;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import io.reist.sandbox.R;

/**
 * Created by m039 on 11/13/15.
 */
public class BaseActivity extends AppCompatActivity
        implements BaseFragment.FragmentController {

    @Override
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
