package io.reist.sandbox.core.view;

import android.content.Context;

/**
 * Created by Reist on 10/15/15.
 */
public interface BaseView {

    Context context();

    Long getComponentId();

    void setComponentId(Long componentId);

    Object getComponent();

}
