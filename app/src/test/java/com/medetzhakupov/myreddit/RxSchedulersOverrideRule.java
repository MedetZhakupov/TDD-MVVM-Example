package com.medetzhakupov.myreddit;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.functions.Function;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Medet Zhakupov.
 * <p>
 * Scheduler test rule to run Rx immediately
 */
public class RxSchedulersOverrideRule implements TestRule {
    private static final Function<Scheduler, Scheduler> INSTANT_SCHEDULER_HANDLER = scheduler -> Schedulers.trampoline();

    @Override
    public Statement apply(final Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                RxAndroidPlugins.reset();
                RxAndroidPlugins.setMainThreadSchedulerHandler(INSTANT_SCHEDULER_HANDLER);

                RxJavaPlugins.reset();
                RxJavaPlugins.setIoSchedulerHandler(INSTANT_SCHEDULER_HANDLER);
                RxJavaPlugins.setNewThreadSchedulerHandler(INSTANT_SCHEDULER_HANDLER);

                base.evaluate();

                RxAndroidPlugins.reset();
                RxJavaPlugins.reset();
            }
        };
    }
}
