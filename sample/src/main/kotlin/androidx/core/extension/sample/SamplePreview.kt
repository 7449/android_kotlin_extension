package androidx.core.extension.sample

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.extension.compose.widget.SimpleInterceptWebView
import java.util.UUID

const val REQUEST_URL = "https://www.baidu.com"

val radioBtnWidget = arrayListOf("select1", "select2", "select3")

val randomString get() = UUID.randomUUID().toString()

const val tagString = "tag"

val tabList
    get() = arrayListOf<String>().apply {
        for (i in 0..5) {
            add("tab:$i")
        }
    }.toMutableStateList()

val mutableStateList
    get() = arrayListOf<String>().apply {
        for (i in 0..50) {
            add(randomString)
        }
    }.toMutableStateList()

val tagList
    get() = arrayListOf<String>().apply {
        for (i in 0..20) {
            add("Index:$i")
        }
    }.toMutableStateList()

@Preview(showBackground = true)
@Composable
fun SampleComposePreview(type: SampleTypes = SampleTypes.Toolbar) {
    type.ScreenContent()
}

@Composable
fun PreviewWeb() {
    SimpleInterceptWebView(REQUEST_URL)
}

@Composable
fun SampleLazyColumn(item: List<String>, content: @Composable (String) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) { items(item) { content(it) } }
}
