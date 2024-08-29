package androidx.core.extension.sample

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.core.extension.compose.boolStateOf
import androidx.core.extension.compose.material3.CopyOrDownloadMaterial3Dialog
import androidx.core.extension.compose.material3.Material3WeightButton
import androidx.core.extension.compose.material3.SimpleMaterial3Dialog
import androidx.core.extension.compose.material3.SimpleMultiViewModel
import androidx.core.extension.compose.material3.SimpleSingleViewModel
import androidx.core.extension.compose.material3.SingleMaterial3InputDialog
import androidx.core.extension.compose.rememberDialog
import androidx.core.extension.compose.stringStateOf
import androidx.core.extension.compose.textFieldValueStateOf
import androidx.core.extension.compose.viewmodel.viewModel
import androidx.core.extension.os.mainHandler
import androidx.core.os.postDelayed
import kotlinx.coroutines.delay
import kotlin.random.Random

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
//    Column {
//        SimpleFilterChip(tagString, selected = true, onText = { it })
//        SimpleFlowRowHorizontalScrollFilterChip(
//            item = tagList,
//            onText = { it },
//            onSelect = { it.contains("2") }
//        )
//        Box(modifier = Modifier.height(100.dp)) {
//            SimpleFlowRowVerticalScrollFilterChip(
//                item = tagList,
//                onText = { it },
//                size = FlowChipSize.Small,
//                onSelect = { it.contains("2") }
//            )
//        }
//        SimpleChip(tagString, onText = { it })
//        SimpleFlowRowHorizontalScrollChip(
//            item = tagList,
//            onText = { it },
//        )
//        SimpleFlowRowChip(tagList, onText = { it })
//    }
}

@Composable
fun PreviewList3() {
//    val refresh = remember { boolStateOf(false) }
//    val items = remember { mutableStateList }
//    SimpleInfiniteList(
//        items = items,
//        refreshing = refresh.value,
//        onRefresh = {
//            refresh.value = true
//            mainHandler.postDelayed(1000) {
//                refresh.value = false
//                items.add(0, randomString)
//            }
//        },
//        onLoadMore = {
//            refresh.value = true
//            mainHandler.postDelayed(1000) {
//                refresh.value = false
//                items.add(randomString)
//            }
//        }
//    ) { Text(it) }
}

@Composable
fun PreviewGrid3() {
//    val refresh = remember { boolStateOf(false) }
//    val items = remember { mutableStateList }
//    SimpleInfiniteVerticalGrid(
//        items = items,
//        refreshing = refresh.value,
//        onRefresh = {
//            refresh.value = true
//            mainHandler.postDelayed(1000) {
//                refresh.value = false
//                items.add(0, randomString)
//            }
//        },
//        onLoadMore = {
//            refresh.value = true
//            mainHandler.postDelayed(1000) {
//                refresh.value = false
//                items.add(randomString)
//            }
//        }
//    ) { Text(it) }
}

@Composable
fun PreviewStaggeredGrid3() {
//    val refresh = remember { boolStateOf(false) }
//    val items = remember { mutableStateList }
//    SimpleInfiniteVerticalStaggeredGrid(
//        items = items,
//        refreshing = refresh.value,
//        onRefresh = {
//            refresh.value = true
//            mainHandler.postDelayed(1000) {
//                refresh.value = false
//                items.add(0, randomString)
//            }
//        },
//        onLoadMore = {
//            refresh.value = true
//            mainHandler.postDelayed(1000) {
//                refresh.value = false
//                items.add(randomString)
//            }
//        }
//    ) { Text(it) }
}

@Composable
fun PreviewSingleBox3(viewModel: SimplePreviewSingleViewModel = viewModel()) {
//    SimpleSingleBox(viewModel = viewModel) {
//        LazyColumn {
//            items(it.notNullData) {
//                Text(text = it, modifier = Modifier.align(Alignment.Center))
//            }
//            item {
//                SimpleTextButton("点击加载更多数据", onClick = viewModel::onClickMore)
//            }
//        }
//    }
}

@Composable
fun PreviewMultiList3(viewModel: SimplePreviewMultiViewModel = viewModel()) {
//    SimpleMultiList(viewModel = viewModel) {
//        Text(text = it, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
//    }
}

@Composable
fun PreviewMultiGrid3(viewModel: SimplePreviewMultiViewModel = viewModel()) {
//    SimpleMultiVerticalGrid(viewModel = viewModel) {
//        Text(text = it, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
//    }
}

@Composable
fun PreviewMultiStaggeredGrid3(viewModel: SimplePreviewMultiViewModel = viewModel()) {
//    SimpleMultiVerticalStaggeredGrid(viewModel = viewModel) {
//        Text(text = it, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
//    }
}

class SimplePreviewSingleViewModel : SimpleSingleViewModel<List<String>>(
    initializeUrl = "https://www.baidu.com"
) {
    private val _requestUrl = stringStateOf()

    override val requestUrl: String get() = _requestUrl.value.ifBlank { super.requestUrl }

    override suspend fun http(url: String): List<String> {
        delay(3000)
        return mutableStateList
    }

    fun onClickMore() {
        _requestUrl.value = "https://www.baidu.com${Random.nextInt()}"
        onRefresh()
    }
}

class SimplePreviewMultiViewModel : SimpleMultiViewModel<String>(
    initializeUrl = "https://www.baidu.com"
) {
    private var maxCount = 0
    override suspend fun http(url: String, isRefresh: Boolean): Pair<MutableList<String>, String> {
        delay(1000)
        return arrayListOf<String>().apply {
            if (maxCount < 5) {
                for (i in 0 until 20) {
                    add(i.toString())
                }
                maxCount++
            } else {
//                throw KotlinNullPointerException()
            }
        } to "https://www.baidu.com"
    }
}