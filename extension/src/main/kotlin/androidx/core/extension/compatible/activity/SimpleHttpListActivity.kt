package androidx.core.extension.compatible.activity

import android.os.Bundle
import androidx.core.extension.http.DEFAULT_REQUEST_END_MARK
import androidx.core.extension.viewmodel.launch
import androidx.core.extension.widget.addSimpleLoadMoreScroll
import androidx.core.extension.widget.recyclerview.SimpleOnLoadMoreListener
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

@Deprecated("Use Compose")
abstract class SimpleHttpListActivity(layoutId: Int) : SimpleKernelActivity(layoutId),
    SwipeRefreshLayout.OnRefreshListener, SimpleOnLoadMoreListener {

    private val httpFailure = { _: Throwable -> swipeRefreshLayout.isRefreshing = false }
    private var nextUrl = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onCreateBefore(savedInstanceState)
        recyclerView.addSimpleLoadMoreScroll(this)
        swipeRefreshLayout.setOnRefreshListener(this)
        if (immediatelyRefresh) {
            swipeRefreshLayout.post { onRefresh() }
        }
        onCreateAfter(savedInstanceState)
    }

    override fun onRefresh() {
        nextUrl = firstRequestUrl
        request(true)
    }

    override fun onLoadMore() {
        if (nextUrl == DEFAULT_REQUEST_END_MARK) return
        if (swipeRefreshLayout.isRefreshing) return
        request(false)
    }

    open val immediatelyRefresh: Boolean = true
    open val firstRequestUrl: String = ""

    abstract val swipeRefreshLayout: SwipeRefreshLayout
    abstract val recyclerView: RecyclerView

    abstract fun onCreateBefore(savedInstanceState: Bundle?)
    open fun onCreateAfter(savedInstanceState: Bundle?) {}

    abstract suspend fun requestHttp(refresh: Boolean, url: String): String

    private fun request(isRefresh: Boolean) {
        swipeRefreshLayout.isRefreshing = true
        launch(httpFailure) {
            val result = requestHttp(isRefresh, nextUrl)
            swipeRefreshLayout.isRefreshing = false
            nextUrl = result.ifBlank { DEFAULT_REQUEST_END_MARK }
        }
    }

}