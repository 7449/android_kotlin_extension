@file:OptIn(ExperimentalMaterialApi::class, ExperimentalLayoutApi::class)

package androidx.core.extension.compose.material

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
import androidx.core.extension.compose.widget.FlowChipSize
import androidx.core.extension.compose.widget.chipFontSize
import androidx.core.extension.compose.widget.chipHeight
import androidx.core.extension.compose.widget.chipPadding

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

@Composable
fun <T> SimpleChip(
    item: T,
    onText: (T) -> String,
    size: FlowChipSize = FlowChipSize.Default,
    onClick: () -> Unit = {},
) {
    Chip(
        modifier = Modifier
            .height(size.chipHeight.dp)
            .padding(size.chipPadding.dp),
        onClick = onClick,
    ) {
        Text(
            text = onText(item),
            fontSize = size.chipFontSize.sp,
            style = TextStyle(platformStyle = PlatformTextStyle(includeFontPadding = false))
        )
    }
}

@Composable
fun <T> SimpleFlowRowFilterChip(
    item: List<T>,
    onText: (T) -> String,
    onSelect: (T) -> Boolean,
    size: FlowChipSize = FlowChipSize.Default,
    onClick: (T) -> Unit = {},
) {
    FlowRow {
        item.forEach { item ->
            SimpleFilterChip(
                item = item,
                selected = onSelect(item),
                size = size,
                onText = onText
            ) { onClick(item) }
        }
    }
}

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
        modifier = Modifier
            .height(size.chipHeight.dp)
            .padding(size.chipPadding.dp),
        onClick = onClick,
    ) {
        Text(
            onText(item),
            fontSize = size.chipFontSize.sp,
            style = TextStyle(platformStyle = PlatformTextStyle(includeFontPadding = false))
        )
    }
}
