package androidx.core.extension.compose.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.runtime.Composable
import androidx.core.extension.text.logE
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.compose.composable

inline fun <reified T : NavRouterArgs> NavBackStackEntry.args(): T {
    return requireNotNull(argsOrNull())
}

inline fun <reified T : NavRouterArgs> NavBackStackEntry.argsInstance(): T {
    return argsOrNull() ?: T::class.java.getConstructor().newInstance()
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
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit,
) {
    val pair = router.composable()
    logE("Router->:${pair.first} Arg->:${pair.second}")
    composable(pair.first, pair.second, content = content)
}

fun NavHostController.navigate(
    route: NavRouter,
    vararg args: NavRouterArgs,
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null,
) {
    val router = route.navigate(args.toList())
    logE("Navigation->:$router")
    navigate(router, navOptions, navigatorExtras)
}