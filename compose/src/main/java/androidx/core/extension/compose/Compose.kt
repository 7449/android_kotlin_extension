package androidx.core.extension.compose

import android.os.Handler
import android.os.Looper
import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt

const val DOWNLOAD = "下载"
const val COPY = "复制"

const val DATA_EMPTY = "数据为空"
const val DATA_FAILURE = "网络请求出错啦"
const val DATA_MORE_EMPTY = "没有更多的数据啦"

internal const val DefaultUA =
    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36"

internal val composeHandler = Handler(Looper.getMainLooper())

fun composeHandlerPost(runnable: Runnable) {
    composeHandler.post(runnable)
}

var colorPrimary = Color("#009688".toColorInt())