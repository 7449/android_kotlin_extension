package androidx.core.extension.util

inline fun <reified T> genericType() = T::class.java

inline fun <reified T> lazyValue(crossinline action: () -> T): Lazy<T> {
    return object : Lazy<T> {

        private var cached: T? = null

        override val value: T
            get() {
                val values = cached
                return if (values == null) {
                    val invoke = action.invoke()
                    cached = invoke
                    invoke
                } else {
                    values
                }
            }

        override fun isInitialized(): Boolean = cached != null
    }
}