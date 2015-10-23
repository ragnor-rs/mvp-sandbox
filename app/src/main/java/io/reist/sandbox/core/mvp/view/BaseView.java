package io.reist.sandbox.core.mvp.view;

import android.content.Context;

/**
 * Created by Reist on 10/15/15.
 */
public interface BaseView {

    Context getContext();

    Long getComponentId();

    void setComponentId(Long componentId);

    Object getComponent();

}
