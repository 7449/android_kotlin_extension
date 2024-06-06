package androidx.core.extension.sample

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.extension.compose.boolStateOf
import androidx.core.extension.compose.rememberDialog
import androidx.core.extension.compose.stringStateOf
import androidx.core.extension.compose.textFieldValueStateOf
import androidx.core.extension.compose.viewmodel.SimpleComposeViewModel
import androidx.core.extension.compose.viewmodel.viewModel
import androidx.core.extension.compose.widget.ColumnSmallText
import androidx.core.extension.compose.widget.CopyOrDownloadDialog
import androidx.core.extension.compose.widget.FlowChipSize
import androidx.core.extension.compose.widget.LabelText
import androidx.core.extension.compose.widget.SimpleBackSearchToolbar
import androidx.core.extension.compose.widget.SimpleBackToolbar
import androidx.core.extension.compose.widget.SimpleButton
import androidx.core.extension.compose.widget.SimpleCard
import androidx.core.extension.compose.widget.SimpleCardBox
import androidx.core.extension.compose.widget.SimpleCardColumn
import androidx.core.extension.compose.widget.SimpleCardRow
import androidx.core.extension.compose.widget.SimpleChip
import androidx.core.extension.compose.widget.SimpleDialog
import androidx.core.extension.compose.widget.SimpleFilterChip
import androidx.core.extension.compose.widget.SimpleFlowRowChip
import androidx.core.extension.compose.widget.SimpleFlowRowHorizontalScrollChip
import androidx.core.extension.compose.widget.SimpleFlowRowHorizontalScrollFilterChip
import androidx.core.extension.compose.widget.SimpleFlowRowVerticalScrollFilterChip
import androidx.core.extension.compose.widget.SimpleIconButton
import androidx.core.extension.compose.widget.SimpleInfiniteBox
import androidx.core.extension.compose.widget.SimpleInfiniteList
import androidx.core.extension.compose.widget.SimpleInfiniteVerticalGrid
import androidx.core.extension.compose.widget.SimpleInfiniteVerticalStaggeredGrid
import androidx.core.extension.compose.widget.SimpleInput
import androidx.core.extension.compose.widget.SimpleInterceptWebView
import androidx.core.extension.compose.widget.SimpleRadioButton
import androidx.core.extension.compose.widget.SimpleStatusBox
import androidx.core.extension.compose.widget.SimpleTabLayout
import androidx.core.extension.compose.widget.SimpleTextButton
import androidx.core.extension.compose.widget.SingleInputDialog
import androidx.core.extension.compose.widget.WeightButton
import androidx.core.extension.http.onSuccessNotNull
import androidx.core.extension.os.mainHandler
import androidx.core.os.postDelayed
import kotlinx.coroutines.delay
import java.util.UUID

@Composable
private fun SampleLazyColumn(item: List<String>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(item) {
            Text(
                text = it,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(5.dp)
            )
        }
    }
}

private val randomString get() = UUID.randomUUID().toString()
private const val tagString = "tag"

private val tabList
    get() = arrayListOf<String>().apply {
        for (i in 0..3) {
            add("tab:$i")
        }
    }.toMutableStateList()

private val mutableStateList
    get() = arrayListOf<String>().apply {
        for (i in 0..50) {
            add(randomString)
        }
    }.toMutableStateList()

private val tagList
    get() = arrayListOf<String>().apply {
        for (i in 0..20) {
            add("Index:$i")
        }
    }.toMutableStateList()

@Preview(showBackground = true)
@Composable
fun SampleComposePreview(type: SampleTypes = SampleTypes.Column) {
    when (type) {
        SampleTypes.Entry -> TODO()
        SampleTypes.Box -> PreviewBox()
        SampleTypes.Button -> PreviewButton()
        SampleTypes.Card -> PreviewCard()
        SampleTypes.Chip -> PreviewChip()
        SampleTypes.Column -> PreviewColumn()
        SampleTypes.Dialog -> PreviewDialog()
        SampleTypes.Grid -> PreviewGrid()
        SampleTypes.Input -> PreviewInput()
        SampleTypes.List -> PreviewList()
        SampleTypes.Row -> PreviewRow()
        SampleTypes.StaggeredGrid -> PreviewStaggeredGrid()
        SampleTypes.Tab -> PreviewTab()
        SampleTypes.Toolbar -> PreviewToolbar()
        SampleTypes.Text -> PreviewText()
        SampleTypes.Web -> PreviewWeb()
        SampleTypes.Status -> PreviewStatus()
    }
}

class SimplePreviewStatusBoxViewModel : SimpleComposeViewModel<String>(
    initializeUrl = "https://www.baidu.com"
) {
    override suspend fun requestHttp(refresh: Boolean, url: String): Pair<String?, String> {
        delay(1000)
//        throw NullPointerException()
        return null to ""
    }
}

@Composable
fun PreviewStatus(
    viewModel: SimplePreviewStatusBoxViewModel = viewModel()
) {
    Column {
        SimpleStatusBox(viewModel = viewModel) {
            it.onSuccessNotNull { value ->
                Text(text = value, modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}

@Composable
fun PreviewWeb() {
    SimpleInterceptWebView(
        url = "https://www.baidu.com",
        onClick = {}
    )
}

@Composable
fun PreviewText() {
    LabelText(
        prefix = "前缀",
        label = tagList,
        onTextValue = { it },
        onClick = {}
    )
}

@Composable
fun PreviewToolbar() {
    Column {
        SimpleBackToolbar(stringResource(R.string.app_name), back = {})
        SimpleBackSearchToolbar(stringResource(R.string.app_name), back = {}, search = {})
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PreviewTab() {
    SimpleTabLayout(
        items = tabList,
        title = { it },
        content = { _, value ->
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(value)
            }
        }
    )
}

@Composable
fun PreviewStaggeredGrid() {
    val refresh = remember { boolStateOf(false) }
    val items = remember { mutableStateList }
    SimpleInfiniteVerticalStaggeredGrid(
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
    ) { Text(it) }
}

@Composable
fun PreviewRow() {
    Row {
        WeightButton("one")
        WeightButton("two")
        WeightButton("three")
    }
}

@Composable
fun PreviewList() {
    val refresh = remember { boolStateOf(false) }
    val items = remember { mutableStateList }
    SimpleInfiniteList(
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
    ) { Text(it) }
}

@Composable
fun PreviewInput() {
    val value = remember { textFieldValueStateOf(TextFieldValue()) }
    SimpleInput(
        label = stringResource(R.string.app_name),
        value = value.value,
        onValueChange = { value.value = it },
        keyboardAction = {
        }
    )
}

@Composable
fun PreviewGrid() {
    val refresh = remember { boolStateOf(false) }
    val items = remember { mutableStateList }
    SimpleInfiniteVerticalGrid(
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
    ) { Text(it) }
}

@Composable
fun PreviewDialog() {
    Column {
        val dialog = rememberDialog {
            SimpleDialog("Test", ok = hide)
        }
        val inputDialog = rememberDialog {
            SingleInputDialog {
            }
        }
        val copyOrDownload = rememberDialog {
            CopyOrDownloadDialog(
                "Test",
                onClickCopy = {},
                onClickDownload = {}
            )
        }
        SimpleButton("ShowDialog") {
            dialog.show()
        }
        SimpleButton("ShowInputDialog") {
            inputDialog.show()
        }
        SimpleButton("ShowCopyOrDownloadDialog") {
            copyOrDownload.show()
        }
    }
}

@Composable
fun PreviewChip() {
    Column {
        SimpleFilterChip(tagString, selected = true, onText = { it })
        SimpleFlowRowHorizontalScrollFilterChip(
            item = tagList,
            onText = { it },
            onSelect = { it.contains("2") }
        )
        Box(modifier = Modifier.height(100.dp)) {
            SimpleFlowRowVerticalScrollFilterChip(
                item = tagList,
                onText = { it },
                size = FlowChipSize.Small,
                onSelect = { it.contains("2") }
            )
        }
        SimpleChip(tagString, onText = { it })
        SimpleFlowRowHorizontalScrollChip(
            item = tagList,
            onText = { it },
        )
        SimpleFlowRowChip(tagList, onText = { it })
    }
}

@Composable
fun PreviewColumn() {
    Box {
        ColumnSmallText(
            item = tagList
        )
    }
}

@Composable
fun PreviewCard() {
    Column {
        Text("Row Card")
        SimpleCardRow(modifier = Modifier.padding(5.dp)) {
            Text("Card1")
            Text("Card2")
        }
        Text("Column Card")
        SimpleCardColumn(modifier = Modifier.padding(5.dp)) {
            Text("Card1")
            Text("Card2")
        }
        Text("Box Card")
        SimpleCardBox(modifier = Modifier.padding(5.dp)) {
            Text("Card1")
            Text("Card2")
        }
        Text("Card")
        SimpleCard {
            Text("Card1")
        }
    }
}

@Composable
fun PreviewButton() {
    Column {

        val select1 = "select1"
        val select2 = "select2"
        val select3 = "select3"
        val currentSelect = remember { stringStateOf(select1) }
        Text("RadioButton")
        Row {
            SimpleRadioButton(
                text = select1,
                selected = currentSelect.value,
                modifier = Modifier.weight(1f)
            ) { currentSelect.value = it }
            SimpleRadioButton(
                text = select2,
                selected = currentSelect.value,
                modifier = Modifier.weight(1f)
            ) { currentSelect.value = it }
            SimpleRadioButton(
                text = select3,
                selected = currentSelect.value,
                modifier = Modifier.weight(1f)
            ) { currentSelect.value = it }
        }

        Text("IconButton")
        SimpleIconButton(imageVector = Icons.Default.Add, tint = Color.Black)

        SimpleButton("Button", onClick = {})
        SimpleTextButton("TextButton", onClick = {})
    }
}

@Composable
fun PreviewBox() {
    val refresh = remember { boolStateOf(false) }
    val items = remember { mutableStateList }
    SimpleInfiniteBox(
        refreshing = refresh.value,
        contentAlignment = Alignment.Center,
        onRefresh = {
            refresh.value = true
            mainHandler.postDelayed(1000) {
                refresh.value = false
                items.add(0, randomString)
            }
        }
    ) { SampleLazyColumn(items) }
}