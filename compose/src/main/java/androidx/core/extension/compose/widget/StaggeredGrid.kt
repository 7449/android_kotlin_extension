package androidx.core.extension.compose.widget

import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridItemScope
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridScope
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan.Companion.FullLine
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

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
fun <T> SimpleVerticalStaggeredGrid(
    items: List<T>,
    modifier: Modifier = Modifier,
    columns: StaggeredGridCells = StaggeredGridCells.Fixed(2),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    reverseLayout: Boolean = false,
    verticalItemSpacing: Dp = 0.dp,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    flingBehavior: FlingBehavior = ScrollableDefaults.flingBehavior(),
    userScrollEnabled: Boolean = true,
    header: LazyStaggeredGridScope.() -> Unit = {},
    item: @Composable LazyStaggeredGridItemScope.(index: Int, item: T) -> Unit,
) {
//    LaunchedEffect(staggeredGridState) {
//        snapshotFlow { staggeredGridState.layoutInfo.visibleItemsInfo }
//            .map { visibleItems -> visibleItems.lastOrNull()?.index }
//            .distinctUntilChanged()
//            .collect { lastVisibleItemIndex ->
//                if (lastVisibleItemIndex == items.size - 1) {
//                    onLoadMore()
//                }
//            }
//    }
    LazyVerticalStaggeredGrid(
        columns = columns,
        state = rememberLazyStaggeredGridState(),
        contentPadding = contentPadding,
        reverseLayout = reverseLayout,
        verticalItemSpacing = verticalItemSpacing,
        horizontalArrangement = horizontalArrangement,
        flingBehavior = flingBehavior,
        userScrollEnabled = userScrollEnabled,
        modifier = Modifier.fillMaxHeight().then(modifier)
    ) {
        header()
        itemsIndexed(items, itemContent = item)
    }
}