package androidx.core.extension.compose

import android.os.Handler
import android.os.Looper
import androidx.compose.ui.graphics.Color

internal val composeHandler = Handler(Looper.getMainLooper())

var colorPrimary = Color(android.graphics.Color.parseColor("#009688"))
