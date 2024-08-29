package androidx.core.extension.compose.material

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerScope
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.extension.compose.colorPrimary
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun <T> SimpleTabLayout(
    items: List<T>,
    title: (T) -> String,
    beyondBoundsPageCount: Int = PagerDefaults.BeyondBoundsPageCount,
    content: @Composable PagerScope.(Int, T) -> Unit,
) {
    if (items.isEmpty()) return
    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f,
        pageCount = { items.size }
    )
    val coroutineScope = rememberCoroutineScope()
    Column(modifier = Modifier.fillMaxSize()) {
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
            beyondBoundsPageCount = beyondBoundsPageCount,
            pageSize = PageSize.Fill,
            state = pagerState
        ) { index ->
            content(index, items[index])
        }
    }
}