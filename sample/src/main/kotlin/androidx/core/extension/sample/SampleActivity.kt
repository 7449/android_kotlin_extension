package androidx.core.extension.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.core.extension.compose.material.setMaterialThemeContent
import androidx.core.extension.compose.screen
import androidx.core.extension.compose.widget.LocalNavController
import androidx.core.extension.compose.widget.SimpleNavHost
import androidx.core.extension.compose.widget.composable
import androidx.core.extension.util.allSealedSubclasses
import androidx.navigation.compose.rememberNavController

class SampleActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setMaterialThemeContent {
            val navController = rememberNavController()
            CompositionLocalProvider(LocalNavController provides navController) {
                SimpleNavHost(
                    startDestination = Entry,
                    navHostController = navController,
                    modifier = Modifier.screen()
                ) { controller ->
                    SampleType::class.allSealedSubclasses
                        .mapNotNull { it.objectInstance }
                        .forEach { obj -> composable(obj) { obj.Screen(it) } }
                }
            }
        }
    }
}