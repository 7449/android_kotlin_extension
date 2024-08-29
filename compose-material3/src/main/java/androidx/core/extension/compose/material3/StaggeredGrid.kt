package androidx.core.extension.compose.material3

import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridItemScope
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridScope
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.extension.compose.widget.SimpleVerticalStaggeredGrid

@Composable
fun <T> SimpleInfiniteVerticalStaggeredGrid(
    modifier: Modifier = Modifier,

    items: List<T>,

    refreshing: Boolean = false,
    onRefresh: () -> Unit = {},
    onLoadMore: () -> Unit = {},

    columns: StaggeredGridCells = StaggeredGridCells.Fixed(2),

    header: LazyStaggeredGridScope.() -> Unit = {},
    item: @Composable LazyStaggeredGridItemScope.(T) -> Unit,
) {
    SimpleInfiniteBox(refreshing = refreshing, onRefresh = onRefresh) {
        SimpleVerticalStaggeredGrid(
            items = items,
            columns = columns,
            modifier = modifier,
            header = header,
        ) { index, item ->
            item(item)
            if (index == items.size - 1 && !refreshing) {
                onLoadMore()
            }
        }
    }
}