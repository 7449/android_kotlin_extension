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
import androidx.core.extension.compose.widget.LocalNavController
import androidx.core.extension.compose.widget.SimpleVerticalGrid
import androidx.core.extension.util.allSealedSubclasses
import androidx.navigation.NavBackStackEntry
import kotlinx.serialization.Serializable

sealed class SampleType {

    val name get() = this::class.simpleName.orEmpty()

    @Composable
    open fun Screen(stack: NavBackStackEntry) {
        val navHostController = LocalNavController.current
        Column {
            SimpleBackToolbar(name) { navHostController.popBackStack() }
            Box(modifier = Modifier.padding(5.dp)) {
                ScreenContent()
            }
        }
    }

    @Composable
    open fun ScreenContent() {
    }

}

@Serializable
data object Entry : SampleType() {
    @Composable
    override fun Screen(stack: NavBackStackEntry) {
        val navHostController = LocalNavController.current
        Column {
            SimpleToolbar(stringResource(R.string.app_name))
            SimpleVerticalGrid(
                items = SampleType::class.allSealedSubclasses
                    .filter { it.objectInstance != Entry }
                    .mapNotNull { it.objectInstance },
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxHeight()
            ) { _, item ->
                SimpleCard {
                    SimpleText(
                        text = item.name,
                        modifier = Modifier
                            .clickable { navHostController.navigate(item) }
                            .padding(6.dp)
                    )
                }
            }
        }
    }
}

@Serializable
data object Toolbar : SampleType() {
    @Composable
    override fun ScreenContent() {
        PreviewToolbar()
    }
}

@Serializable
data object Toolbar3 : SampleType() {
    @Composable
    override fun ScreenContent() {
        PreviewToolbar3()
    }
}

@Serializable
data object Button : SampleType() {
    @Composable
    override fun ScreenContent() {
        PreviewButton()
    }
}

@Serializable
data object Button3 : SampleType() {
    @Composable
    override fun ScreenContent() {
        PreviewButton3()
    }
}

@Serializable
data object Input : SampleType() {
    @Composable
    override fun ScreenContent() {
        PreviewInput()
    }
}

@Serializable
data object Input3 : SampleType() {
    @Composable
    override fun ScreenContent() {
        PreviewInput3()
    }
}

@Serializable
data object Dialog : SampleType() {
    @Composable
    override fun ScreenContent() {
        PreviewDialog()
    }
}

@Serializable
data object Dialog3 : SampleType() {
    @Composable
    override fun ScreenContent() {
        PreviewDialog3()
    }
}

@Serializable
data object Row : SampleType() {
    @Composable
    override fun ScreenContent() {
        PreviewRow()
    }
}

@Serializable
data object Row3 : SampleType() {
    @Composable
    override fun ScreenContent() {
        PreviewRow3()
    }
}

@Serializable
data object Column : SampleType() {
    @Composable
    override fun ScreenContent() {
        PreviewColumn()
    }
}

@Serializable
data object Column3 : SampleType() {
    @Composable
    override fun ScreenContent() {
        PreviewColumn3()
    }
}

@Serializable
data object Text : SampleType() {
    @Composable
    override fun ScreenContent() {
        PreviewText()
    }
}

@Serializable
data object Text3 : SampleType() {
    @Composable
    override fun ScreenContent() {
        PreviewText3()
    }

}

@Serializable
data object Card : SampleType() {
    @Composable
    override fun ScreenContent() {
        PreviewCard()
    }
}

@Serializable
data object Card3 : SampleType() {
    @Composable
    override fun ScreenContent() {
        PreviewCard3()
    }
}

@Serializable
data object Tab : SampleType() {
    @Composable
    override fun ScreenContent() {
        PreviewTab()
    }
}

@Serializable
data object Tab3 : SampleType() {
    @Composable
    override fun ScreenContent() {
        PreviewTab3()
    }
}

@Serializable
data object Box : SampleType() {
    @Composable
    override fun ScreenContent() {
        PreviewBox()
    }
}

@Serializable
data object Box3 : SampleType() {
    @Composable
    override fun ScreenContent() {
        PreviewBox3()
    }
}

@Serializable
data object Chip : SampleType() {
    @Composable
    override fun ScreenContent() {
        PreviewChip()
    }
}

@Serializable
data object Chip3 : SampleType() {
    @Composable
    override fun ScreenContent() {
        PreviewChip3()
    }
}

@Serializable
data object LazyList : SampleType() {
    @Composable
    override fun ScreenContent() {
        PreviewList()
    }
}

@Serializable
data object LazyList3 : SampleType() {
    @Composable
    override fun ScreenContent() {
        PreviewList3()
    }
}

@Serializable
data object Grid : SampleType() {
    @Composable
    override fun ScreenContent() {
        PreviewGrid()
    }
}

@Serializable
data object Grid3 : SampleType() {
    @Composable
    override fun ScreenContent() {
        PreviewGrid3()
    }
}

@Serializable
data object StaggeredGrid : SampleType() {
    @Composable
    override fun ScreenContent() {
        PreviewStaggeredGrid()
    }
}

@Serializable
data object StaggeredGrid3 : SampleType() {
    @Composable
    override fun ScreenContent() {
        PreviewStaggeredGrid3()
    }
}

@Serializable
data object SingleBox : SampleType() {
    @Composable
    override fun ScreenContent() {
        PreviewSingleBox()
    }
}

@Serializable
data object SingleBox3 : SampleType() {
    @Composable
    override fun ScreenContent() {
        PreviewSingleBox3()
    }
}

@Serializable
data object MultiList : SampleType() {
    @Composable
    override fun ScreenContent() {
        PreviewMultiList()
    }
}

@Serializable
data object MultiList3 : SampleType() {
    @Composable
    override fun ScreenContent() {
        PreviewMultiList3()
    }
}

@Serializable
data object MultiGrid : SampleType() {
    @Composable
    override fun ScreenContent() {
        PreviewMultiGrid()
    }
}

@Serializable
data object MultiGrid3 : SampleType() {
    @Composable
    override fun ScreenContent() {
        PreviewMultiGrid3()
    }
}


@Serializable
data object MultiStaggeredGrid : SampleType() {
    @Composable
    override fun ScreenContent() {
        PreviewMultiStaggeredGrid()
    }
}

@Serializable
data object MultiStaggeredGrid3 : SampleType() {
    @Composable
    override fun ScreenContent() {
        PreviewMultiStaggeredGrid3()
    }
}