package androidx.core.extension.compose.widget

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.extension.compose.colorPrimary
import androidx.core.extension.compose.screen

@Composable
inline fun ScaffoldBox(
    statusBarColor: Color = colorPrimary,
    contentColor: Color = Color.White,
    content: @Composable BoxScope.() -> Unit,
) {
    Box(
        modifier = Modifier.screen(statusBarColor, contentColor),
        content = content
    )
}

@Composable
inline fun ScaffoldColumn(
    statusBarColor: Color = colorPrimary,
    contentColor: Color = Color.White,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = Modifier.screen(statusBarColor, contentColor),
        content = content
    )
}

@Composable
inline fun ScaffoldRow(
    statusBarColor: Color = colorPrimary,
    contentColor: Color = Color.White,
    content: @Composable RowScope.() -> Unit,
) {
    Row(
        modifier = Modifier.screen(statusBarColor, contentColor),
        content = content
    )
}