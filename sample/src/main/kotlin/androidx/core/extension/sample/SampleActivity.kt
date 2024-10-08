package androidx.core.extension.sample

import android.os.Bundle
import androidx.core.extension.compose.activity.ComposeActivity
import androidx.core.extension.compose.material.setMaterialThemeContent
import androidx.core.extension.compose.widget.SimpleNavHost
import androidx.core.extension.compose.widget.composable
import androidx.core.extension.util.allSealedSubclasses

class SampleActivity : ComposeActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setMaterialThemeContent {
            SimpleNavHost(Entry) { controller ->
                SampleType::class.allSealedSubclasses
                    .mapNotNull { it.objectInstance }
                    .forEach { obj -> composable(obj) { obj.Screen(controller, it) } }
            }
        }
    }
}