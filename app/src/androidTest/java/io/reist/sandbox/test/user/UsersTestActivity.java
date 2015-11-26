package io.reist.sandbox.test.user;

import android.app.Activity;
import android.os.Bundle;

import io.reist.sandbox.user.view.UsersFragment;

/**
 * Created by m039 on 11/25/15.
 */
public class UsersTestActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            getFragmentManager()
                    .beginTransaction()
                    .add(android.R.id.content, UsersFragment.newInstance())
                    .commit();
        }
    }
}