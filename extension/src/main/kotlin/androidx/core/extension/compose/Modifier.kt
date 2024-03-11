package androidx.core.extension.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
@Stable
fun Modifier.noRippleClick(
    height: Dp = 46.dp,
    onClick: () -> Unit,
) = then(
    fillMaxSize().height(height).wrapContentHeight().clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = null,
        onClick = onClick
    )
)

@Stable
fun Modifier.square(
    square: Dp,
) = then(
    height(square).width(square)
)

@Stable
fun Modifier.horizontalPadding(
    padding: Dp,
) = then(
    padding(horizontal = padding)
)

@Stable
fun Modifier.verticalPadding(
    padding: Dp,
) = then(
    padding(vertical = padding)
)

@Stable
fun Modifier.fillMaxWidthPadding(
    padding: Int = 2,
) = then(
    padding(padding.dp).fillMaxWidth()
)

@Stable
fun Modifier.fillMaxHeightPadding(
    padding: Int = 2,
) = then(
    padding(padding.dp).fillMaxHeight()
)

@Stable
fun Modifier.squarePadding(
    padding: Int = 2,
    square: Dp,
) = then(
    padding(padding.dp).square(square)
)

