package com.nlx.ggstreams.utils.rx

import io.reactivex.Scheduler


class BaseRxUtils(override val observeScheduler: Scheduler,
                  override val subscribeScheduler: Scheduler) : RxUtils