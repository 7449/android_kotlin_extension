@file:Suppress("ObjectPropertyName")

package androidx.core.extension.util

import com.google.gson.Gson

internal val defaultGson = Gson()

internal var _navArgGson: Gson? = null

internal val navArgGson get() = _navArgGson ?: defaultGson

fun registerNavArgGson(gson: Gson) {
    _navArgGson = gson
}

