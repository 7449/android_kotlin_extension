package androidx.core.extension.compose.widget

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

fun textStyle(includeFontPadding: Boolean = true): TextStyle {
    return TextStyle(
        platformStyle = PlatformTextStyle(
            includeFontPadding = includeFontPadding
        )
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun <T> LabelText(
    modifier: Modifier = Modifier,
    prefix: String = "",
    prefixColor: Color = Color.Black,
    label: List<T> = arrayListOf(),
    labelColor: Color = Color.Red,
    fontSize: Int = 14,
    onTextValue: (T) -> String,
    onClick: (T) -> Unit,
) {
    if (label.isEmpty()) return
    Row(modifier = Modifier.then(modifier)) {
        if (prefix.isNotBlank()) {
            Text(
                text = prefix,
                color = prefixColor,
                fontSize = fontSize.sp,
            )
        }
        FlowRow {
            label.forEach {
                Text(
                    text = onTextValue(it),
                    color = labelColor,
                    fontSize = fontSize.sp,
                    modifier = Modifier
                        .clickable { onClick(it) }
                        .padding(start = (if (prefix.isNotBlank()) 2 else 0).dp)
                )
            }
        }
    }
}