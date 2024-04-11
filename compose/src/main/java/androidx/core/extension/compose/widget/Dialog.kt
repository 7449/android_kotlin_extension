package androidx.core.extension.compose.widget

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
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
            Text(
                stringResource(android.R.string.ok),
                color = colorPrimary,
                modifier = Modifier
                    .clickable {
                        ok()
                        holder.hide()
                    }.padding(10.dp)
            )
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
            Text(
                stringResource(android.R.string.search_go),
                color = colorPrimary,
                modifier = Modifier
                    .clickable {
                        input(holder.mutableStateInput.value.text)
                        holder.hide()
                    }.padding(10.dp)
            )
        }
    )
}