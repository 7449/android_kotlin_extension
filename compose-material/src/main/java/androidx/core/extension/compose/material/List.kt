package androidx.core.extension.compose.material

import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.extension.compose.widget.SimpleList

@Composable
fun <T> SimpleInfiniteList(
    modifier: Modifier = Modifier,

    items: List<T>,

    refreshing: Boolean = false,
    onRefresh: () -> Unit = {},
    onLoadMore: () -> Unit = {},

    header: LazyListScope.() -> Unit = {},
    item: @Composable LazyItemScope.(T) -> Unit,
) {
    SimpleInfiniteBox(refreshing = refreshing, onRefresh = onRefresh) {
        SimpleList(
            items = items,
            modifier = modifier,
            header = header
        ) { index, item ->
            item(item)
            if (index == items.size - 1 && !refreshing) {
                onLoadMore()
            }
        }
    }
}