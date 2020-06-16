package com.blacklivesmatter.policebrutality.logging

import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import timber.log.Timber

/**
 * RELEASE logging tree that logs to [FirebaseCrashlytics].
 */
class TimberReleaseTree : Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, throwable: Throwable?) {
        if (priority == Log.VERBOSE) return

        FirebaseCrashlytics.getInstance().log(message)
        if (throwable != null) {
            // Notifies firebase about non fatal crashes
            // See "Configure email alerts" https://support.google.com/firebase/answer/7276542?hl=en
            FirebaseCrashlytics.getInstance().recordException(throwable)
        }
    }
}
