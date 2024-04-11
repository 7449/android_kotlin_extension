package androidx.core.extension.component.ui.activity

import android.os.Bundle
import androidx.core.extension.component.ui.DEFAULT_REQUEST_END_MARK
import androidx.core.extension.component.ui.recyclerview.SimpleOnLoadMoreListener
import androidx.core.extension.component.ui.recyclerview.addSimpleLoadMoreScroll
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.coroutines.launch

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
        lifecycleScope.launch {
            try {
                val result = requestHttp(isRefresh, nextUrl)
                swipeRefreshLayout.isRefreshing = false
                nextUrl = result.ifBlank { DEFAULT_REQUEST_END_MARK }
            } catch (ex: Exception) {
                ex.printStackTrace()
                if (lifecycle.currentState != Lifecycle.State.DESTROYED) {
                    httpFailure.invoke(ex)
                }
            }
        }
    }

}