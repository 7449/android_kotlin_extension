package androidx.core.extension.compose.navigation

import android.net.Uri
import androidx.navigation.NamedNavArgument
import androidx.navigation.navArgument

/**
 * ```
 * empty args
 * object SimpleRouter : NavRouter("router")
 * ```
 * ```
 * single args
 * object SimpleRouter : NavRouter("router") {
 *     class SimpleRouterArgs(val key1: String = "") : NavRouterArgs
 * }
 * ```
 * ```
 * multi args
 * object SimpleRouter : NavRouter("router") {
 *     class SimpleRouterArgs1(val title: String = "") : NavRouterArgs
 *     class SimpleRouterArgs2(val url: String = "") : NavRouterArgs
 * }
 * ```
 */
open class NavRouter(private val target: String) {

    companion object {
        private const val NavKey = "nav_router_args_%s"
    }

    internal fun composable(): Pair<String, List<NamedNavArgument>> {
        val argsClass = argsClass()
        val navArgs = argsClass.mapIndexed { index: Int, clazz: Class<NavRouterArgs> ->
            navArgument(NavKey.format(index)) { type = NavArgType(clazz) }
        }
        val routerSuffix = List(argsClass.size) { index: Int -> "{${NavKey.format(index)}}" }
            .joinToString("/", "/")
        return target.plus(routerSuffix) to navArgs
    }

    internal fun navigate(arg: List<NavRouterArgs> = listOf()): String {
        val navArgs = arg.joinToString("/", "/") { Uri.encode(NavArgType.gson.toJson(it)) }
        return target.plus(navArgs)
    }

    private fun argsClass(): List<Class<NavRouterArgs>> {
        return javaClass.declaredClasses.filterIsInstance<Class<NavRouterArgs>>()
    }

    operator fun invoke(): String {
        return composable().first
    }

}