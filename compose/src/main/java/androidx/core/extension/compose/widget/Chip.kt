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
import java.io.Serializable

interface ChipItem : Serializable {
    val chipText: String
    val chipSelect: Boolean get() = false
}

data class ChipModel<T>(
    val parent: T,
    val text: String,
    val select: Boolean = false,
) : ChipItem {
    override val chipText: String
        get() = text
    override val chipSelect: Boolean
        get() = select
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun <T> SimpleFlowRowChip(tags: List<ChipModel<T>>, onClick: (ChipModel<T>) -> Unit) {
    FlowRow {
        tags.forEach { item ->
            SimpleChip(item) { onClick(item) }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SimpleFlowRowScrollChip(tags: List<ChipItem>, onClick: (ChipItem) -> Unit) {
    val vertScrollState = rememberScrollState()
    FlowRow(modifier = Modifier.verticalScroll(vertScrollState)) {
        tags.forEach { item ->
            SimpleChip(item) { onClick(item) }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SimpleChip(
    item: ChipItem,
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
            item.chipText,
            color = color,
        )
    }
}