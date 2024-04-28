package androidx.core.extension.compose.widget

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Chip
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FilterChip
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private const val DefaultChipHeight = 26
private const val DefaultChipPadding = 2
private const val DefaultChipFontSize = 11

private const val SmallChipHeight = 18
private const val SmallChipPadding = 1
private const val SmallChipFontSize = 8

private val FlowChipSize.chipHeight get() = if (this == FlowChipSize.Default) DefaultChipHeight else SmallChipHeight
private val FlowChipSize.chipPadding get() = if (this == FlowChipSize.Default) DefaultChipPadding else SmallChipPadding
private val FlowChipSize.chipFontSize get() = if (this == FlowChipSize.Default) DefaultChipFontSize else SmallChipFontSize

enum class FlowChipSize { Default, Small }

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun <T> SimpleFlowRowChip(
    item: List<T>,
    onText: (T) -> String,
    size: FlowChipSize = FlowChipSize.Default,
    onClick: (T) -> Unit = {},
) {
    FlowRow {
        item.forEach { item ->
            SimpleChip(item, size = size, onText = onText) { onClick(item) }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun <T> SimpleFlowRowHorizontalScrollChip(
    item: List<T>,
    onText: (T) -> String,
    size: FlowChipSize = FlowChipSize.Default,
    onClick: (T) -> Unit = {},
) {
    FlowRow(modifier = Modifier.horizontalScroll(rememberScrollState())) {
        item.forEach { item ->
            SimpleChip(item, size = size, onText = onText) { onClick(item) }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun <T> SimpleFlowRowVerticalScrollChip(
    item: List<T>,
    onText: (T) -> String,
    size: FlowChipSize = FlowChipSize.Default,
    onClick: (T) -> Unit = {},
) {
    FlowRow(modifier = Modifier.verticalScroll(rememberScrollState())) {
        item.forEach { item ->
            SimpleChip(item, size = size, onText = onText) { onClick(item) }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun <T> SimpleChip(
    item: T,
    onText: (T) -> String,
    size: FlowChipSize = FlowChipSize.Default,
    onClick: () -> Unit = {},
) {
    Chip(
        modifier = Modifier.height(size.chipHeight.dp).padding(size.chipPadding.dp),
        onClick = onClick,
    ) {
        Text(
            text = onText(item),
            fontSize = size.chipFontSize.sp,
            style = TextStyle(platformStyle = PlatformTextStyle(includeFontPadding = false))
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun <T> SimpleFlowRowHorizontalScrollFilterChip(
    item: List<T>,
    onText: (T) -> String,
    onSelect: (T) -> Boolean,
    size: FlowChipSize = FlowChipSize.Default,
    onClick: (T) -> Unit = {},
) {
    FlowRow(modifier = Modifier.horizontalScroll(rememberScrollState())) {
        item.forEach {
            SimpleFilterChip(
                item = it,
                selected = onSelect(it),
                onText = onText,
                size = size,
                onClick = { onClick(it) }
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun <T> SimpleFlowRowVerticalScrollFilterChip(
    item: List<T>,
    onText: (T) -> String,
    onSelect: (T) -> Boolean,
    size: FlowChipSize = FlowChipSize.Default,
    onClick: (T) -> Unit = {},
) {
    FlowRow(modifier = Modifier.verticalScroll(rememberScrollState())) {
        item.forEach {
            SimpleFilterChip(
                item = it,
                selected = onSelect(it),
                onText = onText,
                size = size,
                onClick = { onClick(it) }
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun <T> SimpleFilterChip(
    item: T,
    onText: (T) -> String,
    selected: Boolean = false,
    size: FlowChipSize = FlowChipSize.Default,
    onClick: () -> Unit = {},
) {
    FilterChip(
        selected = selected,
        modifier = Modifier.height(size.chipHeight.dp).padding(size.chipPadding.dp),
        onClick = onClick,
    ) {
        Text(
            onText(item),
            fontSize = size.chipFontSize.sp,
            style = TextStyle(platformStyle = PlatformTextStyle(includeFontPadding = false))
        )
    }
}
