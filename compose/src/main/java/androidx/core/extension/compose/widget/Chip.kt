package androidx.core.extension.compose.widget

private const val DefaultChipHeight = 26
private const val DefaultChipPadding = 2
private const val DefaultChipFontSize = 11

private const val SmallChipHeight = 18
private const val SmallChipPadding = 1
private const val SmallChipFontSize = 8

val FlowChipSize.chipHeight get() = if (this == FlowChipSize.Default) DefaultChipHeight else SmallChipHeight
val FlowChipSize.chipPadding get() = if (this == FlowChipSize.Default) DefaultChipPadding else SmallChipPadding
val FlowChipSize.chipFontSize get() = if (this == FlowChipSize.Default) DefaultChipFontSize else SmallChipFontSize

enum class FlowChipSize { Default, Small }