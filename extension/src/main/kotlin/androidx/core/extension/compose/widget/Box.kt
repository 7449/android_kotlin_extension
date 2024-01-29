package androidx.core.extension.compose.widget

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.extension.compose.colorPrimary

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SimpleInfiniteBox(
    refreshing: Boolean = false,
    onRefresh: () -> Unit = {},
    content: @Composable () -> Unit,
) {
    val state = rememberPullRefreshState(
        refreshing = refreshing,
        onRefresh = onRefresh
    )
    Box(modifier = Modifier.fillMaxSize().pullRefresh(state)) {
        content()
        PullRefreshIndicator(
            refreshing = refreshing,
            state = state,
            contentColor = colorPrimary,
            modifier = Modifier.align(Alignment.TopCenter),
        )
    }
}