package androidx.core.extension.sample

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.extension.compose.stringStateOf
import androidx.core.extension.compose.viewmodel.SimpleMultiViewModel
import androidx.core.extension.compose.viewmodel.SimpleSingleViewModel
import androidx.core.extension.compose.widget.SimpleInterceptWebView
import kotlinx.coroutines.delay
import java.util.UUID
import kotlin.random.Random

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
fun SampleComposePreview(type: SampleType = Toolbar) {
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
