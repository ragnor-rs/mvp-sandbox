package io.reist.sandbox.test.users.view;

import android.app.Activity;
import android.os.Bundle;

import io.reist.sandbox.users.view.UserListFragment;

/**
 * Created by m039 on 11/25/15.
 */
public class UserListActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            getFragmentManager()
                    .beginTransaction()
                    .add(android.R.id.content, UserListFragment.newInstance())
                    .commit();
        }
    }

}