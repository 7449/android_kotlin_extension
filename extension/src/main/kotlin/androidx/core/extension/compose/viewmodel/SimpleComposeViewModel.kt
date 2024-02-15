package androidx.core.extension.compose.viewmodel

import androidx.core.extension.compose.dataStateOf
import androidx.core.extension.compose.error
import androidx.core.extension.compose.isNormal
import androidx.core.extension.compose.refresh
import androidx.core.extension.compose.refreshing
import androidx.core.extension.compose.success
import androidx.core.extension.http.DEFAULT_REQUEST_END_MARK
import androidx.core.extension.text.logE
import androidx.core.extension.viewmodel.launch
import androidx.lifecycle.ViewModel

abstract class SimpleComposeViewModel : ViewModel() {

    private val model = dataStateOf<Unit>()
    private var nextUrl = ""

    val isRefresh get() = model.refreshing()
    val isNormal get() = model.isNormal()

    open val firstRequestUrl: String = ""
    abstract suspend fun requestHttp(refresh: Boolean, url: String): String

    fun onRefresh() {
        nextUrl = firstRequestUrl
        request(true)
    }

    fun onLoadMore() {
        if (isRefresh) return
        if (nextUrl == DEFAULT_REQUEST_END_MARK) return
        request(false)
    }

    fun request(isRefresh: Boolean) {
        logE("request $nextUrl")
        model.refresh()
        launch(error = { model.error(it) }) {
            val result = requestHttp(isRefresh, nextUrl)
            model.success(Unit)
            nextUrl = result.ifBlank { DEFAULT_REQUEST_END_MARK }
        }
    }

}