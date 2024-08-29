package androidx.core.extension.compose

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.compose.ui.graphics.Color

const val DOWNLOAD = "下载"
const val COPY = "复制"

const val DATA_EMPTY = "数据为空"
const val DATA_FAILURE = "网络请求出错啦"
const val DATA_MORE_EMPTY = "没有更多的数据啦"

internal val composeHandler = Handler(Looper.getMainLooper())

fun composeHandlerPost(runnable: Runnable) {
    composeHandler.post(runnable)
}

var colorPrimary = Color(android.graphics.Color.parseColor("#009688"))

fun Context.composeAct(): ComponentActivity {
    return findActivity() as ComponentActivity
}

private fun Context?.findActivity(): Activity? {
    if (this == null) return null
    if (this is Activity) return this
    if (this is ContextWrapper) return baseContext.findActivity()
    return null
}
