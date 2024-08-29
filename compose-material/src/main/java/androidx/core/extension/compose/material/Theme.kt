package androidx.core.extension.compose.material

import android.app.Activity
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.extension.compose.colorPrimary

fun ComponentActivity.setMaterialThemeContent(content: @Composable () -> Unit) {
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