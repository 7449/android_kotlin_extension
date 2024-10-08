package androidx.core.extension.compose

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
@Stable
fun Modifier.noRippleClickable(
    onClick: () -> Unit,
) = clickable(
    interactionSource = remember { MutableInteractionSource() },
    indication = null,
    onClick = onClick
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
@Stable
fun Modifier.noRippleCombinedClickable(
    onClick: () -> Unit,
    onLongClick: (() -> Unit)? = null,
    onDoubleClick: (() -> Unit)? = null,
) = combinedClickable(
    interactionSource = remember { MutableInteractionSource() },
    indication = null,
    onClick = onClick,
    onLongClick = onLongClick,
    onDoubleClick = onDoubleClick
)

@Stable
fun Modifier.horizontalPadding(
    padding: Dp,
) = padding(horizontal = padding)

@Stable
fun Modifier.verticalPadding(
    padding: Dp,
) = padding(vertical = padding)

@Stable
fun Modifier.paddingFillMaxWidth(
    padding: Int = 2,
) = padding(padding.dp).fillMaxWidth()

@Stable
fun Modifier.paddingFillMaxHeight(
    padding: Int = 2,
) = padding(padding.dp).fillMaxHeight()

@Stable
fun Modifier.paddingSquare(
    padding: Int = 2,
    square: Dp,
) = padding(padding.dp).size(square)
