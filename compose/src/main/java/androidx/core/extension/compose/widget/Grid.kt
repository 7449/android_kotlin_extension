package androidx.core.extension.compose.widget

import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

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

@Composable
fun <T> SimpleVerticalGrid(
    items: List<T>,
    modifier: Modifier = Modifier,
    columns: GridCells = GridCells.Fixed(2),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    reverseLayout: Boolean = false,
    verticalArrangement: Arrangement.Vertical = if (!reverseLayout) Arrangement.Top else Arrangement.Bottom,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    flingBehavior: FlingBehavior = ScrollableDefaults.flingBehavior(),
    userScrollEnabled: Boolean = true,
    header: LazyGridScope.() -> Unit = {},
    item: @Composable LazyGridItemScope.(index: Int, item: T) -> Unit,
) {
//    LaunchedEffect(gridState) {
//        snapshotFlow { gridState.layoutInfo.visibleItemsInfo }
//            .map { visibleItems -> visibleItems.lastOrNull()?.index }
//            .distinctUntilChanged()
//            .collect { lastVisibleItemIndex ->
//                if (lastVisibleItemIndex == items.size - 1) {
//                    onLoadMore()
//                }
//            }
//    }
    LazyVerticalGrid(
        state = rememberLazyGridState(),
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
        itemsIndexed(items, itemContent = item)
    }
}