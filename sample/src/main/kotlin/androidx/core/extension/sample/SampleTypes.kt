package androidx.core.extension.sample

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.core.extension.compose.navigation.NavRouter
import androidx.core.extension.compose.navigation.navigate
import androidx.core.extension.compose.noRippleClick
import androidx.core.extension.compose.widget.SimpleCard
import androidx.core.extension.compose.widget.SimpleToolbar
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
        }
    },
    Button {
        @Composable
        override fun ScreenContent() {
        }
    },
    Card {
        @Composable
        override fun ScreenContent() {
        }
    },
    Dialog {
        @Composable
        override fun ScreenContent() {
        }
    },
    Grid {
        @Composable
        override fun ScreenContent() {
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