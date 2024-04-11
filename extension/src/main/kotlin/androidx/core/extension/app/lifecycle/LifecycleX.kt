package androidx.core.extension.app.lifecycle

import androidx.core.app.ComponentActivity
import androidx.core.extension.app.showProgressDialog
import androidx.core.extension.content.showToastShort
import androidx.core.extension.os.postMainThread
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

fun ComponentActivity.simpleLaunch(
    isDelayed: Boolean = false,
    transparent: Boolean = true,
    action: suspend () -> Unit,
) {
    postMainThread {
        showProgressDialog(isDelayed, transparent) { dialog ->
            launch({
                dialog?.dismiss()
                postMainThread { showToastShort(it.message.orEmpty()) }
            }) {
                action.invoke()
                dialog?.dismiss()
            }
        }
    }
}

fun LifecycleOwner.launch(
    failure: suspend (Throwable) -> Unit = {},
    action: suspend () -> Unit,
) {
    lifecycleScope.launch {
        try {
            action.invoke()
        } catch (ex: Exception) {
            ex.printStackTrace()
            if (lifecycle.currentState != Lifecycle.State.DESTROYED) {
                failure.invoke(ex)
            }
        }
    }
}