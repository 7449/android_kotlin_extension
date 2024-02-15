package androidx.core.extension.compose.widget

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.extension.compose.colorPrimary

@Composable
fun SimpleToolbar(
    title: String,
    titleColor: Color = Color.White,
    bgColor: Color = colorPrimary,
    shadow: Dp = 6.dp,
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
) {
    TopAppBar(
        navigationIcon = navigationIcon,
        actions = actions,
        title = {
            Text(
                text = title,
                color = titleColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        backgroundColor = bgColor,
        modifier = Modifier.shadow(shadow)
    )
}