package androidx.core.extension.util

import androidx.core.text.TextUtilsCompat
import androidx.core.view.ViewCompat
import java.util.Locale

val isRtl: Boolean
    get() = TextUtilsCompat.getLayoutDirectionFromLocale(Locale.getDefault()) == ViewCompat.LAYOUT_DIRECTION_RTL

inline fun <T> catchOrNull(action: () -> T) = runCatching { action.invoke() }.getOrNull()