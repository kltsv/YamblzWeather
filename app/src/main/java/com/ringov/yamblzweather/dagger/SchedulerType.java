package com.ringov.yamblzweather.dagger;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface SchedulerType {

    SchedulerEnum scheduler() default SchedulerEnum.Main;
}
