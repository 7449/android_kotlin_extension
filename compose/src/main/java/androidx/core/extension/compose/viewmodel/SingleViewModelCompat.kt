package androidx.core.extension.compose.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import androidx.core.extension.compose.textFieldValueStateOf

abstract class SingleViewModelCompat<T>(
    private val initializeUrl: String = "",
    initializeRefresh: Boolean = initializeUrl.isNotBlank(),
    input: String = "",
) : SimpleSingleViewModel<T>(initializeUrl = initializeUrl, initializeRefresh = initializeRefresh) {
    private val _input = textFieldValueStateOf(TextFieldValue(input))
    val input get() = _input.value

    fun onInputValueChange(value: TextFieldValue) {
        _input.value = value
    }

    open fun searchUrl(key: String): String = key
}