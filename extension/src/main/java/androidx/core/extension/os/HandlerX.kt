package androidx.core.extension.os

import android.os.Handler
import android.os.Looper

val mainHandler = Handler(Looper.getMainLooper())

val isMainThread get() = Looper.getMainLooper() == Looper.myLooper()

fun postMainThread(runnable: Runnable) {
    if (isMainThread) {
        runnable.run()
    } else {
        mainHandler.post(runnable)
    }
}