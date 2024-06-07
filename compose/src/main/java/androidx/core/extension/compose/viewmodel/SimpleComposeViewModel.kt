package androidx.core.extension.compose.viewmodel

import android.util.Log
import androidx.core.extension.compose.dataWrapperStateFlow
import androidx.core.extension.compose.mutableStateListFlow
import androidx.core.extension.compose.widget.StatusListModel
import androidx.core.extension.compose.widget.StatusModel
import androidx.core.extension.compose.widget.statusHandler
import androidx.core.extension.http.DataWrapper
import androidx.core.extension.http.getSuccessOrNull
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
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

    private val _value = dataWrapperStateFlow<MutableList<T>>(DataWrapper.Normal)
    override val value: StateFlow<DataWrapper<MutableList<T>>> get() = _value
    private val _item = mutableStateListFlow(mutableListOf<T>())
    override val item: StateFlow<MutableList<T>> get() = _item

    override val requestUrl: String get() = initializeUrl
    private var currentUrl: String = initializeUrl

    override fun onRefresh(retry: Boolean) {
        if (isLoading) return
        currentUrl = requestUrl
        request(currentUrl, true)
    }

    override fun onLoadMore(retry: Boolean) {
        if (retry) {
            request(currentUrl, false)
        } else if (isSuccess && currentUrl != DEFAULT_REQUEST_END_MARK) {
            request(currentUrl, false)
        }
    }

    override fun request(url: String, isRefresh: Boolean) {
        Log.e("Print", "compose request http : $url")
        if (isRefresh) {
            _item.value.clear()
        }
        _value.value = if (isRefresh) DataWrapper.Loading.Default
        else DataWrapper.Loading.More
        composeLaunch(
            error = {
                _value.value = if (isRefresh) DataWrapper.Failure.Default(it)
                else DataWrapper.Failure.More(it)
            },
            scope = {
                val body = http(url, isRefresh)
                val result = body.first
                _value.value = if (isRefresh && result.isEmpty()) {
                    DataWrapper.Empty.Default
                } else if (!isRefresh && result.isEmpty()) {
                    DataWrapper.Empty.More
                } else {
                    currentUrl = body.second.ifBlank { DEFAULT_REQUEST_END_MARK }
                    _item.value.addAll(result)
                    DataWrapper.Success(result)
                }
            }
        )
    }

}

abstract class SimpleComposeViewModel<T>(
    private val initializeUrl: String = "",
    initializeRefresh: Boolean = initializeUrl.isNotBlank(),
) : ViewModel(), StatusModel<T> {

    init {
        if (initializeRefresh) {
            statusHandler.post { onRefresh() }
        }
    }

    private val _value = dataWrapperStateFlow<T>(DataWrapper.Normal)
    override val value: StateFlow<DataWrapper<T>> get() = _value
    private val _isMore = MutableStateFlow(false)
    override val isMore: StateFlow<Boolean> get() = _isMore

    override val requestUrl: String get() = initializeUrl

    val data get() = value.value.getSuccessOrNull()?.data

    override fun onRefresh() {
        if (isLoading || requestUrl.isBlank() || requestUrl == DEFAULT_REQUEST_END_MARK) return
        request(requestUrl)
    }

    override fun request(url: String) {
        Log.e("Print", "compose request http : $url")
        if (url == initializeUrl) {
            _isMore.value = false
            _value.value = DataWrapper.Loading.Default
        } else {
            _isMore.value = true
        }
        composeLaunch(
            error = {
                _isMore.value = false
                _value.value = DataWrapper.Failure.Default(it)
            },
            scope = {
                val body = http(url)
                _value.value = if (body == null) DataWrapper.Empty.Default
                else DataWrapper.Success(body)
                _isMore.value = false
            }
        )
    }

}