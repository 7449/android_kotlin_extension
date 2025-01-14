package androidx.core.extension.compose

import android.view.Gravity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * ```
 * val dialog = rememberDialog {
 *    //DialogCompose
 * }
 * dialog.hide()
 * dialog.show()
 * ```
 */

typealias DialogCallback = () -> Unit

sealed interface DialogPosition {
    object Bottom : DialogPosition
    object Center : DialogPosition
}

@Composable
fun rememberDialog(
    showCallback: DialogCallback = {},
    hideCallback: DialogCallback = {},
    position: DialogPosition = DialogPosition.Center,
    properties: DialogProperties = DialogProperties(),
    content: @Composable DialogHolder<Any>.() -> Unit
) = rememberArgsDialog<Any>(
    showCallback = showCallback,
    hideCallback = hideCallback,
    position = position,
    properties = properties,
    content = content
)

@Composable
fun <T> rememberArgsDialog(
    showCallback: DialogCallback = {},
    hideCallback: DialogCallback = {},
    position: DialogPosition = DialogPosition.Center,
    properties: DialogProperties = DialogProperties(),
    content: @Composable DialogHolder<T>.() -> Unit
): DialogHolder<T> {
    val currentShowCallback = rememberUpdatedState(showCallback)
    val currentHideCallback = rememberUpdatedState(hideCallback)
    val dialogState = remember { mutableStateOf(false) }
    val dialogInputState = remember { textFieldValueStateOf() }
    val dialogArgs = remember { mutableStateOf<T?>(null) }
    val holder = remember {
        DialogHolder(
            currentShowCallback,
            currentHideCallback,
            dialogState,
            dialogInputState,
            dialogArgs
        )
    }
    if (dialogState.value) {
        Dialog(onDismissRequest = holder.hide, properties = properties) {
            when (position) {
                DialogPosition.Bottom -> {
                    val dialogWindowProvider = LocalView.current.parent as? DialogWindowProvider
                    dialogWindowProvider?.window?.setGravity(Gravity.BOTTOM)
                    dialogWindowProvider?.window?.attributes =
                        dialogWindowProvider.window.attributes.apply { width = -1 }
                }

                DialogPosition.Center -> {
                    //default
                }
            }
            content(holder)
        }
    }
    return holder
}

class DialogHolder<T> internal constructor(
    private val showCallback: State<DialogCallback>,
    private val hideCallback: State<DialogCallback>,
    private val state: MutableState<Boolean>,
    private val inputState: MutableState<TextFieldValue>,
    private val argsState: MutableState<T?>,
) {

    val show: DialogCallback = { show() }
    val hide: DialogCallback = { hide() }
    val input get() = inputState
    val isShow get() = state.value
    val argsOrNull get() = argsState.value
    val args get() = checkNotNull(argsOrNull)

    fun show(args: T? = null, scope: CoroutineScope? = null) {
        if (state.value) return
        if (scope != null) {
            scope.launch {
                argsState.value = args
            }.invokeOnCompletion {
                state.value = true
                showCallback.value()
            }
        } else {
            argsState.value = args
            state.value = true
            showCallback.value()
        }
    }

    fun hide(scope: CoroutineScope? = null) {
        if (!state.value) return
        if (scope != null) {
            scope.launch {
                argsState.value = null
            }.invokeOnCompletion {
                state.value = false
                hideCallback.value()
            }
        } else {
            argsState.value = null
            state.value = false
            hideCallback.value()
        }
    }

}