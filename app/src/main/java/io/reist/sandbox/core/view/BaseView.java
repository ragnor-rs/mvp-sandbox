package io.reist.sandbox.core.view;

import android.content.Context;
import android.os.Bundle;

/**
 * Created by Reist on 10/15/15.
 */
public interface BaseView {

    Bundle getExtras();

    Context getContext();

    Long getComponentId();

    void setComponentId(Long componentId);

    Object getComponent();

}
