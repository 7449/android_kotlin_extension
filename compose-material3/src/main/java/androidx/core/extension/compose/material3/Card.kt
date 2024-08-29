package androidx.core.extension.compose.material3

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun SimpleCardRow(
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalAlignment: Alignment.Vertical = Alignment.Top,

    cardModifier: Modifier = Modifier,
    padding: Dp = 8.dp,
    roundedCorner: Dp = 8.dp,
    backgroundColor: Color = Color.White,
    elevation: Dp = 8.dp,

    content: @Composable RowScope.() -> Unit,
) {
    SimpleCard(
        modifier = cardModifier,
        padding = padding,
        roundedCorner = roundedCorner,
        backgroundColor = backgroundColor,
        elevation = elevation,
    ) {
        Row(modifier, horizontalArrangement, verticalAlignment) { content() }
    }
}

//如果不需要修改Column的配置,使用默认的SimpleCard即可
@Composable
fun SimpleCardColumn(
    modifier: Modifier = Modifier,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,

    cardModifier: Modifier = Modifier,
    padding: Dp = 8.dp,
    roundedCorner: Dp = 8.dp,
    backgroundColor: Color = Color.White,
    elevation: Dp = 8.dp,

    content: @Composable ColumnScope.() -> Unit,
) {
    SimpleCard(
        modifier = cardModifier,
        padding = padding,
        roundedCorner = roundedCorner,
        backgroundColor = backgroundColor,
        elevation = elevation,
    ) {
        Column(modifier, verticalArrangement, horizontalAlignment) { content() }
    }
}

@Composable
fun SimpleCardBox(
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.TopStart,
    propagateMinConstraints: Boolean = false,

    cardModifier: Modifier = Modifier,
    padding: Dp = 8.dp,
    roundedCorner: Dp = 8.dp,
    backgroundColor: Color = Color.White,
    elevation: Dp = 8.dp,

    content: @Composable BoxScope.() -> Unit,
) {
    SimpleCard(
        modifier = cardModifier,
        padding = padding,
        roundedCorner = roundedCorner,
        backgroundColor = backgroundColor,
        elevation = elevation,
    ) {
        Box(modifier, contentAlignment, propagateMinConstraints) { content() }
    }
}

@Composable
fun SimpleCard(
    modifier: Modifier = Modifier,
    padding: Dp = 10.dp,
    roundedCorner: Dp = 10.dp,
    backgroundColor: Color = Color.White,
    elevation: Dp = 10.dp,
    content: @Composable ColumnScope.() -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(padding)
            .then(modifier),
        elevation = CardDefaults.cardElevation(
            defaultElevation = elevation,
            pressedElevation = elevation,
            focusedElevation = elevation,
            hoveredElevation = elevation,
            draggedElevation = elevation,
            disabledElevation = elevation
        ),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        shape = RoundedCornerShape(roundedCorner),
        content = content
    )
}