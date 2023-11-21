package androidx.core.extension.text

import android.util.Log

var logTag = "Print"
var isPrint = true

fun logI(value: Any) {
    if (!isPrint) return
    Log.i(logTag, value.toString())
}

fun logE(value: Any) {
    if (!isPrint) return
    Log.e(logTag, value.toString())
}

fun logW(value: Any) {
    if (!isPrint) return
    Log.w(logTag, value.toString())
}

fun logV(value: Any) {
    if (!isPrint) return
    Log.v(logTag, value.toString())
}

fun logD(value: Any) {
    if (!isPrint) return
    Log.d(logTag, value.toString())
}