package androidx.core.extension.compose.viewmodel

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
inline fun <reified VM : ViewModel> viewModel(
    vararg args: Any,
    key: String? = null,
): VM = viewModel<VM>(key = key) {
    val keys = args.map { it::class.java }.toTypedArray()
    val values = args.map { it }.toTypedArray()
    VM::class.java.getDeclaredConstructor(*keys).newInstance(*values)
}