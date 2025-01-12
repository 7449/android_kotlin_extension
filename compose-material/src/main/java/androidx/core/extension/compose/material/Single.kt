@file:OptIn(ExperimentalMaterialApi::class)

package androidx.core.extension.compose.material

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.extension.compose.colorPrimary
import androidx.core.extension.compose.viewmodel.SingleViewModel
import androidx.core.extension.wrapper.http.DataWrapper

@Composable
fun <T, VM : SingleViewModel<T>> SimpleSingleBox(
    modifier: Modifier = Modifier,
    viewModel: VM,
    content: @Composable BoxScope.(DataWrapper.Success<T>) -> Unit,
) {
    val dataWrapper by viewModel.value.collectAsState()
    val isMore by viewModel.isMore.collectAsState()
    Box(
        modifier = Modifier
            .fillMaxSize()
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
        PullRefreshIndicator(
            refreshing = isMore,
            state = rememberPullRefreshState(
                refreshing = isMore,
                onRefresh = {}
            ),
            contentColor = colorPrimary,
            scale = false,
            modifier = Modifier.align(Alignment.TopCenter),
        )
    }
}