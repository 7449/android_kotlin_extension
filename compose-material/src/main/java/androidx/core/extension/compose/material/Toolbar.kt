package androidx.core.extension.compose.material

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.extension.compose.colorPrimary
import androidx.core.extension.compose.shadowWithClipIntersect

@Composable
fun SimpleBackToolbar(
    title: String,
    back: () -> Unit,
) {
    SimpleToolbar(
        title = title,
        navigationIcon = singleNavArrowBack(back),
    )
}

@Composable
fun SimpleBackSearchToolbar(
    title: String,
    back: () -> Unit,
    search: () -> Unit,
) {
    SimpleToolbar(
        title = title,
        navigationIcon = singleNavArrowBack(back),
        actions = {
            SimpleIconButton(
                imageVector = Icons.Default.Search,
                onClick = search
            )
        }
    )
}

@Composable
fun SimpleToolbar(
    title: String,
    titleColor: Color = Color.White,
    bgColor: Color = colorPrimary,
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
        elevation = 10.dp,
        backgroundColor = bgColor,
        modifier = Modifier.shadowWithClipIntersect(10.dp)
    )
}

fun singleActionSearch(
    onClick: (() -> Unit)? = null,
): @Composable RowScope.() -> Unit {
    if (onClick == null) return {}
    return {
        SimpleIconButton(imageVector = Icons.Default.Search, onClick = onClick)
    }
}

fun singleNavArrowBack(
    onClick: (() -> Unit)? = null,
): @Composable (() -> Unit)? {
    if (onClick == null) return null
    return {
        SimpleIconButton(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack, onClick = onClick,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
        )
    }
}