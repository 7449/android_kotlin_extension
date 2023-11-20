package androidx.core.extension.util

import android.content.res.Resources
import android.util.TypedValue

private val metrics get() = Resources.getSystem().displayMetrics

fun Float.dp2pxInt(): Int = dp2px().toInt()

fun Float.sp2pxInt(): Int = sp2px().toInt()

fun Float.px2spInt(): Int = px2sp().toInt()

fun Float.px2dpInt(): Int = px2dp().toInt()

fun Float.dp2px(): Float =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, metrics)

fun Float.sp2px(): Float =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, this, metrics)

fun Float.px2sp(): Float =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, this, metrics)

fun Float.px2dp(): Float =
    this / metrics.density
