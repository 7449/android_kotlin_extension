package androidx.core.extension.compose.activity

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.extension.compose.colorPrimary

fun ComponentActivity.setThemeContent(content: @Composable () -> Unit) {
    setContent {
        val view = LocalView.current
        if (!view.isInEditMode) {
            SideEffect {
                val window = (view.context as Activity).window
                window.statusBarColor = colorPrimary.toArgb()
            }
        }
        MaterialTheme(
            content = content,
            colors = MaterialTheme.colors.copy(
                primary = colorPrimary,
                secondary = colorPrimary
            ),
        )
    }
}

fun Context.composeAct(): ComponentActivity {
    return findActivity() as ComponentActivity
}

private fun Context?.findActivity(): Activity? {
    if (this == null) return null
    if (this is Activity) return this
    if (this is ContextWrapper) return baseContext.findActivity()
    return null
}