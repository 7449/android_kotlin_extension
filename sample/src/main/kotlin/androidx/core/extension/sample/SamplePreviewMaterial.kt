package androidx.core.extension.sample

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.core.extension.compose.boolStateOf
import androidx.core.extension.compose.material.CopyOrDownloadDialog
import androidx.core.extension.compose.material.SimpleDialog
import androidx.core.extension.compose.material.SingleInputDialog
import androidx.core.extension.compose.material.WeightButton
import androidx.core.extension.compose.rememberDialog
import androidx.core.extension.compose.stringStateOf
import androidx.core.extension.compose.textFieldValueStateOf
import androidx.core.extension.os.mainHandler
import androidx.core.os.postDelayed

@Composable
fun PreviewToolbar() {
    Column {
        androidx.core.extension.compose.material.SimpleBackToolbar(
            stringResource(R.string.app_name),
            back = {}
        )
        androidx.core.extension.compose.material.SimpleBackSearchToolbar(
            stringResource(R.string.app_name),
            back = {},
            search = {}
        )
    }
}

@Composable
fun PreviewButton() {
    Column {
        val currentSelect = remember { stringStateOf(radioBtnWidget.first()) }
        Row {
            radioBtnWidget.forEach { it ->
                androidx.core.extension.compose.material.SimpleRadioButton(
                    text = it,
                    selected = currentSelect.value,
                    modifier = Modifier.weight(1f)
                ) { currentSelect.value = it }
            }
        }
        androidx.core.extension.compose.material.SimpleIconButton(
            imageVector = Icons.Default.Add,
            tint = Color.Black
        )
        androidx.core.extension.compose.material.SimpleButton("Button", onClick = {})
        androidx.core.extension.compose.material.SimpleTextButton("TextButton", onClick = {})
    }
}

@Composable
fun PreviewInput() {
    val value = remember { textFieldValueStateOf(TextFieldValue()) }
    androidx.core.extension.compose.material.SimpleInput(
        label = stringResource(R.string.app_name),
        value = value.value,
        onValueChange = { value.value = it },
        keyboardAction = {
        }
    )
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
        androidx.core.extension.compose.material.SimpleButton("ShowDialog") {
            dialog.show()
        }
        androidx.core.extension.compose.material.SimpleButton("ShowInputDialog") {
            inputDialog.show()
        }
        androidx.core.extension.compose.material.SimpleButton("ShowCopyOrDownloadDialog") {
            copyOrDownload.show()
        }
    }
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
fun PreviewColumn() {
    androidx.core.extension.compose.material.ColumnSmallText(item = tagList)
}

@Composable
fun PreviewText() {
    androidx.core.extension.compose.material.LabelText(
        prefix = "前缀",
        label = tagList,
        onTextValue = { it },
        onClick = {}
    )
}

@Composable
fun PreviewCard() {
    Column {
        androidx.core.extension.compose.material.SimpleCardRow(modifier = Modifier.padding(5.dp)) {
            androidx.core.extension.compose.material.SimpleText("Card1")
            androidx.core.extension.compose.material.SimpleText("Card2")
        }
        androidx.core.extension.compose.material.SimpleCardColumn(modifier = Modifier.padding(5.dp)) {
            androidx.core.extension.compose.material.SimpleText("Card1")
            androidx.core.extension.compose.material.SimpleText("Card2")
        }
        androidx.core.extension.compose.material3.SimpleCardBox(modifier = Modifier.padding(5.dp)) {
            androidx.core.extension.compose.material.SimpleText("Card1")
            androidx.core.extension.compose.material.SimpleText("Card2")
        }
        androidx.core.extension.compose.material.SimpleCard {
            androidx.core.extension.compose.material.SimpleText("Card1")
        }
    }
}

@Composable
fun PreviewTab() {
    androidx.core.extension.compose.material.SimpleText("Not Supported")
//    androidx.core.extension.compose.material.SimpleTabLayout(
//        items = tabList,
//        title = { it },
//        content = { _, value ->
//            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//                androidx.core.extension.compose.material.SimpleText(value)
//            }
//        }
//    )
}

@Composable
fun PreviewBox() {
    val refresh = remember { boolStateOf(false) }
    val items = remember { mutableStateList }
    androidx.core.extension.compose.material.SimpleInfiniteBox(
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
            androidx.core.extension.compose.material.SimpleText(
                text = it,
                modifier = Modifier.padding(5.dp)
            )
        }
    }
}

@Composable
fun PreviewChip() {
}

@Composable
fun PreviewList() {
}

@Composable
fun PreviewGrid() {
}

@Composable
fun PreviewStaggeredGrid() {
}

@Composable
fun PreviewSingleBox() {
}

@Composable
fun PreviewMultiList() {
}

@Composable
fun PreviewMultiGrid() {
}

@Composable
fun PreviewMultiStaggeredGrid() {
}