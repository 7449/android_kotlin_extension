package androidx.core.extension.compose.widget

import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.extension.compose.colorPrimary
import androidx.core.extension.http.DataWrapper
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun <T : Any, MODEL : StatusListModel<T>> SimpleStatusLazyScrollScreen(
    model: MODEL,
    contentAlignment: Alignment = Alignment.TopStart,
    propagateMinConstraints: Boolean = false,
    indicatorContentColor: Color = colorPrimary,
    indicatorScale: Boolean = false,
    content: @Composable BoxScope.() -> Unit,
) {
    val dataWrapper by model.value.collectAsState()
    val state = rememberPullRefreshState(
        refreshing = model.isLoading,
        onRefresh = model::onRefresh
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(state),
        contentAlignment = contentAlignment,
        propagateMinConstraints = propagateMinConstraints
    ) {
        content()
        when (dataWrapper) {
            is DataWrapper.Failure.Default -> SimpleStatusFailureScreen(retry = model::onRefresh)
            DataWrapper.Empty.Default -> SimpleStatusEmptyScreen(empty = model::onRefresh)
            DataWrapper.Loading.More -> {}
            is DataWrapper.Failure.More -> {}
            DataWrapper.Loading.Default -> {}
            is DataWrapper.Success -> {}
            DataWrapper.Empty.More -> {}
            DataWrapper.Normal -> {}
        }
        PullRefreshIndicator(
            refreshing = model.isLoading,
            state = state,
            contentColor = indicatorContentColor,
            scale = indicatorScale,
            modifier = Modifier.align(Alignment.TopCenter),
        )
    }
}

@Composable
internal fun BoxScope.SimpleStatusLoadingDefaultScreen() {
    CircularProgressIndicator(
        modifier = Modifier.align(Alignment.Center)
    )
}

@Composable
internal fun BoxScope.SimpleStatusEmptyScreen(empty: () -> Unit) {
    TextButton(
        onClick = empty,
        modifier = Modifier.align(Alignment.Center)
    ) { Text(text = "数据为空") }
}

@Composable
internal fun BoxScope.SimpleStatusFailureScreen(retry: () -> Unit) {
    TextButton(
        onClick = retry,
        modifier = Modifier.align(Alignment.Center)
    ) { Text(text = "网络请求出错啦") }
}

@Composable
internal fun SimpleStatusEmptyMoreScreen(empty: () -> Unit) {
    Box(modifier = Modifier.fillMaxWidth()) {
        TextButton(
            onClick = empty,
            modifier = Modifier.align(Alignment.Center)
        ) { Text(text = "没有更多的数据啦") }
    }
}

@Composable
internal fun SimpleStatusFailureMoreScreen(retry: () -> Unit) {
    Box(modifier = Modifier.fillMaxWidth()) {
        TextButton(
            onClick = retry,
            modifier = Modifier.align(Alignment.Center)
        ) { Text(text = "网络请求出错啦") }
    }
}

internal val statusHandler = Handler(Looper.getMainLooper())

interface StatusListModel<T> {
    val value: StateFlow<DataWrapper<MutableList<T>>>
    val item: StateFlow<MutableList<T>>
    val requestUrl: String
    fun onRefresh(retry: Boolean = false)
    fun onLoadMore(retry: Boolean = false)
    fun request(url: String, isRefresh: Boolean)
    suspend fun http(url: String, isRefresh: Boolean): Pair<MutableList<T>, String>
    val isLoading get() = value.value is DataWrapper.Loading
    val isFailure get() = value.value is DataWrapper.Failure
    val isEmpty get() = value.value is DataWrapper.Empty
    val isSuccess get() = value.value is DataWrapper.Success
}

interface StatusModel<T> {
    val value: StateFlow<DataWrapper<T>>
    val isMore: StateFlow<Boolean>
    val requestUrl: String
    fun onRefresh()
    fun request(url: String)
    suspend fun http(url: String): T?
    val isLoading get() = value.value is DataWrapper.Loading
    val isFailure get() = value.value is DataWrapper.Failure
    val isEmpty get() = value.value is DataWrapper.Empty
    val isSuccess get() = value.value is DataWrapper.Success
}