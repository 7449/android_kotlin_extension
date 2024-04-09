package androidx.core.extension.util

import android.app.Activity
import android.app.Application.ActivityLifecycleCallbacks
import android.os.Bundle
import androidx.core.extension.text.logE

object LogLifecycle : ActivityLifecycleCallbacks {
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        logE("$activity onActivityCreated")
    }

    override fun onActivityStarted(activity: Activity) {
        logE("$activity onActivityStarted")
    }

    override fun onActivityResumed(activity: Activity) {
        logE("$activity onActivityResumed")
    }

    override fun onActivityPaused(activity: Activity) {
        logE("$activity onActivityPaused")
    }

    override fun onActivityStopped(activity: Activity) {
        logE("$activity onActivityStopped")
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        logE("$activity onActivitySaveInstanceState")
    }

    override fun onActivityDestroyed(activity: Activity) {
        logE("$activity onActivityDestroyed")
    }
}