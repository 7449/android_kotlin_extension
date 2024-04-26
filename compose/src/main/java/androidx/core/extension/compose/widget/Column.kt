package androidx.core.extension.compose.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp

@Composable
fun ColumnSmallText(
    item: List<String>,
    modifier: Modifier = Modifier,
    color: Color = Color.Gray,
    size: Int = 10,
    includeFontPadding: Boolean = false,
    showIndex: Boolean = false,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
) {
    if (item.isEmpty()) return
    Column(
        modifier = modifier,
        horizontalAlignment = horizontalAlignment,
        verticalArrangement = verticalArrangement
    ) {
        item.forEachIndexed { index, s ->
            Text(
                if (showIndex) "%s：%s".format(index, s) else s,
                style = TextStyle(
                    fontSize = size.sp,
                    color = color,
                    platformStyle = PlatformTextStyle(includeFontPadding = includeFontPadding)
                ),
            )
        }
    }
}