package androidx.core.extension.compatible.databinding.adapter

import androidx.core.extension.compatible.databinding.DATABIND_ING_HIDE_MESSAGE

@Deprecated(DATABIND_ING_HIDE_MESSAGE)
class DataBindHolder<T>(
    @Deprecated(DATABIND_ING_HIDE_MESSAGE)
    val variables: Array<Variable>,
    @Deprecated(DATABIND_ING_HIDE_MESSAGE)
    val clazz: Class<T>,
    @Deprecated(DATABIND_ING_HIDE_MESSAGE)
    val factory: DataBindAdapter<T>,
)