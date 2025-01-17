package androidx.core.extension.compose.material3

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.core.extension.compose.colorPrimary

fun ComponentActivity.setMaterial3ThemeContent(content: @Composable () -> Unit) {
    setContent {
        MaterialTheme(
            content = content,
            colorScheme = MaterialTheme.colorScheme.copy(
                primary = colorPrimary,
                secondary = colorPrimary
            ),
        )
    }
}