package androidx.core.extension.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.text.input.TextFieldValue

/**
 * ```
 * val dialog = rememberDialog {
 *    //DialogCompose
 * }
 * dialog.hide()
 * dialog.show()
 * ```
 */
@Composable
fun rememberDialog(
    showCallback: () -> Unit = {},
    hideCallback: () -> Unit = {},
    dialog: @Composable DialogHolder.() -> Unit,
): DialogHolder {

    val currentShowCallback = rememberUpdatedState(showCallback)
    val currentHideCallback = rememberUpdatedState(hideCallback)

    val dialogState = remember { boolStateOf(false) }
    val dialogInputState = remember { textFieldValueStateOf() }

    val holder = remember {
        DialogHolder(
            currentShowCallback,
            currentHideCallback,
            dialogState,
            dialogInputState
        )
    }

    if (dialogState.value) {
        dialog(holder)
    }

    return holder

}

class DialogHolder internal constructor(
    private val showCallback: State<() -> Unit>,
    private val hideCallback: State<() -> Unit>,
    private val state: MutableState<Boolean>,
    private val input: MutableState<TextFieldValue>,
) {

    val textFieldState get() = input
    val show: () -> Unit = { show() }
    val hide: () -> Unit = { hide() }

    fun show() {
        state.value = true
        showCallback.value()
    }

    fun hide() {
        state.value = false
        hideCallback.value()
    }

}