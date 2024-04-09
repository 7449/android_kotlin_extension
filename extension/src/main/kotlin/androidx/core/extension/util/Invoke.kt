package androidx.core.extension.util

import kotlin.reflect.KClass

val <T : Any> KClass<T>.allSealedSubclasses: List<KClass<out T>>
    get() = run {
        val kClasses = arrayListOf<KClass<out T>>()
        sealedSubclasses.forEach {
            if (it.isSealed) kClasses.addAll(it.allSealedSubclasses)
            else kClasses.add(it)
        }
        return kClasses
    }