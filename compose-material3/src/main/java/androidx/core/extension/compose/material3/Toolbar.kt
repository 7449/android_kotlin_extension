@file:OptIn(ExperimentalMaterial3Api::class)

package androidx.core.extension.compose.material3

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.extension.compose.colorPrimary

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
        actions = singleActionSearch(onClick = search)
    )
}

@Composable
fun SimpleToolbar(
    title: String,
    titleColor: Color = Color.White,
    shadow: Dp = 6.dp,
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(containerColor = colorPrimary),
    navigationIcon: @Composable () -> Unit = {},
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
        colors = colors,
        modifier = Modifier.shadow(shadow)
    )
}

fun singleActionSearch(
    onClick: (() -> Unit)? = null,
): @Composable RowScope.() -> Unit {
    if (onClick == null) return {}
    return {
        SimpleIconButton(
            imageVector = Icons.Default.Search,
            onClick = onClick
        )
    }
}

fun singleNavArrowBack(
    onClick: (() -> Unit)? = null,
): @Composable () -> Unit {
    if (onClick == null) return { }
    return {
        SimpleIconButton(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            onClick = onClick
        )
    }
}