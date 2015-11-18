package io.reist.sandbox.app.view;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import io.reist.sandbox.R;
import io.reist.sandbox.core.view.BaseFragment;
import io.reist.sandbox.repolist.view.RepoListFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, android.app.FragmentManager.OnBackStackChangedListener {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fragmentManager = getFragmentManager();

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );

        drawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();
        drawerToggle.setToolbarNavigationClickListener(v -> fragmentManager.popBackStackImmediate());

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_camara);

        fragmentManager.addOnBackStackChangedListener(this);

        showFragment(new RepoListFragment(), false);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (fragmentManager.getBackStackEntryCount() > 1) {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_camara:
                showFragment(new RepoListFragment(), true, true);
                break;
            case R.id.nav_gallery:
                showFragment(new TestFragment(), true, true);
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * @param fragment - fragment to display
     * @param remove   - boolean, stays for whether current fragment should be thrown away or stay in a back stack.
     *                 false to stay in a back stack
     */
    public void showFragment(BaseFragment fragment, boolean remove) {
        showFragment(fragment, remove, false);
    }

    private void showFragment(BaseFragment fragment, boolean remove, boolean popBackStackInclusive) {
        Fragment topmostFragment = findTopmostFragment(fragmentManager);
        if (topmostFragment != null && fragment.getName().equals(topmostFragment.getTag())) {
            return;
        }
        replace(fragmentManager, topmostFragment, fragment, remove, popBackStackInclusive);
    }

    private static void replace(FragmentManager fragmentManager, Fragment what, BaseFragment with, boolean remove, boolean popBackStackInclusive) {
        if (popBackStackInclusive && fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStackImmediate(fragmentManager.getBackStackEntryAt(0).getName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }

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

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onBackStackChanged() {
        boolean showBurger = fragmentManager.getBackStackEntryCount() == 1;
        drawerToggle.setDrawerIndicatorEnabled(showBurger);
        getSupportActionBar().setDisplayHomeAsUpEnabled(!showBurger);
        drawerToggle.syncState();
    }

}
