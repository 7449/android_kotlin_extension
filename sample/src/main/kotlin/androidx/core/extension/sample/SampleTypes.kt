package androidx.core.extension.sample

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.extension.compose.navigation.NavRouter
import androidx.core.extension.compose.navigation.navigate
import androidx.core.extension.compose.widget.SimpleBackToolbar
import androidx.core.extension.compose.widget.SimpleCard
import androidx.core.extension.compose.widget.SimpleToolbar
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController

enum class SampleTypes {

    Entry {
        @Composable
        override fun Screen(controller: NavHostController, stack: NavBackStackEntry) {
            Column {
                SimpleToolbar(stringResource(R.string.app_name))
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxHeight()
                ) {
                    items(SampleTypes.entries.filter { it != Entry }) {
                        SimpleCard {
                            Text(
                                text = it.name,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .clickable { controller.navigate(it.router) }
                                    .padding(6.dp),
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
            PreviewBox()
        }
    },
    Button {
        @Composable
        override fun ScreenContent() {
            PreviewButton()
        }
    },
    Card {
        @Composable
        override fun ScreenContent() {
            PreviewCard()
        }
    },
    Chip {
        @Composable
        override fun ScreenContent() {
            PreviewChip()
        }
    },
    Column {
        @Composable
        override fun ScreenContent() {
            PreviewColumn()
        }
    },
    Dialog {
        @Composable
        override fun ScreenContent() {
            PreviewDialog()
        }
    },
    Grid {
        @Composable
        override fun ScreenContent() {
            PreviewGrid()
        }
    },
    Input {
        @Composable
        override fun ScreenContent() {
            PreviewInput()
        }
    },
    List {
        @Composable
        override fun ScreenContent() {
            PreviewList()
        }
    },
    Row {
        @Composable
        override fun ScreenContent() {
            PreviewRow()
        }
    },
    StaggeredGrid {
        @Composable
        override fun ScreenContent() {
            PreviewStaggeredGrid()
        }
    },
    Tab {
        @Composable
        override fun ScreenContent() {
            PreviewTab()
        }
    },
    Toolbar {
        @Composable
        override fun ScreenContent() {
            PreviewToolbar()
        }
    },
    Text {
        @Composable
        override fun ScreenContent() {
            PreviewText()
        }
    },
    Web {
        @Composable
        override fun ScreenContent() {
            PreviewWeb()
        }
    }

    ;

    val router: NavRouter = object : NavRouter(name) {}

    @Composable
    open fun Screen(controller: NavHostController, stack: NavBackStackEntry) {
        Column {
            SimpleBackToolbar(name) { controller.popBackStack() }
            Box(modifier = Modifier.padding(5.dp)) {
                ScreenContent()
            }
        }
    }

    @Composable
    open fun ScreenContent() {
    }

}