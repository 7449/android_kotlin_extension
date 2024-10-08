package androidx.core.extension.compose.material

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.extension.compose.horizontalPadding

@Composable
fun RowScope.WeightButton(
    text: String,
    size: Int = 12,
    color: Color = Color.White,
    onClick: () -> Unit = {},
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .weight(1f)
            .horizontalPadding(2.dp),
    ) {
        Text(text, fontSize = size.sp, color = color)
    }
}