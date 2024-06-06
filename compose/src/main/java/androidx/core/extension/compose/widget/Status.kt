package androidx.core.extension.compose.widget

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.extension.http.DataWrapper

@Composable
fun <T> SimpleStatusBox(
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.TopStart,
    propagateMinConstraints: Boolean = false,
    dataWrapper: DataWrapper<T>,
    retry: () -> Unit = {},
    empty: () -> Unit = {},
    content: @Composable BoxScope.(DataWrapper<T>) -> Unit,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = contentAlignment,
        propagateMinConstraints = propagateMinConstraints
    ) {
        SimpleStatusScreen(
            dataWrapper = dataWrapper,
            retry = retry,
            empty = empty,
            content = content
        )
    }
}

@Composable
internal fun <T> BoxScope.SimpleStatusScreen(
    dataWrapper: DataWrapper<T> = DataWrapper.Normal,
    retry: () -> Unit,
    empty: () -> Unit,
    content: @Composable BoxScope.(DataWrapper<T>) -> Unit,
) {
    when (dataWrapper) {
        is DataWrapper.Success -> content(dataWrapper)
        is DataWrapper.Failure -> SimpleStatusFailureScreen(retry = retry)
        DataWrapper.Empty.Default -> SimpleStatusEmptyScreen(empty = empty)
        DataWrapper.Loading.Default -> SimpleStatusLoadingDefaultScreen()
        DataWrapper.Loading.Refresh -> {}
        DataWrapper.Loading.More -> {}
        DataWrapper.Empty.Load -> {}
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