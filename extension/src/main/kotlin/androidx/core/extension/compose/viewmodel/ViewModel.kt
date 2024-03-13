package androidx.core.extension.compose.viewmodel

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

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
    } ?: throw IllegalArgumentException("No suitable constructor found for ViewModel of type ${VM::class.java.name}")
    constructor.newInstance(*args.map { it }.toTypedArray()) as VM
}