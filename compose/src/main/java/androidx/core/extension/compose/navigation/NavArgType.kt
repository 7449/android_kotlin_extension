@file:Suppress("UNCHECKED_CAST")

package androidx.core.extension.compose.navigation

import android.os.Bundle
import androidx.navigation.NavType

internal class NavArgType<E : NavRouterArgs>(private val clazz: Class<E>) : NavType<E>(true) {

    override val name: String
        get() = "NavArgType"

    override fun get(bundle: Bundle, key: String): E? {
        @Suppress("DEPRECATION")
        return bundle.getSerializable(key) as? E
    }

    override fun parseValue(value: String): E {
        return navArgGson.fromJson(value, clazz)
    }

    override fun put(bundle: Bundle, key: String, value: E) {
        bundle.putSerializable(key, value)
    }

}