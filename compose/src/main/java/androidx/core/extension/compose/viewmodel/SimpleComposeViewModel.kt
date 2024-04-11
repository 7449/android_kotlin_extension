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

    private val model = dataStateOf<Unit>()
    private var nextUrl = ""

    val isRefresh get() = model.refreshing()
    val isNormal get() = model.isNormal()

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
        model.refresh()
        composeLaunch(error = { model.error(it) }) {
            val result = requestHttp(isRefresh, url)
            model.success(Unit)
            nextUrl = result.ifBlank { DEFAULT_REQUEST_END_MARK }
        }
    }

}