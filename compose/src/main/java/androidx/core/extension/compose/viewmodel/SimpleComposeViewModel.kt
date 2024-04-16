package androidx.core.extension.compose.viewmodel

import android.util.Log
import androidx.annotation.CallSuper
import androidx.core.extension.compose.dataStateOf
import androidx.core.extension.compose.error
import androidx.core.extension.compose.isNormal
import androidx.core.extension.compose.refresh
import androidx.core.extension.compose.refreshing
import androidx.core.extension.compose.success
import androidx.lifecycle.ViewModel

abstract class SimpleComposeViewModel : ViewModel() {

    private val _model = dataStateOf<Unit>()
    private var nextUrl = ""

    val isRefresh get() = _model.refreshing()
    val isNormal get() = _model.isNormal()
    val model get() = _model.value

    open val firstRequestUrl: String = ""
    abstract suspend fun requestHttp(refresh: Boolean, url: String): String

    @CallSuper
    open fun onRefresh() {
        nextUrl = firstRequestUrl
        request(true, nextUrl)
    }

    @CallSuper
    open fun onLoadMore() {
        if (isRefresh) return
        if (nextUrl == DEFAULT_REQUEST_END_MARK) return
        request(false, nextUrl)
    }

    fun request(isRefresh: Boolean, url: String) {
        Log.e("Print", "compose request http : $url")
        _model.refresh()
        composeLaunch(error = { _model.error(it) }) {
            val result = requestHttp(isRefresh, url)
            _model.success(Unit)
            nextUrl = result.ifBlank { DEFAULT_REQUEST_END_MARK }
        }
    }

}