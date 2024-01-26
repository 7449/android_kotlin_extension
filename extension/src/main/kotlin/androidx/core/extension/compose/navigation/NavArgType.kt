@file:Suppress("UNCHECKED_CAST")

package androidx.core.extension.compose.navigation

import android.os.Bundle
import androidx.navigation.NavType
import com.google.gson.Gson

internal class NavArgType<E : NavRouterArgs>(private val clazz: Class<E>) : NavType<E>(true) {

    companion object {
        val gson = Gson()
    }

    override val name: String
        get() = "NavArgType"

    override fun get(bundle: Bundle, key: String): E? {
        return bundle.getSerializable(key) as? E
    }

    override fun parseValue(value: String): E {
        return gson.fromJson(value, clazz)
    }

    override fun put(bundle: Bundle, key: String, value: E) {
        bundle.putSerializable(key, value)
    }

}