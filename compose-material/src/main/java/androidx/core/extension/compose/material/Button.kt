package androidx.core.extension.compose.material

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
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

