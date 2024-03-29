package androidx.core.extension.compose.widget

import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.extension.compose.colorPrimary

fun LazyGridScope.header(
    content: @Composable LazyGridItemScope.() -> Unit,
) {
    item(span = { GridItemSpan(maxLineSpan) }, content = content)
}

fun <T> LazyGridScope.fillWidthItems(
    item: List<T>?,
    content: @Composable LazyGridItemScope.(T) -> Unit,
) {
    if (item == null) return
    items(item, span = { GridItemSpan(maxLineSpan) }) { content(it) }
}

fun <T> LazyGridScope.fillWidthItem(
    item: T?,
    content: @Composable LazyGridItemScope.(T) -> Unit,
) {
    if (item == null) return
    item(span = { GridItemSpan(maxLineSpan) }, content = { content(item) })
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun <T> SimpleInfiniteVerticalGrid(
    items: List<T>,

    refreshing: Boolean = false,
    onRefresh: () -> Unit = {},
    onLoadMore: () -> Unit = {},

    contentAlignment: Alignment = Alignment.TopStart,
    propagateMinConstraints: Boolean = false,

    columns: GridCells = GridCells.Fixed(2),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    reverseLayout: Boolean = false,
    verticalArrangement: Arrangement.Vertical = if (!reverseLayout) Arrangement.Top else Arrangement.Bottom,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    flingBehavior: FlingBehavior = ScrollableDefaults.flingBehavior(),
    userScrollEnabled: Boolean = true,
    header: @Composable LazyGridItemScope.() -> Unit = {},

    indicatorContentColor: Color = colorPrimary,
    indicatorScale: Boolean = false,

    content: @Composable LazyGridItemScope.(T) -> Unit,
) {
    val gridState = rememberLazyGridState()
    val state = rememberPullRefreshState(
        refreshing = refreshing,
        onRefresh = onRefresh
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(state),
        contentAlignment = contentAlignment,
        propagateMinConstraints = propagateMinConstraints
    ) {
        LazyVerticalGrid(
            state = gridState,
            columns = columns,
            contentPadding = contentPadding,
            reverseLayout = reverseLayout,
            verticalArrangement = verticalArrangement,
            horizontalArrangement = horizontalArrangement,
            flingBehavior = flingBehavior,
            userScrollEnabled = userScrollEnabled,
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
        PullRefreshIndicator(
            refreshing = refreshing,
            state = state,
            contentColor = indicatorContentColor,
            scale = indicatorScale,
            modifier = Modifier.align(Alignment.TopCenter),
        )
    }
}
