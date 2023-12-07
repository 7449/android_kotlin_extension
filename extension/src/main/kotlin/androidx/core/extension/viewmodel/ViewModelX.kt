package androidx.core.extension.viewmodel

import androidx.core.extension.text.logE
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

typealias Scope<T> = suspend (CoroutineScope) -> T
typealias Error = suspend (Exception) -> Unit
typealias Cancel = suspend () -> Unit

fun ViewModel.launch(
    error: Error? = null,
    cancel: Cancel? = null,
    scope: Scope<Unit>,
): Job {
    return viewModelScope.launch {
        try {
            scope.invoke(this)
        } catch (e: Exception) {
            when (e) {
                is CancellationException -> cancel?.invoke()
                else -> error?.invoke(e)
            }
        }
    }
}

suspend inline fun <reified T> ViewModel.async(crossinline scope: Scope<T>): T {
    return withContext(viewModelScope.coroutineContext) { scope.invoke(this) }
}

fun globalScope(
    failure: suspend (Throwable) -> Unit = {},
    action: suspend () -> Unit,
) {
    @Suppress("OPT_IN_USAGE")
    GlobalScope.launch {
        try {
            action.invoke()
        } catch (ex: Exception) {
            logE("failure:${ex.message}")
            ex.printStackTrace()
            failure.invoke(ex)
        }
    }
}

fun blockScope(
    failure: suspend (Throwable) -> Unit = {},
    action: suspend () -> Unit,
) {
    runBlocking {
        try {
            action.invoke()
        } catch (ex: Exception) {
            logE("failure:${ex.message}")
            ex.printStackTrace()
            failure.invoke(ex)
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
            logE("failure:${ex.message}")
            if (lifecycle.currentState != Lifecycle.State.DESTROYED) {
                failure.invoke(ex)
            }
        }
    }
}

fun Job.cancelCompat() {
    if (job.isActive && !job.isCompleted && !job.isCancelled) {
        job.cancel()
    }
}