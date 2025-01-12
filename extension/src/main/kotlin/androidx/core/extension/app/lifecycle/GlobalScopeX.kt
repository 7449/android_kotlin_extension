package androidx.core.extension.app.lifecycle

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

fun globalScope(
    failure: suspend (Throwable) -> Unit = {},
    action: suspend () -> Unit,
) {
    @Suppress("OPT_IN_USAGE")
    GlobalScope.launch {
        try {
            action.invoke()
        } catch (ex: Exception) {
            ex.printStackTrace()
            failure.invoke(ex)
        }
    }
}