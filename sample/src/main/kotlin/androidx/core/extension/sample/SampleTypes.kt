package androidx.core.extension.sample

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.extension.compose.material.SimpleBackToolbar
import androidx.core.extension.compose.material.SimpleCard
import androidx.core.extension.compose.material.SimpleText
import androidx.core.extension.compose.material.SimpleToolbar
import androidx.core.extension.compose.navigation.NavRouter
import androidx.core.extension.compose.navigation.navigate
import androidx.core.extension.compose.widget.SimpleVerticalGrid
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController

enum class SampleTypes {

    Entry {
        @Composable
        override fun Screen(controller: NavHostController, stack: NavBackStackEntry) {
            Column {
                SimpleToolbar(stringResource(R.string.app_name))
                SimpleVerticalGrid(
                    items = SampleTypes.entries.filter { it != Entry },
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxHeight()
                ) { _, item ->
                    SimpleCard {
                        SimpleText(
                            text = item.name,
                            modifier = Modifier
                                .clickable { controller.navigate(item.router) }
                                .padding(6.dp)
                        )
                    }
                }
            }
        }
    },

    Toolbar {
        @Composable
        override fun ScreenContent() {
            PreviewToolbar()
        }
    },
    Toolbar3 {
        @Composable
        override fun ScreenContent() {
            PreviewToolbar3()
        }
    },

    Button {
        @Composable
        override fun ScreenContent() {
            PreviewButton()
        }
    },
    Button3 {
        @Composable
        override fun ScreenContent() {
            PreviewButton3()
        }
    },

    Input {
        @Composable
        override fun ScreenContent() {
            PreviewInput()
        }
    },
    Input3 {
        @Composable
        override fun ScreenContent() {
            PreviewInput3()
        }
    },

    Dialog {
        @Composable
        override fun ScreenContent() {
            PreviewDialog()
        }
    },
    Dialog3 {
        @Composable
        override fun ScreenContent() {
            PreviewDialog3()
        }
    },

    Row {
        @Composable
        override fun ScreenContent() {
            PreviewRow()
        }
    },
    Row3 {
        @Composable
        override fun ScreenContent() {
            PreviewRow3()
        }
    },

    Column {
        @Composable
        override fun ScreenContent() {
            PreviewColumn()
        }
    },
    Column3 {
        @Composable
        override fun ScreenContent() {
            PreviewColumn3()
        }
    },

    Text {
        @Composable
        override fun ScreenContent() {
            PreviewText()
        }
    },
    Text3 {
        @Composable
        override fun ScreenContent() {
            PreviewText3()
        }
    },

    Card {
        @Composable
        override fun ScreenContent() {
            PreviewCard()
        }
    },
    Card3 {
        @Composable
        override fun ScreenContent() {
            PreviewCard3()
        }
    },

    Tab {
        @Composable
        override fun ScreenContent() {
            PreviewTab()
        }
    },
    Tab3 {
        @Composable
        override fun ScreenContent() {
            PreviewTab3()
        }
    },

    Box {
        @Composable
        override fun ScreenContent() {
            PreviewBox()
        }
    },
    Box3 {
        @Composable
        override fun ScreenContent() {
            PreviewBox3()
        }
    },

    Chip {
        @Composable
        override fun ScreenContent() {
            PreviewChip()
        }
    },
    Chip3 {
        @Composable
        override fun ScreenContent() {
            PreviewChip3()
        }
    },

    List {
        @Composable
        override fun ScreenContent() {
            PreviewList()
        }
    },
    List3 {
        @Composable
        override fun ScreenContent() {
            PreviewList3()
        }
    },

    Grid {
        @Composable
        override fun ScreenContent() {
            PreviewGrid()
        }
    },
    Grid3 {
        @Composable
        override fun ScreenContent() {
            PreviewGrid3()
        }
    },

    StaggeredGrid {
        @Composable
        override fun ScreenContent() {
            PreviewStaggeredGrid()
        }
    },
    StaggeredGrid3 {
        @Composable
        override fun ScreenContent() {
            PreviewStaggeredGrid3()
        }
    },

    SingleBox {
        @Composable
        override fun ScreenContent() {
            PreviewSingleBox()
        }
    },
    SingleBox3 {
        @Composable
        override fun ScreenContent() {
            PreviewSingleBox3()
        }
    },

    MultiList {
        @Composable
        override fun ScreenContent() {
            PreviewMultiList()
        }
    },
    MultiList3 {
        @Composable
        override fun ScreenContent() {
            PreviewMultiList3()
        }
    },

    MultiGrid {
        @Composable
        override fun ScreenContent() {
            PreviewMultiGrid()
        }
    },
    MultiGrid3 {
        @Composable
        override fun ScreenContent() {
            PreviewMultiGrid3()
        }
    },

    MultiStaggeredGrid {
        @Composable
        override fun ScreenContent() {
            PreviewMultiStaggeredGrid()
        }
    },
    MultiStaggeredGrid3 {
        @Composable
        override fun ScreenContent() {
            PreviewMultiStaggeredGrid3()
        }
    },

    Web {
        @Composable
        override fun ScreenContent() {
            PreviewWeb()
        }
    },
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