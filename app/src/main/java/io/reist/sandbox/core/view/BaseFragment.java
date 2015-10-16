package io.reist.sandbox.core.view;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.reist.sandbox.R;
import io.reist.sandbox.core.BaseApplication;
import io.reist.sandbox.core.ComponentCache;
import io.reist.sandbox.core.presenter.BasePresenter;

/**
 * Created by Reist on 10/13/15.
 */
public abstract class BaseFragment<P extends BasePresenter> extends Fragment implements BaseView {

    public static final int PERMISSION_REQUEST_CODE_GROUP = 0xab;

    private static final String STATE_COMPONENT_ID = "STATE_COMPONENT_ID";

    private Runnable runnable;

    private int fragmentIndex;

    private Long componentId;

    private boolean stateSaved;

    public final String getName() {
        return getClass().getName();
    }
    
    public final void runPrivileged(Runnable runnable, String... permissions) {

        Activity activity = getActivity();

        List<String> deniedPermissions = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                deniedPermissions.add(permission);
            }
        }

        if (deniedPermissions.isEmpty()) {
            runnable.run();
        } else {

            this.runnable = runnable;

            String[] permissionArray = new String[deniedPermissions.size()];
            for (int i = 0; i < permissionArray.length; i++) {
                permissionArray[i] = deniedPermissions.get(i);
            }

            ActivityCompat.requestPermissions(
                    activity,
                    permissionArray,
                    getPermissionRequestCode()
            );

        }

    }

    private int getPermissionRequestCode() {
        return PERMISSION_REQUEST_CODE_GROUP | (fragmentIndex + 1 << 8);
    }

    @Override
    public final void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode != PERMISSION_REQUEST_CODE_GROUP) {
            return;
        }

        boolean everythingGranted = true;
        for (int grantResult : grantResults) {
            everythingGranted &= grantResult == PackageManager.PERMISSION_GRANTED;
        }

        if (everythingGranted) {
            runnable.run();
            runnable = null;
        } else {
            Toast.makeText(getActivity(), R.string.permissions_required, Toast.LENGTH_LONG).show();
        }

    }

    public final void setFragmentIndex(int fragmentIndex) {
        this.fragmentIndex = fragmentIndex;
    }

    /// --- ///

    @Override
    public final Long getComponentId() {
        return componentId;
    }

    @Override
    public final void setComponentId(Long componentId) {
        this.componentId = componentId;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        componentId = savedInstanceState == null ? null : savedInstanceState.getLong(STATE_COMPONENT_ID);
        stateSaved = false;
        Object component = getComponentCache().getComponentFor(this);
        inject(component);
        attach();
    }

    private ComponentCache getComponentCache() {
        BaseApplication application = (BaseApplication) getActivity().getApplication();
        return application.getComponentCache();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(STATE_COMPONENT_ID, componentId);
        stateSaved = true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (!stateSaved) {
            getComponentCache().invalidateComponentFor(this);
        }
        detach();
    }

    protected abstract void inject(Object component);

    protected abstract void attach();

    protected abstract void detach();

}
