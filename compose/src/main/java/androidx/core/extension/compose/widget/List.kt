package androidx.core.extension.compose.widget

import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun <T> SimpleInfiniteList(
    modifier: Modifier = Modifier,

    items: List<T>,

    refreshing: Boolean = false,
    onRefresh: () -> Unit = {},
    onLoadMore: () -> Unit = {},

    contentAlignment: Alignment = Alignment.TopStart,
    propagateMinConstraints: Boolean = false,

    contentPadding: PaddingValues = PaddingValues(0.dp),
    reverseLayout: Boolean = false,
    verticalArrangement: Arrangement.Vertical = if (!reverseLayout) Arrangement.Top else Arrangement.Bottom,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    flingBehavior: FlingBehavior = ScrollableDefaults.flingBehavior(),
    userScrollEnabled: Boolean = true,

    indicatorContentColor: Color = colorPrimary,
    indicatorScale: Boolean = false,

    header: LazyListScope.() -> Unit = {},
    item: @Composable LazyItemScope.(T) -> Unit,
) {
    val state = rememberPullRefreshState(
        refreshing = refreshing,
        onRefresh = onRefresh
    )
    val listState = rememberLazyListState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(state),
        contentAlignment = contentAlignment,
        propagateMinConstraints = propagateMinConstraints
    ) {
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxHeight().then(modifier),
            contentPadding = contentPadding,
            reverseLayout = reverseLayout,
            verticalArrangement = verticalArrangement,
            horizontalAlignment = horizontalAlignment,
            flingBehavior = flingBehavior,
            userScrollEnabled = userScrollEnabled
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
//    LaunchedEffect(listState) {
//        snapshotFlow { listState.layoutInfo.visibleItemsInfo }
//            .map { visibleItems -> visibleItems.lastOrNull()?.index }
//            .distinctUntilChanged()
//            .collect { lastVisibleItemIndex ->
//                if (lastVisibleItemIndex == items.size && !refreshing) {
//                    onLoadMore()
//                }
//            }
//    }
}