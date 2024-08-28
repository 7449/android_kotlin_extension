package androidx.core.extension.compose.widget

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
internal fun BoxScope.SimpleStatusLoadingDefaultScreen() {
    CircularProgressIndicator(
        modifier = Modifier.align(Alignment.Center)
    )
}

@Composable
internal fun BoxScope.SimpleStatusEmptyScreen(empty: () -> Unit) {
    TextButton(
        onClick = empty,
        modifier = Modifier.align(Alignment.Center)
    ) { Text(text = "数据为空") }
}

@Composable
internal fun BoxScope.SimpleStatusFailureScreen(retry: () -> Unit) {
    TextButton(
        onClick = retry,
        modifier = Modifier.align(Alignment.Center)
    ) { Text(text = "网络请求出错啦") }
}

@Composable
internal fun SimpleStatusEmptyMoreScreen(empty: () -> Unit) {
    Box(modifier = Modifier.fillMaxWidth()) {
        TextButton(
            onClick = empty,
            modifier = Modifier.align(Alignment.Center)
        ) { Text(text = "没有更多的数据啦") }
    }
}

@Composable
internal fun SimpleStatusFailureMoreScreen(retry: () -> Unit) {
    Box(modifier = Modifier.fillMaxWidth()) {
        TextButton(
            onClick = retry,
            modifier = Modifier.align(Alignment.Center)
        ) { Text(text = "网络请求出错啦") }
    }
}