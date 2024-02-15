package androidx.core.extension.sample

import android.os.Bundle
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.extension.compose.activity.ComposeActivity
import androidx.core.extension.compose.activity.setThemeContent
import androidx.core.extension.compose.textFieldValueStateOf
import androidx.core.extension.compose.widget.SimpleInput

class SampleActivity : ComposeActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setThemeContent {
            val fieldValueMutableState = remember { textFieldValueStateOf() }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                Text("Compose")
                SimpleInput(
                    label = "Input...",
                    value = fieldValueMutableState.value,
                    onValueChange = { fieldValueMutableState.value = it },
                    keyboardAction = {
                    }
                )
            }
        }
    }
}