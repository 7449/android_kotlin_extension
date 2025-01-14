package androidx.core.extension.compose.material

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.core.extension.compose.COPY
import androidx.core.extension.compose.DOWNLOAD
import androidx.core.extension.compose.DialogHolder
import androidx.core.extension.compose.colorPrimary

@Composable
fun DialogHolder<*>.CopyOrDownloadDialog(
    value: String?,
    onClickCopy: (String) -> Unit,
    onClickDownload: (String) -> Unit,
) {
    AlertDialog(
        onDismissRequest = hide,
        text = { Text(value.orEmpty(), maxLines = 3, overflow = TextOverflow.Ellipsis) },
        confirmButton = {
            TextButton(onClick = {
                onClickCopy(value.orEmpty())
                hide()
            }) { Text(COPY, color = Color.Black) }
            TextButton(onClick = {
                onClickDownload(value.orEmpty())
                hide()
            }) { Text(DOWNLOAD, color = colorPrimary) }
        }
    )
}

@Composable
fun DialogHolder<*>.SimpleDialog(title: String, ok: () -> Unit) {
    AlertDialog(
        title = { Text(title, fontSize = 15.sp) },
        onDismissRequest = { hide() },
        confirmButton = {
            TextButton(onClick = {
                ok()
                hide()
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
fun DialogHolder<*>.SingleInputDialog(callback: (String) -> Unit) {
    AlertDialog(
        onDismissRequest = hide,
        text = {
            Column {
                SimpleInput(
                    label = stringResource(android.R.string.search_go),
                    value = input.value,
                    keyboardOptions = KeyboardOptions.Default,
                    onValueChange = { input.value = it },
                    keyboardAction = { }
                )
            }
        },
        confirmButton = {
            TextButton(onClick = {
                callback(input.value.text)
                hide()
            }) {
                Text(
                    stringResource(android.R.string.search_go),
                    color = colorPrimary,
                )
            }
        }
    )
}