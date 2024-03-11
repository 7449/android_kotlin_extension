package androidx.core.extension.sample

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.extension.compose.boolStateOf
import androidx.core.extension.compose.navigation.NavRouter
import androidx.core.extension.compose.navigation.navigate
import androidx.core.extension.compose.noRippleClick
import androidx.core.extension.compose.stringStateOf
import androidx.core.extension.compose.widget.SimpleCard
import androidx.core.extension.compose.widget.SimpleCardBox
import androidx.core.extension.compose.widget.SimpleCardColumn
import androidx.core.extension.compose.widget.SimpleIconButton
import androidx.core.extension.compose.widget.SimpleInfiniteBox
import androidx.core.extension.compose.widget.SimpleInfiniteVerticalGrid
import androidx.core.extension.compose.widget.SimpleRadioButton
import androidx.core.extension.compose.widget.SimpleToolbar
import androidx.core.extension.os.mainHandler
import androidx.core.os.postDelayed
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController

enum class SampleTypes {

    Entry {
        @Composable
        override fun Screen(controller: NavHostController, stack: NavBackStackEntry) {
            Column {
                SimpleToolbar("ComposeSample")
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxHeight()
                ) {
                    items(SampleTypes.entries.filter { it != Entry }) {
                        SimpleCard {
                            Text(
                                it.name,
                                modifier = Modifier.noRippleClick { controller.navigate(it.router) },
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                }
            }
        }
    },

    Box {
        @Composable
        override fun ScreenContent() {
            val refresh = remember { boolStateOf(false) }
            val items = remember { DefaultItems().toMutableStateList() }
            SimpleInfiniteBox(
                refreshing = refresh.value,
                contentAlignment = Alignment.Center,
                onRefresh = {
                    refresh.value = true
                    mainHandler.postDelayed(1000) {
                        refresh.value = false
                        items.add(0, RandomString())
                    }
                }
            ) { SampleLazyColumn(items) }
        }
    },
    Button {
        @Composable
        override fun ScreenContent() {
            Column(modifier = Modifier.padding(5.dp)) {

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

            }
        }
    },
    Card {
        @Composable
        override fun ScreenContent() {
            LazyColumn(modifier = Modifier.padding(5.dp)) {
                item {
                    Text("CardColumn")
                    SimpleCardColumn {
                        Text("Card1")
                        Text("Card2")
                    }
                }
                item {
                    Text("CardBox")
                    SimpleCardBox {
                        Text("Card1")
                        Text("Card2")
                    }
                }
                item {
                    Text("Card")
                    SimpleCard {
                        Text("Card1")
                        Text("Card2")
                    }
                }
            }
        }
    },
    Grid {
        @Composable
        override fun ScreenContent() {
            val refresh = remember { boolStateOf(false) }
            val items = remember { DefaultItems().toMutableStateList() }
            SimpleInfiniteVerticalGrid(
                items,
                refreshing = refresh.value,
                onRefresh = {
                    refresh.value = true
                    mainHandler.postDelayed(1000) {
                        refresh.value = false
                        items.add(0, RandomString())
                    }
                },
                onLoadMore = {
                    refresh.value = true
                    mainHandler.postDelayed(1000) {
                        refresh.value = false
                        items.add(RandomString())
                    }
                }
            ) { Text(it) }
        }
    },
    Input {
        @Composable
        override fun ScreenContent() {
        }
    },
    List {
        @Composable
        override fun ScreenContent() {
        }
    },
    Nav {
        @Composable
        override fun ScreenContent() {
        }
    },
    Tab {
        @Composable
        override fun ScreenContent() {
        }
    },
    Tag {
        @Composable
        override fun ScreenContent() {
        }
    },
    Toolbar {
        @Composable
        override fun ScreenContent() {
        }
    },

    ;

    open val router: NavRouter = object : NavRouter(name) {}

    @Composable
    open fun Screen(controller: NavHostController, stack: NavBackStackEntry) {
        SampleContent(name, { controller.popBackStack() }) { ScreenContent() }
    }

    @Composable
    open fun ScreenContent() {
    }

}