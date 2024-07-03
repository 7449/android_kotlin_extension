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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.extension.compose.colorPrimary
import androidx.core.extension.http.DataWrapper

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

//    LaunchedEffect(state) {
//        snapshotFlow { state.layoutInfo.visibleItemsInfo }
//            .map { visibleItems -> visibleItems.lastOrNull()?.index }
//            .distinctUntilChanged()
//            .collect { lastVisibleItemIndex ->
//                if (lastVisibleItemIndex == model.item.size && dataWrapper.isSuccess) {
//                    model.onLoadMore()
//                }
//            }
//    }

@Composable
fun <T : Any, M : StatusListModel<T>> SimpleStatusVerticalGrid(
    modifier: Modifier = Modifier,

    model: M,

    contentAlignment: Alignment = Alignment.TopStart,
    propagateMinConstraints: Boolean = false,

    columns: GridCells = GridCells.Fixed(2),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    reverseLayout: Boolean = false,
    verticalArrangement: Arrangement.Vertical = if (!reverseLayout) Arrangement.Top else Arrangement.Bottom,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    flingBehavior: FlingBehavior = ScrollableDefaults.flingBehavior(),
    userScrollEnabled: Boolean = true,

    indicatorContentColor: Color = colorPrimary,
    indicatorScale: Boolean = false,


    header: LazyGridScope.() -> Unit = {},
    item: @Composable LazyGridItemScope.(T) -> Unit,
) {
    val state = rememberLazyGridState()
    val dataWrapper by model.value.collectAsState()
    SimpleStatusLazyScrollScreen(
        model = model,
        dataWrapper = dataWrapper,
        contentAlignment = contentAlignment,
        propagateMinConstraints = propagateMinConstraints,
        indicatorContentColor = indicatorContentColor,
        indicatorScale = indicatorScale
    ) {
        LazyVerticalGrid(
            state = state,
            columns = columns,
            contentPadding = contentPadding,
            reverseLayout = reverseLayout,
            verticalArrangement = verticalArrangement,
            horizontalArrangement = horizontalArrangement,
            flingBehavior = flingBehavior,
            userScrollEnabled = userScrollEnabled,
            modifier = Modifier.fillMaxHeight().then(modifier)
        ) {
            header()
            itemsIndexed(model.item) { index, item ->
                item(item)
                if (index == model.item.size - 1 && dataWrapper.isSuccess) {
                    model.onLoadMore()
                }
            }
            item(span = { GridItemSpan(maxLineSpan) }) {
                if (dataWrapper is DataWrapper.Empty.More) {
                    SimpleStatusEmptyMoreScreen { }
                } else if (dataWrapper is DataWrapper.Failure.More) {
                    SimpleStatusFailureMoreScreen { model.onLoadMore(retry = true) }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun <T> SimpleInfiniteVerticalGrid(
    modifier: Modifier = Modifier,

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

    indicatorContentColor: Color = colorPrimary,
    indicatorScale: Boolean = false,


    header: LazyGridScope.() -> Unit = {},
    item: @Composable LazyGridItemScope.(T) -> Unit,
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
