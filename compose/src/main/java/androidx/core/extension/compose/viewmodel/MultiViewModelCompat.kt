package androidx.core.extension.compose.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.core.extension.compose.textFieldValueStateOf

abstract class MultiViewModelCompat<T>(
    private val initializeUrl: String = "",
    initializeRefresh: Boolean = initializeUrl.isNotBlank(),
    input: String = "",
) : SimpleMultiViewModel<T>(initializeUrl = initializeUrl, initializeRefresh = initializeRefresh) {
    private val _input = textFieldValueStateOf(TextFieldValue(input))
    val input get() = _input.value

    private val _staging = mutableStateOf<Any?>(null)
    val staging get() = _staging.value

    fun <T> stagingTOrNull(): T? {
        @Suppress("UNCHECKED_CAST")
        return _staging.value as? T
    }

    fun <T> stagingT(): T {
        return checkNotNull(stagingTOrNull())
    }

    fun staging(staging: Any) {
        _staging.value = staging
    }

    fun onInputValueChange(value: TextFieldValue) {
        _input.value = value
    }

    open fun searchUrl(key: String): String = key
}