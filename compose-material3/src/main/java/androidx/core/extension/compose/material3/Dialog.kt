package androidx.core.extension.compose.material3

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
fun DialogHolder.CopyOrDownloadMaterial3Dialog(
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
fun DialogHolder.SimpleMaterial3Dialog(title: String, ok: () -> Unit) {
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
fun DialogHolder.SingleMaterial3InputDialog(input: (String) -> Unit) {
    AlertDialog(
        onDismissRequest = hide,
        text = {
            Column {
                SimpleInput(
                    label = stringResource(android.R.string.search_go),
                    value = mutableStateInput.value,
                    keyboardOptions = KeyboardOptions.Default,
                    onValueChange = { mutableStateInput.value = it },
                    keyboardAction = { }
                )
            }
        },
        confirmButton = {
            TextButton(onClick = {
                input(mutableStateInput.value.text)
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