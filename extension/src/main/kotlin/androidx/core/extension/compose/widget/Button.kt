package androidx.core.extension.compose.widget

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun SimpleRadioButton(
    modifier: Modifier = Modifier,
    text: String,
    selected: String,
    onSelect: (String) -> Unit,
) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier) {
        RadioButton(
            selected = text == selected,
            onClick = { onSelect(text) }
        )
        Text(text = text)
    }
}

