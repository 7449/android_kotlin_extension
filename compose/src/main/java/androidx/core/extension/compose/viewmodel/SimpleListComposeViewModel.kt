package androidx.core.extension.compose.viewmodel

import android.util.Log
import androidx.core.extension.compose.dataWrapperStateFlow
import androidx.core.extension.compose.widget.StatusListModel
import androidx.core.extension.compose.widget.statusHandler
import androidx.core.extension.http.DataWrapper
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow

abstract class SimpleListComposeViewModel<T>(
    private val initializeUrl: String = "",
    initializeRefresh: Boolean = initializeUrl.isNotBlank(),
) : ViewModel(), StatusListModel<T> {

    init {
        if (initializeRefresh) {
            statusHandler.post { onRefresh() }
        }
    }

    private val _value = dataWrapperStateFlow<List<T>>(DataWrapper.Normal)
    override val value: StateFlow<DataWrapper<List<T>>> get() = _value
    override val item: MutableList<T> = arrayListOf()

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
            item.clear()
        }
        _value.value = if (isRefresh) DataWrapper.Loading.Default
        else DataWrapper.Loading.More
        composeLaunch(
            error = {
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
                    item.addAll(result)
                    _value.value = if (currentUrl == REQUEST_END_MARK) DataWrapper.Empty.More
                    else DataWrapper.Success(result)
                    return@composeLaunch
                }
                _value.value = DataWrapper.Empty.More
            }
        )
    }

}