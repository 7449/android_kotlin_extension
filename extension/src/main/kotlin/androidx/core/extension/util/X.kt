package androidx.core.extension.util

import android.view.View
import androidx.core.text.TextUtilsCompat
import java.util.Locale

val isRtl: Boolean
    get() = TextUtilsCompat.getLayoutDirectionFromLocale(Locale.getDefault()) == View.LAYOUT_DIRECTION_RTL

inline fun <T> catchOrNull(action: () -> T) = runCatching { action.invoke() }.getOrNull()