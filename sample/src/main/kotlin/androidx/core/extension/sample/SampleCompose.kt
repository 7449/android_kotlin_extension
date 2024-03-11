package androidx.core.extension.sample

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.extension.compose.widget.SimpleBackToolbar
import java.util.UUID

@Composable
fun SampleContent(title: String, back: () -> Unit, content: @Composable () -> Unit) {
    Column {
        SimpleBackToolbar(title, back)
        content()
    }
}

@Composable
fun SampleLazyColumn(item: List<String>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) { items(item) { Text(it) } }
}


fun RandomString() = UUID.randomUUID().toString()

fun DefaultItems() = arrayListOf<String>().apply {
    for (i in 0..50) {
        add(RandomString())
    }
}