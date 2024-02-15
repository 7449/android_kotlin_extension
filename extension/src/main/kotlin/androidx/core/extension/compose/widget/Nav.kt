package androidx.core.extension.compose.widget

import androidx.compose.runtime.Composable
import androidx.core.extension.compose.navigation.NavRouter
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@Composable
fun SimpleNavHost(
    startDestination: NavRouter,
    navHostController: NavHostController = rememberNavController(),
    builder: NavGraphBuilder.(NavHostController) -> Unit,
) {
    NavHost(
        navController = navHostController,
        startDestination = startDestination(),
        builder = { builder(navHostController) }
    )
}