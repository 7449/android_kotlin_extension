@file:OptIn(ExperimentalMaterialApi::class)

package androidx.core.extension.compose.material

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.extension.compose.colorPrimary

@Composable
fun SimpleInfiniteBox(
    modifier: Modifier = Modifier,
    refreshing: Boolean = false,
    onRefresh: () -> Unit = {},
    content: @Composable BoxScope.() -> Unit,
) {
    val state = rememberPullRefreshState(
        refreshing = refreshing,
        onRefresh = onRefresh
    )
    Box(
        modifier = modifier.then(Modifier.pullRefresh(state).fillMaxSize()),
        contentAlignment = Alignment.TopStart,
    ) {
        content()
        PullRefreshIndicator(
            refreshing = refreshing,
            state = state,
            contentColor = colorPrimary,
            scale = false,
            modifier = Modifier.align(Alignment.TopCenter),
        )
    }
}
