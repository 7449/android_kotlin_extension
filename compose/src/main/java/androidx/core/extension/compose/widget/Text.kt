package androidx.core.extension.compose.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle

fun textStyle(includeFontPadding: Boolean = true): TextStyle {
    return TextStyle(
        platformStyle = PlatformTextStyle(includeFontPadding = includeFontPadding)
    )
}

fun buildAnnotatedStringValue(
    prefix: String = "",
    prefixColor: Color = Color.Black,
    text: String,
    textColor: Color = Color.Red,
): AnnotatedString = buildAnnotatedString {
    withStyle(style = SpanStyle(color = prefixColor)) {
        append(prefix)
    }
    withStyle(style = SpanStyle(color = textColor)) {
        append(text)
    }
}

@Composable
fun ColumnSmallTextContainer(
    item: List<String>,
    modifier: Modifier = Modifier,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    content: @Composable (Int, String) -> Unit,
) {
    if (item.isEmpty()) return
    Column(
        modifier = modifier,
        horizontalAlignment = horizontalAlignment,
        verticalArrangement = verticalArrangement
    ) { item.forEachIndexed { index, value -> content(index, value) } }
}