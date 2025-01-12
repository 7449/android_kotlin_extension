package androidx.core.extension.compose.material3

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.extension.compose.viewmodel.SingleViewModel
import androidx.core.extension.wrapper.http.DataWrapper

@Composable
fun <T, VM : SingleViewModel<T>> SimpleSingleBox(
    modifier: Modifier = Modifier,
    viewModel: VM,
    content: @Composable BoxScope.(DataWrapper.Success<T>) -> Unit,
) {
//    val state = rememberPullToRefreshState()
    val dataWrapper by viewModel.value.collectAsState()
    val isMore by viewModel.isMore.collectAsState()
    Box(
        modifier = Modifier
            .fillMaxSize()
//            .pullToRefresh(
//                state = state,
//                isRefreshing = isMore,
//                enabled = isMore,
//                onRefresh = {}
//            )
            .then(modifier)
    ) {
        when (dataWrapper) {
            is DataWrapper.Success -> content(dataWrapper as DataWrapper.Success<T>)
            is DataWrapper.Failure.Default -> SimpleStatusFailureScreen(retry = viewModel::onRefresh)
            DataWrapper.Empty.Default -> SimpleStatusEmptyScreen(empty = viewModel::onRefresh)
            DataWrapper.Loading.Default -> SimpleStatusLoadingDefaultScreen()
            DataWrapper.Loading.More -> {}
            is DataWrapper.Failure.More -> {}
            DataWrapper.Empty.More -> {}
            DataWrapper.Normal -> {}
        }
//        Indicator(
//            modifier = Modifier.align(Alignment.TopCenter),
//            isRefreshing = isMore,
//            state = state,
//            containerColor = Color.White,
//            color = colorPrimary,
//        )
        if (isMore) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }
    }
}