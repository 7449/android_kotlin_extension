package androidx.core.extension.compose.material3

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridItemScope
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridScope
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.extension.compose.viewmodel.MultiViewModel
import androidx.core.extension.http.DataWrapper

@Composable
private fun <T : Any, VM : MultiViewModel<T>> SimpleMultiLazyScroll(
    viewModel: VM,
    content: @Composable BoxScope.() -> Unit,
) {
    val dataWrapper by viewModel.value.collectAsState()
    SimpleInfiniteBox(refreshing = dataWrapper.isLoading, onRefresh = viewModel::onRefresh) {
        content()
        when (dataWrapper) {
            is DataWrapper.Failure.Default -> SimpleStatusFailureScreen(retry = viewModel::onRefresh)
            DataWrapper.Empty.Default -> SimpleStatusEmptyScreen(empty = viewModel::onRefresh)
            DataWrapper.Loading.More -> {}
            DataWrapper.Loading.Default -> {}
            is DataWrapper.Failure.More -> {}
            is DataWrapper.Success -> {}
            DataWrapper.Empty.More -> {}
            DataWrapper.Normal -> {}
        }
    }
}

@Composable
fun <T : Any, VM : MultiViewModel<T>> SimpleMultiVerticalGrid(
    modifier: Modifier = Modifier,
    viewModel: VM,
    columns: GridCells = GridCells.Fixed(2),
    header: LazyGridScope.() -> Unit = {},
    item: @Composable LazyGridItemScope.(T) -> Unit,
) {
    val gridState = rememberLazyGridState()
    val dataWrapper by viewModel.value.collectAsState()
    SimpleMultiLazyScroll(viewModel = viewModel) {
        LazyVerticalGrid(
            state = gridState,
            columns = columns,
            modifier = Modifier
                .fillMaxHeight()
                .then(modifier)
        ) {
            header()
            itemsIndexed(viewModel.item) { index, item ->
                item(item)
                if (index == viewModel.item.size - 1 && dataWrapper.isSuccess) {
                    viewModel.onLoadMore()
                }
            }
            item(span = { GridItemSpan(maxLineSpan) }) {
                if (dataWrapper is DataWrapper.Empty.More) {
                    SimpleStatusEmptyMoreScreen { }
                } else if (dataWrapper is DataWrapper.Failure.More) {
                    SimpleStatusFailureMoreScreen { viewModel.onLoadMore(retry = true) }
                }
            }
        }
    }
}

@Composable
fun <T : Any, VM : MultiViewModel<T>> SimpleMultiList(
    modifier: Modifier = Modifier,
    viewModel: VM,
    header: LazyListScope.() -> Unit = {},
    item: @Composable LazyItemScope.(T) -> Unit,
) {
    val listState = rememberLazyListState()
    val dataWrapper by viewModel.value.collectAsState()
    SimpleMultiLazyScroll(viewModel = viewModel) {
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxHeight()
                .then(modifier),
        ) {
            header()
            itemsIndexed(viewModel.item) { index, item ->
                item(item)
                if (index == viewModel.item.size - 1 && dataWrapper.isSuccess) {
                    viewModel.onLoadMore()
                }
            }
            item {
                if (dataWrapper is DataWrapper.Empty.More) {
                    SimpleStatusEmptyMoreScreen { }
                } else if (dataWrapper is DataWrapper.Failure.More) {
                    SimpleStatusFailureMoreScreen { viewModel.onLoadMore(retry = true) }
                }
            }
        }
    }
}

@Composable
fun <T : Any, M : MultiViewModel<T>> SimpleMultiVerticalStaggeredGrid(
    modifier: Modifier = Modifier,
    viewModel: M,
    columns: StaggeredGridCells = StaggeredGridCells.Fixed(2),
    header: LazyStaggeredGridScope.() -> Unit = {},
    item: @Composable LazyStaggeredGridItemScope.(T) -> Unit,
) {
    val staggeredGridState = rememberLazyStaggeredGridState()
    val dataWrapper by viewModel.value.collectAsState()
    SimpleMultiLazyScroll(viewModel = viewModel) {
        LazyVerticalStaggeredGrid(
            columns = columns,
            state = staggeredGridState,
            modifier = Modifier
                .fillMaxHeight()
                .then(modifier)
        ) {
            header()
            itemsIndexed(viewModel.item) { index, item ->
                item(item)
                if (index == viewModel.item.size - 1 && dataWrapper.isSuccess) {
                    viewModel.onLoadMore()
                }
            }
            item(span = StaggeredGridItemSpan.FullLine) {
                if (dataWrapper is DataWrapper.Empty.More) {
                    SimpleStatusEmptyMoreScreen { }
                } else if (dataWrapper is DataWrapper.Failure.More) {
                    SimpleStatusFailureMoreScreen { viewModel.onLoadMore(retry = true) }
                }
            }
        }
    }
}