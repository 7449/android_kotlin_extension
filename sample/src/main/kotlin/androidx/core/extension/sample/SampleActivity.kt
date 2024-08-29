package androidx.core.extension.sample

import android.os.Bundle
import androidx.core.extension.compose.activity.ComposeActivity
import androidx.core.extension.compose.material.setMaterialThemeContent
import androidx.core.extension.compose.navigation.composable
import androidx.core.extension.compose.widget.SimpleNavHost

class SampleActivity : ComposeActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setMaterialThemeContent {
            SimpleNavHost(SampleTypes.Entry.router) { controller ->
                SampleTypes.entries.forEach { value ->
                    composable(value.router) { value.Screen(controller, it) }
                }
            }
        }
    }
}