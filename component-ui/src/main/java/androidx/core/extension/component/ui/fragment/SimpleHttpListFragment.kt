package androidx.core.extension.component.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.core.extension.component.ui.DEFAULT_REQUEST_END_MARK
import androidx.core.extension.component.ui.recyclerview.SimpleOnLoadMoreListener
import androidx.core.extension.component.ui.recyclerview.addSimpleLoadMoreScroll
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.coroutines.launch

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