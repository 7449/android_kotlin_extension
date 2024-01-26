package androidx.core.extension.compose.widget

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.extension.compose.colorPrimary

fun LazyGridScope.header(
    content: @Composable LazyGridItemScope.() -> Unit,
) {
    item(span = { GridItemSpan(maxLineSpan) }, content = content)
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun <T> SimpleInfiniteVerticalGrid(
    items: List<T>,
    refreshing: Boolean = false,
    onRefresh: () -> Unit = {},
    onLoadMore: () -> Unit = {},
    columns: GridCells = GridCells.Fixed(2),
    header: @Composable LazyGridItemScope.() -> Unit = {},
    content: @Composable (T) -> Unit,
) {
    val gridState = rememberLazyGridState()
    val state = rememberPullRefreshState(
        refreshing = refreshing,
        onRefresh = onRefresh
    )
    Box(modifier = Modifier.fillMaxSize().pullRefresh(state)) {
        Column {
            LazyVerticalGrid(
                state = gridState,
                columns = columns,
                modifier = Modifier.fillMaxHeight()
            ) {
                header(header)
                itemsIndexed(items) { index, item ->
                    content(item)
                    if (index == items.size - 1 && !refreshing) {
                        onLoadMore()
                    }
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
