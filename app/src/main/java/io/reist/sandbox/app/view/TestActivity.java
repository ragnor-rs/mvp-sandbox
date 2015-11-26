package io.reist.sandbox.app.view;

import android.app.Activity;
import android.os.Bundle;

import io.reist.sandbox.user.view.UsersFragment;

/**
 * Created by m039 on 11/25/15.
 */
public class TestActivity extends Activity {

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
