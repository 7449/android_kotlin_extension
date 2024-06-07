package androidx.core.extension.compose.viewmodel

import android.util.Log
import androidx.core.extension.compose.dataWrapperStateFlow
import androidx.core.extension.compose.widget.StatusModel
import androidx.core.extension.compose.widget.statusHandler
import androidx.core.extension.http.DataWrapper
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

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

    val data get() = value.value.value

    override fun onRefresh() {
        if (shouldSkipRefresh()) return
        request(requestUrl)
    }

    override fun request(url: String) {
        Log.e("Print", "compose request http : $url")
        if (isMoreRequest(url)) {
            updateIsMore(true)
        } else {
            _value.value = DataWrapper.Loading.Default
            updateIsMore(false)
        }
        composeLaunch(
            error = {
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
                || requestUrl == DEFAULT_REQUEST_END_MARK
    }

}