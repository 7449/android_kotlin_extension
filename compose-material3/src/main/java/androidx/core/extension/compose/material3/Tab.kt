package androidx.core.extension.compose.material3

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerScope
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.extension.compose.colorPrimary
import kotlinx.coroutines.launch

@Composable
fun <T> SimpleTabLayout(
    items: List<T>,
    title: (T) -> String,
    beyondViewportPageCount: Int = PagerDefaults.BeyondViewportPageCount,
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
            containerColor = colorPrimary,
            contentColor = Color.White,
            edgePadding = 0.dp,
            selectedTabIndex = pagerState.currentPage,
            indicator = {
                TabRowDefaults.SecondaryIndicator(
                    color = Color.White,
                    modifier = Modifier.tabIndicatorOffset(it[pagerState.currentPage])
                )
            },
            divider = {
                HorizontalDivider(color = colorPrimary)
            }
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
            beyondViewportPageCount = beyondViewportPageCount,
            pageSize = PageSize.Fill,
            state = pagerState
        ) { index ->
            content(index, items[index])
        }
    }
}