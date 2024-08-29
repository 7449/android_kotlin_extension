package androidx.core.extension.compose.material3

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.extension.compose.widget.SimpleVerticalGrid

@Composable
fun <T> SimpleInfiniteVerticalGrid(
    modifier: Modifier = Modifier,

    items: List<T>,

    refreshing: Boolean = false,
    onRefresh: () -> Unit = {},
    onLoadMore: () -> Unit = {},

    columns: GridCells = GridCells.Fixed(2),

    header: LazyGridScope.() -> Unit = {},
    item: @Composable LazyGridItemScope.(T) -> Unit,
) {
    SimpleInfiniteBox(refreshing = refreshing, onRefresh = onRefresh) {
        SimpleVerticalGrid(
            items = items,
            modifier = modifier,
            columns = columns,
            header = header
        ) { index, item ->
            item(item)
            if (index == items.size - 1 && !refreshing) {
                onLoadMore()
            }
        }
    }
}