package androidx.core.extension.compose.activity

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.core.extension.compose.ThemePrimary

fun ComponentActivity.setThemeContent(content: @Composable () -> Unit) {
    setContent { ThemePrimary(content = content) }
}