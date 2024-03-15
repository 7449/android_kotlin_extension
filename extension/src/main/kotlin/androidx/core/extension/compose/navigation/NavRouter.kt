package androidx.core.extension.compose.navigation

import android.net.Uri
import androidx.core.extension.util.navArgGson
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
        private const val NAV_KEY = "nav_router_args_%s"
    }

    private val argsClass = javaClass.declaredClasses.filterIsInstance<Class<NavRouterArgs>>()

    private val navArgs = argsClass.mapIndexed { index: Int, clazz: Class<NavRouterArgs> ->
        navArgument(NAV_KEY.format(index)) { type = NavArgType(clazz) }
    }

    private val routerSuffix = List(argsClass.size) { index: Int -> "{${NAV_KEY.format(index)}}" }
        .joinToString("/", "/")

    internal val composable = target.plus(routerSuffix) to navArgs

    internal fun navigate(arg: List<NavRouterArgs> = listOf()): String {
        val navArgs = arg.joinToString("/", "/") { Uri.encode(navArgGson.toJson(it)) }
        return target.plus(navArgs)
    }

    operator fun invoke(): String {
        return composable.first
    }

}