package androidx.core.extension.compose.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.core.extension.compose.composeHandlerPost
import androidx.core.extension.compose.dataWrapperStateFlow
import androidx.core.extension.wrapper.http.DataWrapper
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