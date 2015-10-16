package io.reist.sandbox.core;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import io.reist.sandbox.core.view.BaseView;

public class ComponentCache {

    private final AtomicLong idSequence = new AtomicLong();

    private final Map<Long, Object> componentMap = new HashMap<>();

    private final BaseApplication baseApplication;

    public ComponentCache(BaseApplication baseApplication) {
        this.baseApplication = baseApplication;
    }

    public Object getComponentFor(BaseView view) {

        Long componentId = view.getComponentId();

        if (componentId == null) {
            componentId = idSequence.incrementAndGet();
            view.setComponentId(componentId);
        }

        Object component = componentMap.get(componentId);

        if (component == null) {
            component = baseApplication.buildComponentFor(view);
            componentMap.put(componentId, component);
        }

        return component;

    }

    public void invalidateComponentFor(BaseView view) {
        Long componentId = view.getComponentId();
        if (componentId == null) {
            return;
        }
        componentMap.remove(componentId);
    }

}
