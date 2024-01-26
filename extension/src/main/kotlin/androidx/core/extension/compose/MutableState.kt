package androidx.core.extension.compose

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.core.extension.http.DataWrapper

fun <T> dataStateOf(
    value: DataWrapper<T> = DataWrapper.Normal(),
): MutableState<DataWrapper<T>> {
    return mutableStateOf(value)
}

fun stringStateOf(
    value: String = "",
): MutableState<String> {
    return mutableStateOf(value)
}

fun boolStateOf(
    value: Boolean = false,
): MutableState<Boolean> {
    return mutableStateOf(value)
}

fun textFieldValueStateOf(
    value: TextFieldValue = TextFieldValue(),
): MutableState<TextFieldValue> {
    return mutableStateOf(value)
}

