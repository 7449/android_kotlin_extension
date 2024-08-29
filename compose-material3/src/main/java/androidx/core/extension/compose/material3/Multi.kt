package androidx.core.extension.compose.material3

import android.util.Log
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Modifier
import androidx.core.extension.compose.composeHandlerPost
import androidx.core.extension.compose.dataWrapperStateFlow
import androidx.core.extension.compose.viewmodel.REQUEST_END_MARK
import androidx.core.extension.compose.viewmodel.composeLaunch
import androidx.core.extension.http.DataWrapper
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow

/**
 * 多次请求，多次刷新
 * [value]每次请求的状态变化
 * [item]列表数据,通过[value]每次改变进行刷新
 * [requestUrl]请求的url
 */
interface MultiViewModel<T> {
    val value: StateFlow<DataWrapper<List<T>>>
    val item: List<T>
    val requestUrl: String
    fun onRefresh(retry: Boolean = false)
    fun onLoadMore(retry: Boolean = false)
    fun request(url: String, isRefresh: Boolean)
    suspend fun http(url: String, isRefresh: Boolean): Pair<List<T>, String>
}

/**
 * 多次请求，多次刷新
 * [initializeUrl]初始化的url，可通过重写[requestUrl]在某些特殊情况下更新Url
 * [initializeRefresh]是否初始化刷新
 */
abstract class SimpleMultiViewModel<T>(
    private val initializeUrl: String = "",
    private val initializeRefresh: Boolean = initializeUrl.isNotBlank(),
) : ViewModel(), MultiViewModel<T> {

    init {
        if (initializeRefresh) {
            composeHandlerPost { onRefresh() }
        }
    }

    private val _value = dataWrapperStateFlow<List<T>>(DataWrapper.Normal)
    private val _item = mutableStateListOf<T>()
    override val value: StateFlow<DataWrapper<List<T>>> get() = _value
    override val item: List<T> get() = _item.toList()

    override val requestUrl: String get() = initializeUrl
    private var currentUrl: String = initializeUrl

    override fun onRefresh(retry: Boolean) {
        if (value.value.isLoading) return
        currentUrl = requestUrl
        request(currentUrl, true)
    }

    override fun onLoadMore(retry: Boolean) {
        if (retry) {
            request(currentUrl, false)
        } else if ((value.value.isSuccess || value.value.isFailure) && currentUrl != REQUEST_END_MARK) {
            request(currentUrl, false)
        }
    }

    override fun request(url: String, isRefresh: Boolean) {
        Log.e("Print", "compose request http : $url")
        if (isRefresh) {
            _item.clear()
        }
        _value.value = if (isRefresh) DataWrapper.Loading.Default
        else DataWrapper.Loading.More
        composeLaunch(
            error = {
                Log.e("Print", "compose request http failure: ${it.message}")
                _value.value = if (isRefresh) DataWrapper.Failure.Default(it)
                else DataWrapper.Failure.More(it)
            },
            scope = {
                val (result, nextUrl) = http(url, isRefresh)
                currentUrl = nextUrl.ifBlank { REQUEST_END_MARK }
                if (isRefresh && result.isEmpty()) {
                    _value.value = DataWrapper.Empty.Default
                    return@composeLaunch
                }
                if (result.isNotEmpty()) {
                    _item.addAll(result)
                    _value.value = if (currentUrl == REQUEST_END_MARK) DataWrapper.Empty.More
                    else DataWrapper.Success(result)
                    return@composeLaunch
                }
                _value.value = DataWrapper.Empty.More
            }
        )
    }

}

@Composable
private fun <T : Any, VM : MultiViewModel<T>> SimpleMultiLazyScroll(
    viewModel: VM,
    content: @Composable BoxScope.() -> Unit,
) {
    val dataWrapper by viewModel.value.collectAsState()
    SimpleInfiniteBox(
        refreshing = dataWrapper.isLoading,
        onRefresh = viewModel::onRefresh,
        modifier = Modifier.fillMaxSize()
    ) {
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
            modifier = Modifier.fillMaxHeight().then(modifier)
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
            modifier = Modifier.fillMaxHeight().then(modifier),
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
            modifier = Modifier.fillMaxHeight().then(modifier)
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