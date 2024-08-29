package androidx.core.extension.compose.material3

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.extension.compose.composeHandlerPost
import androidx.core.extension.compose.dataWrapperStateFlow
import androidx.core.extension.compose.viewmodel.REQUEST_END_MARK
import androidx.core.extension.compose.viewmodel.composeLaunch
import androidx.core.extension.http.DataWrapper
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * 一次请求，多次刷新
 * [value]请求的数据
 * [isMore]是否是更多数据请求
 * [requestUrl]请求的Url
 */
interface SingleViewModel<T> {
    val value: StateFlow<DataWrapper<T>>
    val isMore: StateFlow<Boolean>
    val requestUrl: String
    fun onRefresh()
    fun request(url: String)
    suspend fun http(url: String): T?
}

/**
 * 一次请求多次刷新
 * [initializeUrl]初始化的url，可通过重写[requestUrl]在某些特殊情况下更新Url
 * [initializeRefresh]是否初始化刷新
 * [isMore]适用于一次请求后其他数据不变,某些数据需要通过按钮点击去控制更新
 */
abstract class SimpleSingleViewModel<T>(
    private val initializeUrl: String = "",
    private val initializeRefresh: Boolean = initializeUrl.isNotBlank(),
) : ViewModel(), SingleViewModel<T> {

    init {
        if (initializeRefresh) {
            composeHandlerPost {
                onRefresh()
            }
        }
    }

    private val _value = dataWrapperStateFlow<T>(DataWrapper.Normal)
    override val value: StateFlow<DataWrapper<T>> get() = _value
    private val _isMore = MutableStateFlow(false)
    override val isMore: StateFlow<Boolean> get() = _isMore

    override val requestUrl: String get() = initializeUrl

    val data get() = value.value.value

    override fun onRefresh() {
        if (shouldSkipRefresh()) return
        request(requestUrl)
    }

    override fun request(url: String) {
        Log.e("Print", "compose request http : $url ${isMoreRequest(url)}")
        if (isMoreRequest(url)) {
            updateIsMore(true)
        } else {
            _value.value = DataWrapper.Loading.Default
            updateIsMore(false)
        }
        composeLaunch(
            error = {
                Log.e("Print", "compose request http failure: ${it.message}")
                _value.value = DataWrapper.Failure.Default(it)
                updateIsMore(false)
            },
            scope = {
                val body = http(url)
                _value.value = if (body == null) DataWrapper.Empty.Default
                else DataWrapper.Success(body)
                updateIsMore(false)
            }
        )
    }

    private fun updateIsMore(value: Boolean) {
        if (isMore.value == value) return
        _isMore.value = value
    }

    private fun isMoreRequest(url: String): Boolean {
        return url != initializeUrl
    }

    private fun shouldSkipRefresh(): Boolean {
        return value.value.isLoading
                || requestUrl.isBlank()
                || requestUrl == REQUEST_END_MARK
    }

}

@Composable
fun <T, VM : SingleViewModel<T>> SimpleSingleBox(
    modifier: Modifier = Modifier,
    viewModel: VM,
    content: @Composable BoxScope.(DataWrapper.Success<T>) -> Unit,
) {
//    val state = rememberPullToRefreshState()
    val dataWrapper by viewModel.value.collectAsState()
    val isMore by viewModel.isMore.collectAsState()
    Box(
        modifier = Modifier
            .fillMaxSize()
//            .pullToRefresh(
//                state = state,
//                isRefreshing = isMore,
//                enabled = isMore,
//                onRefresh = {}
//            )
            .then(modifier)
    ) {
        when (dataWrapper) {
            is DataWrapper.Success -> content(dataWrapper as DataWrapper.Success<T>)
            is DataWrapper.Failure.Default -> SimpleStatusFailureScreen(retry = viewModel::onRefresh)
            DataWrapper.Empty.Default -> SimpleStatusEmptyScreen(empty = viewModel::onRefresh)
            DataWrapper.Loading.Default -> SimpleStatusLoadingDefaultScreen()
            DataWrapper.Loading.More -> {}
            is DataWrapper.Failure.More -> {}
            DataWrapper.Empty.More -> {}
            DataWrapper.Normal -> {}
        }
//        Indicator(
//            modifier = Modifier.align(Alignment.TopCenter),
//            isRefreshing = isMore,
//            state = state,
//            containerColor = Color.White,
//            color = colorPrimary,
//        )
        if (isMore) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }
    }
}