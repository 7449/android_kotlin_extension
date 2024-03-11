package androidx.core.extension.sample

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.core.extension.compose.widget.SimpleBackToolbar

@Composable
fun SampleContent(title: String, back: () -> Unit, content: @Composable () -> Unit) {
    Column {
        SimpleBackToolbar(title, back)
        content()
    }
}