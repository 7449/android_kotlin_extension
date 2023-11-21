package androidx.core.extension.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

typealias Scope<T> = suspend (CoroutineScope) -> T
typealias Error = suspend (Exception) -> Unit
typealias Cancel = suspend () -> Unit

fun ViewModel.launch(scope: Scope<Unit>, error: Error? = null, cancel: Cancel? = null): Job {
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

fun Job.cancelCompat() {
    if (job.isActive && !job.isCompleted && !job.isCancelled) {
        job.cancel()
    }
}