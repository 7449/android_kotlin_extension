package androidx.core.extension.compose.widget

import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridItemScope
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridScope
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan.Companion.FullLine
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.extension.compose.colorPrimary

fun <T> LazyStaggeredGridScope.fillWidthItems(
    item: List<T>?,
    content: @Composable LazyStaggeredGridItemScope.(T) -> Unit,
) {
    if (item == null) return
    items(item, span = { FullLine }) { content(it) }
}

fun <T> LazyStaggeredGridScope.fillWidthItem(
    item: T?,
    content: @Composable LazyStaggeredGridItemScope.(T) -> Unit,
) {
    if (item == null) return
    item(span = FullLine, content = { content(item) })
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun <T> SimpleInfiniteVerticalStaggeredGrid(
    modifier: Modifier = Modifier,

    items: List<T>,

    refreshing: Boolean = false,
    onRefresh: () -> Unit = {},
    onLoadMore: () -> Unit = {},

    contentAlignment: Alignment = Alignment.TopStart,
    propagateMinConstraints: Boolean = false,

    columns: StaggeredGridCells = StaggeredGridCells.Fixed(2),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    reverseLayout: Boolean = false,
    verticalItemSpacing: Dp = 0.dp,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    flingBehavior: FlingBehavior = ScrollableDefaults.flingBehavior(),
    userScrollEnabled: Boolean = true,

    indicatorContentColor: Color = colorPrimary,
    indicatorScale: Boolean = false,

    header: LazyStaggeredGridScope.() -> Unit = {},
    item: @Composable LazyStaggeredGridItemScope.(T) -> Unit,
) {
    val staggeredGridState = rememberLazyStaggeredGridState()
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
        LazyVerticalStaggeredGrid(
            columns = columns,
            state = staggeredGridState,
            contentPadding = contentPadding,
            reverseLayout = reverseLayout,
            verticalItemSpacing = verticalItemSpacing,
            horizontalArrangement = horizontalArrangement,
            flingBehavior = flingBehavior,
            userScrollEnabled = userScrollEnabled,
            modifier = Modifier.fillMaxHeight().then(modifier)
        ) {
            header()
            itemsIndexed(items) { index, item ->
                item(item)
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
//    LaunchedEffect(staggeredGridState) {
//        snapshotFlow { staggeredGridState.layoutInfo.visibleItemsInfo }
//            .map { visibleItems -> visibleItems.lastOrNull()?.index }
//            .distinctUntilChanged()
//            .collect { lastVisibleItemIndex ->
//                if (lastVisibleItemIndex == items.size && !refreshing) {
//                    onLoadMore()
//                }
//            }
//    }
}