package androidx.core.extension.sample

import android.os.Bundle
import androidx.activity.addCallback
import androidx.core.extension.compose.activity.ComposeActivity
import androidx.core.extension.compose.activity.setThemeContent
import androidx.core.extension.compose.navigation.composable
import androidx.core.extension.compose.widget.SimpleNavHost

class SampleActivity : ComposeActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onBackPressedDispatcher.addCallback(this,true){

        }
        setThemeContent {
            SimpleNavHost(SampleTypes.Entry.router) { controller ->
                SampleTypes.entries.forEach { value ->
                    composable(value.router) { value.Screen(controller, it) }
                }
            }
        }
    }
}