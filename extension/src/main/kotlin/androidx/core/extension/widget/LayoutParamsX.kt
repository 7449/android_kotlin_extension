package androidx.core.extension.widget

import android.os.Build
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.core.extension.compatible.TYPE_SYSTEM_ALERT_COMPATIBLE

const val MATCH_PARENT = ViewGroup.LayoutParams.MATCH_PARENT
const val WRAP_CONTENT = ViewGroup.LayoutParams.WRAP_CONTENT

val matchViewGroupParams: ViewGroup.LayoutParams
    get() = groupParams(height = MATCH_PARENT)

fun groupParams(
    width: Int = LinearLayout.LayoutParams.MATCH_PARENT,
    height: Int = LinearLayout.LayoutParams.WRAP_CONTENT,
): ViewGroup.LayoutParams {
    return ViewGroup.LayoutParams(width, height)
}

val matchLinearParams: LinearLayout.LayoutParams
    get() = linearParams(height = LinearLayout.LayoutParams.MATCH_PARENT)

fun linearParams(
    width: Int = LinearLayout.LayoutParams.MATCH_PARENT,
    height: Int = LinearLayout.LayoutParams.WRAP_CONTENT,
): LinearLayout.LayoutParams {
    return LinearLayout.LayoutParams(width, height)
}

val matchFrameParams: FrameLayout.LayoutParams
    get() = frameParams(height = LinearLayout.LayoutParams.MATCH_PARENT)

fun frameParams(
    width: Int = FrameLayout.LayoutParams.MATCH_PARENT,
    height: Int = FrameLayout.LayoutParams.WRAP_CONTENT,
): FrameLayout.LayoutParams {
    return FrameLayout.LayoutParams(width, height)
}

fun WindowManager.LayoutParams.floatType() = also {
    type = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
    } else TYPE_SYSTEM_ALERT_COMPATIBLE
}