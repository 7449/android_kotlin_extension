package androidx.core.extension.recyclerview.ui

import android.os.Bundle
import android.view.View
import androidx.core.extension.recyclerview.SimpleOnLoadMoreListener
import androidx.core.extension.recyclerview.addSimpleLoadMoreScroll
import androidx.core.extension.viewmodel.launch
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

@Deprecated("Use Compose")
abstract class SimpleHttpListFragment(layoutId: Int) : Fragment(layoutId),
    SwipeRefreshLayout.OnRefreshListener, SimpleOnLoadMoreListener {

    private val httpFailure = { _: Throwable -> swipeRefreshLayout.isRefreshing = false }
    private var nextUrl = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewCreatedBefore(view, savedInstanceState)
        recyclerView.addSimpleLoadMoreScroll(this)
        swipeRefreshLayout.setOnRefreshListener(this)
        if (immediatelyRefresh) {
            swipeRefreshLayout.post { onRefresh() }
        }
        onViewCreatedAfter(view, savedInstanceState)
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

    abstract fun onViewCreatedBefore(view: View, savedInstanceState: Bundle?)
    open fun onViewCreatedAfter(view: View, savedInstanceState: Bundle?) {}

    abstract suspend fun requestHttp(refresh: Boolean, url: String): String

    fun request(isRefresh: Boolean) {
        swipeRefreshLayout.isRefreshing = true
        launch(httpFailure) {
            val result = requestHttp(isRefresh, nextUrl)
            swipeRefreshLayout.isRefreshing = false
            nextUrl = result.ifBlank { DEFAULT_REQUEST_END_MARK }
        }
    }

}