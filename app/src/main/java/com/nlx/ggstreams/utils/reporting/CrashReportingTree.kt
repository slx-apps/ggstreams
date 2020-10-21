package com.nlx.ggstreams.utils.reporting

import android.util.Log
import timber.log.Timber

class CrashReportingTree : Timber.Tree() {

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (priority == Log.VERBOSE || priority == Log.DEBUG) {
            return;
        }

        if (t != null) {
            if (priority == Log.ERROR) {
                //
            } else if (priority == Log.WARN) {
                //
            }
        }
    }
}