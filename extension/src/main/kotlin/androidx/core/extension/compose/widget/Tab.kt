package androidx.core.extension.compose.widget

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.extension.compose.colorPrimary
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun <T> SimpleTabLayout(
    items: List<T>,
    title: (T) -> String,
    lazy: Boolean = false,
    content: @Composable (Int, T) -> Unit,
) {
    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f,
        pageCount = { items.size }
    )
    val coroutineScope = rememberCoroutineScope()
    if (items.isEmpty()) return
    Column {
        ScrollableTabRow(
            backgroundColor = colorPrimary,
            contentColor = Color.White,
            edgePadding = 0.dp,
            selectedTabIndex = pagerState.currentPage
        ) {
            items.forEachIndexed { index, item ->
                Tab(
                    text = { Text(title.invoke(item), color = Color.White) },
                    selected = pagerState.currentPage == index,
                    onClick = { coroutineScope.launch { pagerState.animateScrollToPage(index) } },
                )
            }
        }
        HorizontalPager(
            pageSpacing = 0.dp,
            beyondBoundsPageCount = 2,
            pageSize = PageSize.Fill,
            state = pagerState
        ) { index ->
            if (lazy) {
                if (index == pagerState.currentPage) {
                    content(index, items[index])
                }
            } else {
                content(index, items[index])
            }
        }
    }
}