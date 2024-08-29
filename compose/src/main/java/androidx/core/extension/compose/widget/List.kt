package androidx.core.extension.compose.widget

import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun <T> SimpleList(
    items: List<T>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    reverseLayout: Boolean = false,
    verticalArrangement: Arrangement.Vertical = if (!reverseLayout) Arrangement.Top else Arrangement.Bottom,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    flingBehavior: FlingBehavior = ScrollableDefaults.flingBehavior(),
    userScrollEnabled: Boolean = true,
    header: LazyListScope.() -> Unit = {},
    item: @Composable LazyItemScope.(index: Int, item: T) -> Unit,
) {
//    LaunchedEffect(listState) {
//        snapshotFlow { listState.layoutInfo.visibleItemsInfo }
//            .map { visibleItems -> visibleItems.lastOrNull()?.index }
//            .distinctUntilChanged()
//            .collect { lastVisibleItemIndex ->
//                if (lastVisibleItemIndex == items.size - 1) {
//                    onLoadMore()
//                }
//            }
//    }
    LazyColumn(
        state = rememberLazyListState(),
        contentPadding = contentPadding,
        reverseLayout = reverseLayout,
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment,
        flingBehavior = flingBehavior,
        userScrollEnabled = userScrollEnabled,
        modifier = Modifier.fillMaxHeight().then(modifier)
    ) {
        header()
        itemsIndexed(items, itemContent = item)
    }
}