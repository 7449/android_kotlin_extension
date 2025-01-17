package androidx.core.extension.compose.material

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.extension.compose.COPY
import androidx.core.extension.compose.DOWNLOAD
import androidx.core.extension.compose.DialogCardColumn
import androidx.core.extension.compose.DialogHolder
import androidx.core.extension.compose.colorPrimary
import androidx.core.extension.compose.rememberDialog

@Composable
fun rememberCopyOrDownloadDialog(
    value: String?,
    onClickCopy: (String) -> Unit,
    onClickDownload: (String) -> Unit,
): DialogHolder<*> {
    val currentCopy = rememberUpdatedState(onClickCopy)
    val currentDownload = rememberUpdatedState(onClickDownload)
    return rememberDialog { CopyOrDownloadDialog(value, currentCopy.value, currentDownload.value) }
}

@Composable
fun rememberSimpleDialog(title: String, ok: () -> Unit): DialogHolder<*> {
    val currentOk = rememberUpdatedState(ok)
    return rememberDialog { SimpleDialog(title, currentOk.value) }
}

@Composable
fun rememberSingleInputDialog(callback: (String) -> Unit): DialogHolder<*> {
    val currentCallback = rememberUpdatedState(callback)
    return rememberDialog { SingleInputDialog(currentCallback.value) }
}

@Composable
fun DialogHolder<*>.CopyOrDownloadDialog(
    value: String?,
    onClickCopy: (String) -> Unit,
    onClickDownload: (String) -> Unit,
) {
    DialogCardColumn {
        Text(value.orEmpty(), maxLines = 3, overflow = TextOverflow.Ellipsis)
        Row(
            modifier = Modifier
                .padding(top = 5.dp)
                .align(Alignment.End)
        ) {
            TextButton(onClick = {
                onClickCopy(value.orEmpty())
                hide()
            }) { Text(COPY, color = Color.Black) }
            TextButton(onClick = {
                onClickDownload(value.orEmpty())
                hide()
            }) { Text(DOWNLOAD, color = colorPrimary) }
        }
    }
}

@Composable
fun DialogHolder<*>.SimpleDialog(title: String, ok: () -> Unit) {
    DialogCardColumn {
        Text(title, fontSize = 15.sp)
        TextButton(
            modifier = Modifier
                .padding(top = 10.dp)
                .align(Alignment.End),
            onClick = {
                ok()
                hide()
            }
        ) {
            Text(
                stringResource(android.R.string.ok),
                color = colorPrimary,
            )
        }
    }
}

@Composable
fun DialogHolder<*>.SingleInputDialog(callback: (String) -> Unit) {
    DialogCardColumn {
        SimpleInput(
            label = stringResource(android.R.string.search_go),
            value = input.value,
            keyboardOptions = KeyboardOptions.Default,
            onValueChange = { input.value = it },
            keyboardAction = { }
        )
        TextButton(
            modifier = Modifier
                .padding(top = 10.dp)
                .align(Alignment.End),
            onClick = {
                callback(input.value.text)
                hide()
            }
        ) {
            Text(
                stringResource(android.R.string.search_go),
                color = colorPrimary,
            )
        }
    }
}