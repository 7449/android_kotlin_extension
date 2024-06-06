package androidx.core.extension.compose.widget

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.extension.http.DataWrapper

@Composable
internal fun <T> BoxScope.SimpleStatusScreen(
    dataWrapper: DataWrapper<T> = DataWrapper.Normal,
    retry: () -> Unit,
    empty: () -> Unit,
    content: @Composable BoxScope.(DataWrapper<T>) -> Unit,
) {
    when (dataWrapper) {
        is DataWrapper.Success -> content(dataWrapper)
        is DataWrapper.Failure.Default -> SimpleStatusFailureScreen(retry = retry)
        DataWrapper.Empty.Default -> SimpleStatusEmptyScreen(empty = empty)
        DataWrapper.Loading.Default -> SimpleStatusLoadingDefaultScreen()
        DataWrapper.Loading.More -> {}
        is DataWrapper.Failure.More -> {}
        DataWrapper.Empty.More -> {}
        DataWrapper.Normal -> {}
    }
}

@Composable
private fun BoxScope.SimpleStatusLoadingDefaultScreen() {
    CircularProgressIndicator(
        modifier = Modifier.align(Alignment.Center)
    )
}

@Composable
private fun BoxScope.SimpleStatusEmptyScreen(empty: () -> Unit) {
    TextButton(
        onClick = empty,
        modifier = Modifier.align(Alignment.Center)
    ) { Text(text = "数据为空") }
}

@Composable
private fun BoxScope.SimpleStatusFailureScreen(retry: () -> Unit) {
    TextButton(
        onClick = retry,
        modifier = Modifier.align(Alignment.Center)
    ) { Text(text = "点击重试") }
}