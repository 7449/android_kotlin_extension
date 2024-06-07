package androidx.core.extension.compose.widget

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.extension.compose.colorPrimary
import androidx.core.extension.http.DataWrapper

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun <T, M : StatusModel<T>> SimpleStatusBox(
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.TopStart,
    propagateMinConstraints: Boolean = false,
    indicatorContentColor: Color = colorPrimary,
    indicatorScale: Boolean = false,
    model: M,
    content: @Composable BoxScope.(DataWrapper.Success<T>) -> Unit,
) {
    val dataWrapper by model.value.collectAsState()
    val isMore by model.isMore.collectAsState()
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = contentAlignment,
        propagateMinConstraints = propagateMinConstraints
    ) {
        when (dataWrapper) {
            is DataWrapper.Success -> content(dataWrapper as DataWrapper.Success<T>)
            is DataWrapper.Failure.Default -> SimpleStatusFailureScreen(retry = model::onRefresh)
            DataWrapper.Empty.Default -> SimpleStatusEmptyScreen(empty = model::onRefresh)
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
            contentColor = indicatorContentColor,
            scale = indicatorScale,
            modifier = Modifier.align(Alignment.TopCenter),
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SimpleInfiniteBox(
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.TopStart,
    propagateMinConstraints: Boolean = false,

    refreshing: Boolean = false,
    onRefresh: () -> Unit = {},

    indicatorContentColor: Color = colorPrimary,
    indicatorScale: Boolean = false,

    content: @Composable BoxScope.() -> Unit,
) {
    val state = rememberPullRefreshState(
        refreshing = refreshing,
        onRefresh = onRefresh
    )
    Box(
        modifier = modifier
            .pullRefresh(state)
            .fillMaxSize(),
        contentAlignment = contentAlignment,
        propagateMinConstraints = propagateMinConstraints
    ) {
        content()
        PullRefreshIndicator(
            refreshing = refreshing,
            state = state,
            contentColor = indicatorContentColor,
            scale = indicatorScale,
            modifier = Modifier.align(Alignment.TopCenter),
        )
    }
}