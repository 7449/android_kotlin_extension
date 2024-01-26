package androidx.core.extension.compose.widget

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
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
fun <T> SimpleInfiniteList(
    items: List<T>,
    refreshing: Boolean = false,
    onRefresh: () -> Unit = {},
    onLoadMore: () -> Unit = {},
    content: @Composable (T) -> Unit,
) {
    val listState = rememberLazyListState()
    val state = rememberPullRefreshState(
        refreshing = refreshing,
        onRefresh = onRefresh
    )
    Box(modifier = Modifier.fillMaxSize().pullRefresh(state)) {
        LazyColumn(state = listState, modifier = Modifier.fillMaxHeight()) {
            itemsIndexed(items) { index, item ->
                content(item)
                if (index == items.size - 1 && !refreshing) {
                    onLoadMore()
                }
            }
        }
        PullRefreshIndicator(
            refreshing = refreshing,
            state = state,
            contentColor = colorPrimary,
            modifier = Modifier.align(Alignment.TopCenter),
        )
    }
}