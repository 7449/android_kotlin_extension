package androidx.core.extension.compose.viewmodel

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@Composable
inline fun <reified VM : ViewModel> viewModel(
    vararg args: Any,
    key: String? = null,
): VM = viewModel<VM>(key = key) {
    val constructors = VM::class.java.declaredConstructors
        .filter { it.parameterTypes.size == args.size }
    val constructor = constructors.find { constructor ->
        constructor.parameterTypes.withIndex().all { (index, type) ->
            type.isAssignableFrom(args[index].javaClass)
        }
    }
        ?: throw IllegalArgumentException("No suitable constructor found for ViewModel of type ${VM::class.java.name}")
    constructor.newInstance(*args.map { it }.toTypedArray()) as VM
}

const val DEFAULT_REQUEST_END_MARK = "request.end"

typealias ComposeScope<T> = suspend (CoroutineScope) -> T
typealias ComposeError = suspend (Exception) -> Unit
typealias ComposeCancel = suspend () -> Unit

fun ViewModel.composeLaunch(
    error: ComposeError? = null,
    cancel: ComposeCancel? = null,
    scope: ComposeScope<Unit>,
): Job = viewModelScope.launch {
    try {
        scope.invoke(this)
    } catch (e: Exception) {
        when (e) {
            is CancellationException -> cancel?.invoke()
            else -> error?.invoke(e)
        }
    }
}