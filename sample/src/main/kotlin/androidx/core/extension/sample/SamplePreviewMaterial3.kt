package androidx.core.extension.sample

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.extension.compose.boolStateOf
import androidx.core.extension.compose.material3.CopyOrDownloadMaterial3Dialog
import androidx.core.extension.compose.material3.Material3WeightButton
import androidx.core.extension.compose.material3.SimpleMaterial3Dialog
import androidx.core.extension.compose.material3.SingleMaterial3InputDialog
import androidx.core.extension.compose.rememberDialog
import androidx.core.extension.compose.stringStateOf
import androidx.core.extension.compose.textFieldValueStateOf
import androidx.core.extension.compose.viewmodel.viewModel
import androidx.core.extension.compose.widget.FlowChipSize
import androidx.core.extension.os.mainHandler
import androidx.core.os.postDelayed

@Composable
fun PreviewToolbar3() {
    Column {
        androidx.core.extension.compose.material3.SimpleBackToolbar(
            stringResource(R.string.app_name),
            back = {}
        )
        androidx.core.extension.compose.material3.SimpleBackSearchToolbar(
            stringResource(R.string.app_name),
            back = {},
            search = {}
        )
    }
}

@Composable
fun PreviewButton3() {
    Column {
        val currentSelect = remember { stringStateOf(radioBtnWidget.first()) }
        Row {
            radioBtnWidget.forEach { it ->
                androidx.core.extension.compose.material3.SimpleRadioButton(
                    text = it,
                    selected = currentSelect.value,
                    modifier = Modifier.weight(1f)
                ) { currentSelect.value = it }
            }
        }
        androidx.core.extension.compose.material3.SimpleIconButton(
            imageVector = Icons.Default.Add,
            tint = Color.Black
        )
        androidx.core.extension.compose.material3.SimpleButton("Button", onClick = {})
        androidx.core.extension.compose.material3.SimpleTextButton("TextButton", onClick = {})
    }
}

@Composable
fun PreviewInput3() {
    val value = remember { textFieldValueStateOf(TextFieldValue()) }
    androidx.core.extension.compose.material3.SimpleInput(
        label = stringResource(R.string.app_name),
        value = value.value,
        onValueChange = { value.value = it },
        keyboardAction = {
        }
    )
}

@Composable
fun PreviewDialog3() {
    Column {
        val dialog = rememberDialog {
            SimpleMaterial3Dialog("Test", ok = hide)
        }
        val inputDialog = rememberDialog {
            SingleMaterial3InputDialog {
            }
        }
        val copyOrDownload = rememberDialog {
            CopyOrDownloadMaterial3Dialog(
                "Test",
                onClickCopy = {},
                onClickDownload = {}
            )
        }
        androidx.core.extension.compose.material3.SimpleButton("ShowDialog") {
            dialog.show()
        }
        androidx.core.extension.compose.material3.SimpleButton("ShowInputDialog") {
            inputDialog.show()
        }
        androidx.core.extension.compose.material3.SimpleButton("ShowCopyOrDownloadDialog") {
            copyOrDownload.show()
        }
    }
}

@Composable
fun PreviewRow3() {
    Row {
        Material3WeightButton("one")
        Material3WeightButton("two")
        Material3WeightButton("three")
    }
}

@Composable
fun PreviewColumn3() {
    androidx.core.extension.compose.material3.ColumnSmallText(item = tagList)
}

@Composable
fun PreviewText3() {
    androidx.core.extension.compose.material3.LabelText(
        prefix = "前缀",
        label = tagList,
        onTextValue = { it },
        onClick = {}
    )
}

@Composable
fun PreviewCard3() {
    Column {
        androidx.core.extension.compose.material3.SimpleCardRow(modifier = Modifier.padding(5.dp)) {
            androidx.core.extension.compose.material3.SimpleText("Card1")
            androidx.core.extension.compose.material3.SimpleText("Card2")
        }
        androidx.core.extension.compose.material3.SimpleCardColumn(modifier = Modifier.padding(5.dp)) {
            androidx.core.extension.compose.material3.SimpleText("Card1")
            androidx.core.extension.compose.material3.SimpleText("Card2")
        }
        androidx.core.extension.compose.material3.SimpleCardBox(modifier = Modifier.padding(5.dp)) {
            androidx.core.extension.compose.material3.SimpleText("Card1")
            androidx.core.extension.compose.material3.SimpleText("Card2")
        }
        androidx.core.extension.compose.material3.SimpleCard {
            androidx.core.extension.compose.material3.SimpleText("Card1")
        }
    }
}

@Composable
fun PreviewTab3() {
    androidx.core.extension.compose.material3.SimpleTabLayout(
        items = tabList,
        title = { it },
        content = { _, value ->
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                androidx.core.extension.compose.material3.SimpleText(value)
            }
        }
    )
}

@Composable
fun PreviewBox3() {
    val refresh = remember { boolStateOf(false) }
    val items = remember { mutableStateList }
    androidx.core.extension.compose.material3.SimpleInfiniteBox(
        refreshing = refresh.value,
        onRefresh = {
            refresh.value = true
            mainHandler.postDelayed(1000) {
                refresh.value = false
                items.add(0, randomString)
            }
        }
    ) {
        SampleLazyColumn(items) {
            androidx.core.extension.compose.material3.SimpleText(
                text = it,
                modifier = Modifier.padding(5.dp)
            )
        }
    }
}

@Composable
fun PreviewChip3() {
    Column {
        androidx.core.extension.compose.material3.SimpleFilterChip(
            tagString,
            selected = true,
            onText = { it }
        )
        androidx.core.extension.compose.material3.SimpleFlowRowHorizontalScrollFilterChip(
            item = tagList,
            onText = { it },
            onSelect = { it.contains("2") }
        )
        Box(modifier = Modifier.height(100.dp)) {
            androidx.core.extension.compose.material3.SimpleFlowRowVerticalScrollFilterChip(
                item = tagList,
                onText = { it },
                size = FlowChipSize.Small,
                onSelect = { it.contains("2") }
            )
        }
        androidx.core.extension.compose.material3.SimpleChip(tagString, onText = { it })
        androidx.core.extension.compose.material3.SimpleFlowRowChip(tagList, onText = { it })
        androidx.core.extension.compose.material3.SimpleFlowRowHorizontalScrollChip(
            item = tagList,
            onText = { it },
        )
    }
}

@Composable
fun PreviewList3() {
    val refresh = remember { boolStateOf(false) }
    val items = remember { mutableStateList }
    androidx.core.extension.compose.material3.SimpleInfiniteList(
        items = items,
        refreshing = refresh.value,
        onRefresh = {
            refresh.value = true
            mainHandler.postDelayed(1000) {
                refresh.value = false
                items.add(0, randomString)
            }
        },
        onLoadMore = {
            refresh.value = true
            mainHandler.postDelayed(1000) {
                refresh.value = false
                items.add(randomString)
            }
        }
    ) { androidx.core.extension.compose.material3.SimpleText(it) }
}

@Composable
fun PreviewGrid3() {
    val refresh = remember { boolStateOf(false) }
    val items = remember { mutableStateList }
    androidx.core.extension.compose.material3.SimpleInfiniteVerticalGrid(
        items = items,
        refreshing = refresh.value,
        onRefresh = {
            refresh.value = true
            mainHandler.postDelayed(1000) {
                refresh.value = false
                items.add(0, randomString)
            }
        },
        onLoadMore = {
            refresh.value = true
            mainHandler.postDelayed(1000) {
                refresh.value = false
                items.add(randomString)
            }
        }
    ) { androidx.core.extension.compose.material3.SimpleText(it) }
}

@Composable
fun PreviewStaggeredGrid3() {
    val refresh = remember { boolStateOf(false) }
    val items = remember { mutableStateList }
    androidx.core.extension.compose.material3.SimpleInfiniteVerticalStaggeredGrid(
        items = items,
        refreshing = refresh.value,
        onRefresh = {
            refresh.value = true
            mainHandler.postDelayed(1000) {
                refresh.value = false
                items.add(0, randomString)
            }
        },
        onLoadMore = {
            refresh.value = true
            mainHandler.postDelayed(1000) {
                refresh.value = false
                items.add(randomString)
            }
        }
    ) { androidx.core.extension.compose.material3.SimpleText(it) }
}

@Composable
fun PreviewSingleBox3(viewModel: SimplePreviewSingleViewModel = viewModel()) {
    androidx.core.extension.compose.material3.SimpleSingleBox(viewModel = viewModel) {
        LazyColumn {
            items(it.notNullData) {
                androidx.core.extension.compose.material3.SimpleText(
                    text = it,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            item {
                androidx.core.extension.compose.material3.SimpleTextButton(
                    "点击加载更多数据",
                    onClick = viewModel::onClickMore
                )
            }
        }
    }
}

@Composable
fun PreviewMultiList3(viewModel: SimplePreviewMultiViewModel = viewModel()) {
    androidx.core.extension.compose.material3.SimpleMultiList(viewModel = viewModel) {
        androidx.core.extension.compose.material3.SimpleText(
            text = it,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun PreviewMultiGrid3(viewModel: SimplePreviewMultiViewModel = viewModel()) {
    androidx.core.extension.compose.material3.SimpleMultiVerticalGrid(viewModel = viewModel) {
        androidx.core.extension.compose.material3.SimpleText(
            text = it,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun PreviewMultiStaggeredGrid3(viewModel: SimplePreviewMultiViewModel = viewModel()) {
    androidx.core.extension.compose.material3.SimpleMultiVerticalStaggeredGrid(viewModel = viewModel) {
        androidx.core.extension.compose.material3.SimpleText(
            text = it,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}