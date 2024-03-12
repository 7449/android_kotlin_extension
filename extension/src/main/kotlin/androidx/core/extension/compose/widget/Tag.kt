package androidx.core.extension.compose.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.extension.compose.colorPrimary
import androidx.core.extension.util.tag.TagItem
import androidx.core.extension.util.tag.TagModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun <T> SimpleFlowRowTag(tags: List<TagModel<T>>, onClick: (TagModel<T>) -> Unit) {
    FlowRow {
        tags.forEach { item ->
            SimpleTag(item) { onClick(item) }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SimpleFlowRowTagContent(tags: List<TagItem>, onClick: (TagItem) -> Unit) {
    val vertScrollState = rememberScrollState()
    FlowRow(modifier = Modifier.verticalScroll(vertScrollState)) {
        tags.forEach { item ->
            SimpleTag(item) { onClick(item) }
        }
    }
}

@Composable
fun SimpleTag(
    item: TagItem,
    color: Color = Color.White,
    fontSize: TextUnit = 8.sp,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .padding(5.dp)
            .clip(RoundedCornerShape(26.dp))
            .background(colorPrimary)
            .clickable(onClick = onClick)
    ) {
        Text(
            item.tagText,
            color = color,
            fontSize = fontSize,
            modifier = Modifier.padding(10.dp, 5.dp, 10.dp, 5.dp),
        )
    }
}