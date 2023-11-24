package androidx.core.extension.os

fun stringMapOf(vararg pairs: Pair<String, String>): StringMap {
    val map = hashMapOf<String, String>()
    pairs.forEach { map[it.first] = it.second }
    return map
}

fun anyMapOf(vararg pairs: Pair<String, Any>): AnyMap {
    val map = hashMapOf<String, Any>()
    pairs.forEach { map[it.first] = it.second }
    return map
}