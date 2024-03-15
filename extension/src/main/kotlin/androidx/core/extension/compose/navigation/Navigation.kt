package androidx.core.extension.compose.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.compose.composable

inline fun <reified T : NavRouterArgs> NavBackStackEntry.args(): T {
    return requireNotNull(argsOrNull())
}

inline fun <reified T : NavRouterArgs> NavBackStackEntry.argsOrNull(): T? {
    val bundle = arguments ?: return null
    bundle.keySet().forEach {
        @Suppress("DEPRECATION")
        val any = bundle.get(it)
        if (T::class.java == any?.javaClass) {
            return any as? T
        }
    }
    return null
}

fun NavGraphBuilder.composable(
    router: NavRouter,
    deepLinks: List<NavDeepLink> = emptyList(),
    enterTransition: (@JvmSuppressWildcards AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? = null,
    exitTransition: (@JvmSuppressWildcards AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? = null,
    popEnterTransition: (@JvmSuppressWildcards AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? = enterTransition,
    popExitTransition: (@JvmSuppressWildcards AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? = exitTransition,
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit,
) {
    val pair = router.composable
    composable(
        route = pair.first,
        arguments = pair.second,
        deepLinks = deepLinks,
        enterTransition = enterTransition,
        exitTransition = exitTransition,
        popEnterTransition = popEnterTransition,
        popExitTransition = popExitTransition,
        content = content
    )
}

fun NavHostController.navigate(
    route: NavRouter,
    vararg args: NavRouterArgs,
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null,
) {
    val router = route.navigate(args.toList())
    navigate(router, navOptions, navigatorExtras)
}