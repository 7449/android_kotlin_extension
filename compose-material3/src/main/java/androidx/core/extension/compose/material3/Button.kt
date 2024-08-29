package androidx.core.extension.compose.material3

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign

@Composable
fun SimpleText(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Center,
) {
    Text(
        text = text,
        textAlign = textAlign,
        modifier = modifier,
    )
}

@Composable
fun SimpleButton(text: String, onClick: () -> Unit) {
    Button(onClick = onClick) {
        Text(text = text)
    }
}

@Composable
fun SimpleTextButton(text: String, onClick: () -> Unit) {
    TextButton(onClick = onClick) {
        Text(text = text)
    }
}

@Composable
fun SimpleRadioButton(
    modifier: Modifier = Modifier,
    text: String,
    selected: String,
    onSelect: (String) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.clickable { onSelect(text) }
    ) {
        RadioButton(
            selected = text == selected,
            onClick = { onSelect(text) }
        )
        Text(text = text)
    }
}

@Composable
fun <T> SimpleRadioButton(
    modifier: Modifier = Modifier,
    value: T,
    onTextValue: (T) -> String,
    selected: (T) -> Boolean,
    onSelect: (T) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.clickable { onSelect(value) }
    ) {
        RadioButton(
            selected = selected(value),
            onClick = { onSelect(value) }
        )
        Text(text = onTextValue(value))
    }
}

@Composable
fun SimpleIconButton(
    imageVector: ImageVector,
    modifier: Modifier = Modifier,
    tint: Color = Color.White,
    onClick: () -> Unit = {},
) {
    IconButton(onClick = onClick, modifier = modifier) {
        Icon(
            imageVector = imageVector,
            contentDescription = null,
            tint = tint
        )
    }
}

