package androidx.core.extension.compose.widget

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.core.extension.compose.DialogHolder
import androidx.core.extension.compose.colorPrimary

@Composable
fun SimpleDialog(
    title: String,
    holder: DialogHolder,
    ok: () -> Unit,
) {
    AlertDialog(
        title = { Text(title, fontSize = 15.sp) },
        onDismissRequest = { holder.hide() },
        confirmButton = {
            TextButton(onClick = {
                ok()
                holder.hide()
            }) {
                Text(
                    stringResource(android.R.string.ok),
                    color = colorPrimary,
                )
            }
        }
    )
}

@Composable
fun SingleInputDialog(
    holder: DialogHolder,
    input: (String) -> Unit,
) {
    AlertDialog(
        onDismissRequest = holder.hide,
        text = {
            Column {
                SimpleInput(
                    label = stringResource(android.R.string.search_go),
                    value = holder.mutableStateInput.value,
                    keyboardOptions = KeyboardOptions.Default,
                    onValueChange = { holder.mutableStateInput.value = it },
                    keyboardAction = { }
                )
            }
        },
        confirmButton = {
            TextButton(onClick = {
                input(holder.mutableStateInput.value.text)
                holder.hide()
            }) {
                Text(
                    stringResource(android.R.string.search_go),
                    color = colorPrimary,
                )
            }
        }
    )
}