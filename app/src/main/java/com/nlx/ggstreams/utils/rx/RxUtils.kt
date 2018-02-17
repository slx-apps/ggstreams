package com.nlx.ggstreams.utils.rx

import io.reactivex.Scheduler

public interface RxUtils {
    val observeScheduler: Scheduler
    val subscribeScheduler: Scheduler
}