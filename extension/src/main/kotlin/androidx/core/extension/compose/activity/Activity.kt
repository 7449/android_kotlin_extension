package androidx.core.extension.compose.activity

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.core.extension.compose.colorPrimary

fun ComponentActivity.setThemeContent(content: @Composable () -> Unit) {
    setContent {
        MaterialTheme(
            content = content,
            colors = MaterialTheme.colors.copy(
                primary = colorPrimary,
                secondary = colorPrimary
            ),
        )
    }
}