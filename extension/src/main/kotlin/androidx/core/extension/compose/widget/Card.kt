package androidx.core.extension.compose.widget

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

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