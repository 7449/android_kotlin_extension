package androidx.core.extension.compose.widget

import android.annotation.SuppressLint
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun SimpleInterceptWebView(url: String, onClick: (String) -> Unit) {
    val intercept = remember { mutableStateListOf<String>() }
    val webViewMutableState = remember { mutableStateOf<WebView?>(null) }
    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(intercept) {
                SimpleCardRow(
                    padding = 4.dp,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(5.dp)
                ) {
                    Text(it, modifier = Modifier.weight(1f))
                    SimpleTextButton("保存") { onClick(it) }
                }
            }
        }
        AndroidView(
            modifier = Modifier.weight(2f),
            onRelease = { it.destroy() },
            factory = { it ->
                webViewMutableState.value ?: WebView(it).apply {
                    layoutParams = FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.MATCH_PARENT
                    )
                    settings.javaScriptEnabled = true
                    webViewClient = object : WebViewClient() {
                        override fun onPageFinished(view: WebView, url: String) {
                            intercept.add(url)
                        }
                    }
                }.also { webViewMutableState.value = it }
            }
        )
    }
    webViewMutableState.value?.let {
        LaunchedEffect(webViewMutableState) {
            it.loadUrl(url)
        }
    }
}