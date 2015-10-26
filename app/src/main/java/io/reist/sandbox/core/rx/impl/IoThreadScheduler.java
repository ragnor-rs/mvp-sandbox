package io.reist.sandbox.core.rx.impl;

import io.reist.sandbox.core.rx.Scheduler;

/**
 * Created by Reist on 10/26/15.
 */
public class IoThreadScheduler extends Scheduler {

    private final Worker worker;

    public IoThreadScheduler() {
        this.worker = register(new NewThreadScheduler.NewThreadWorker(this));
    }

    @Override
    public Worker createWorker() {
        return worker;
    }

}
