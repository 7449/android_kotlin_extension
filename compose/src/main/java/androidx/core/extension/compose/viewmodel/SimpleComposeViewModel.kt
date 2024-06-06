package androidx.core.extension.compose.viewmodel

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.annotation.CallSuper
import androidx.core.extension.compose.dataWrapperStateFlowOf
import androidx.core.extension.http.DataWrapper
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow

abstract class SimpleListComposeViewModel<T>(
    private val initializeUrl: String = "",
    initializeRefresh: Boolean = initializeUrl.isNotBlank(),
    value: DataWrapper<List<T>> = DataWrapper.Normal,
) : SimpleComposeViewModel<List<T>>(initializeUrl, initializeRefresh, value) {
    abstract override suspend fun requestHttp(refresh: Boolean, url: String): Pair<List<T>, String>
}

abstract class SimpleComposeViewModel<T>(
    private val initializeUrl: String = "",
    initializeRefresh: Boolean = initializeUrl.isNotBlank(),
    value: DataWrapper<T> = DataWrapper.Normal,
) : ViewModel() {

    companion object {
        private val viewModelHandler = Handler(Looper.getMainLooper())
    }

    init {
        if (initializeRefresh) {
            viewModelHandler.post { onRefresh() }
        }
    }

    private val _value = dataWrapperStateFlowOf(value)
    val value: StateFlow<DataWrapper<T>> get() = _value

    private var nextUrl = ""
    open val firstRequestUrl: String get() = initializeUrl
    abstract suspend fun requestHttp(refresh: Boolean, url: String): Pair<T?, String>

    @CallSuper
    open fun onRefresh() {
        if (value is DataWrapper.Loading) return
        nextUrl = firstRequestUrl
        request(true, nextUrl)
    }

    @CallSuper
    open fun onLoadMore() {
        if (value is DataWrapper.Loading) return
        if (nextUrl == DEFAULT_REQUEST_END_MARK) return
        request(false, nextUrl)
    }

    fun request(isRefresh: Boolean, url: String) {
        Log.e("Print", "compose request http : $url")
        _value.value = if (isRefresh) DataWrapper.Loading.Default
        else DataWrapper.Loading.More
        composeLaunch(
            error = {
                _value.value = if (isRefresh) DataWrapper.Failure.Default(it)
                else DataWrapper.Failure.More(it)
            },
            scope = {
                val body = requestHttp(isRefresh, url)
                val result = body.first
                _value.value = if (result == null) {
                    DataWrapper.Empty.Default
                } else if (result is List<*> && isRefresh && result.isEmpty()) {
                    DataWrapper.Empty.Default
                } else if (result is List<*> && !isRefresh && result.isEmpty()) {
                    DataWrapper.Empty.More
                } else {
                    DataWrapper.Success(result)
                }
                nextUrl = body.second.ifBlank { DEFAULT_REQUEST_END_MARK }
            }
        )
    }

}