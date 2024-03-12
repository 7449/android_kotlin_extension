package androidx.core.extension.compose.widget

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
import androidx.compose.material.Card
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
    padding: Dp = 8.dp,
    elevation: Dp = 8.dp,
    bgColor: Color = Color.White,
    shape: Dp = 8.dp,
    content: @Composable RowScope.() -> Unit,
) {
    SimpleCard(padding, elevation, bgColor, shape) {
        Row(modifier, horizontalArrangement, verticalAlignment) { content() }
    }
}

@Composable
fun SimpleCardColumn(
    modifier: Modifier = Modifier,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    padding: Dp = 8.dp,
    elevation: Dp = 8.dp,
    bgColor: Color = Color.White,
    shape: Dp = 8.dp,
    content: @Composable ColumnScope.() -> Unit,
) {
    SimpleCard(padding, elevation, bgColor, shape) {
        Column(modifier, verticalArrangement, horizontalAlignment) { content() }
    }
}

@Composable
fun SimpleCardBox(
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.TopStart,
    propagateMinConstraints: Boolean = false,
    padding: Dp = 8.dp,
    elevation: Dp = 8.dp,
    bgColor: Color = Color.White,
    shape: Dp = 8.dp,
    content: @Composable BoxScope.() -> Unit,
) {
    SimpleCard(padding, elevation, bgColor, shape) {
        Box(modifier, contentAlignment, propagateMinConstraints) { content() }
    }
}

@Composable
fun SimpleCard(
    padding: Dp = 8.dp,
    elevation: Dp = 8.dp,
    bgColor: Color = Color.White,
    shape: Dp = 8.dp,
    content: @Composable () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(padding),
        elevation = elevation,
        backgroundColor = bgColor,
        shape = RoundedCornerShape(shape),
        content = content
    )
}