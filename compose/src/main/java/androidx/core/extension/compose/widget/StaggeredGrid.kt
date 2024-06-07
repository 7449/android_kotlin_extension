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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.extension.compose.colorPrimary
import androidx.core.extension.http.DataWrapper

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

@Composable
fun <T : Any, MODEL : StatusListModel<T>> SimpleStatusVerticalStaggeredGrid(
    modifier: Modifier = Modifier,

    model: MODEL,

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
    val state = rememberLazyStaggeredGridState()
    val dataWrapper by model.value.collectAsState()
    val items by model.item.collectAsState()
    SimpleStatusLazyScrollScreen(
        model = model,
        contentAlignment = contentAlignment,
        propagateMinConstraints = propagateMinConstraints,
        indicatorContentColor = indicatorContentColor,
        indicatorScale = indicatorScale
    ) {
        LazyVerticalStaggeredGrid(
            columns = columns,
            state = state,
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
                if (index == items.size - 1 && !model.isLoading) {
                    model.onLoadMore()
                }
            }
            item(span = FullLine) {
                if (dataWrapper is DataWrapper.Empty.More) {
                    SimpleStatusEmptyMoreScreen { model.onLoadMore(retry = true) }
                } else if (dataWrapper is DataWrapper.Failure.More) {
                    SimpleStatusFailureMoreScreen { model.onLoadMore(retry = true) }
                }
            }
        }
    }
    LaunchedEffect(dataWrapper) {
        if (dataWrapper is DataWrapper.Empty.More || dataWrapper is DataWrapper.Failure.More) {
            state.animateScrollToItem(items.size - 1)
        }
    }
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
}