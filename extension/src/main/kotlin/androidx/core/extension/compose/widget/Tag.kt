package androidx.core.extension.compose.widget

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.extension.compose.colorPrimary
import androidx.core.extension.compose.horizontalPadding
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SimpleTag(
    item: TagItem,
    color: Color = Color.White,
    onClick: () -> Unit,
) {
    Chip(
        modifier = Modifier.horizontalPadding(3.dp),
        onClick = onClick,
        border = BorderStroke(1.dp, colorPrimary.copy(alpha = 0.9f)),
        colors = ChipDefaults.chipColors(
            backgroundColor = colorPrimary,
            contentColor = Color.White
        )
    ) {
        Text(
            item.tagText,
            color = color,
        )
    }
}