package androidx.core.extension.compose

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.core.extension.wrapper.http.DataWrapper
import kotlinx.coroutines.flow.MutableStateFlow

fun <T> dataWrapperStateOf(
    value: DataWrapper<T> = DataWrapper.Normal,
): MutableState<DataWrapper<T>> {
    return mutableStateOf(value)
}

fun <T> dataWrapperStateFlow(
    value: DataWrapper<T> = DataWrapper.Normal,
): MutableStateFlow<DataWrapper<T>> {
    return MutableStateFlow(value)
}

fun <T> mutableStateListFlow(
    item: MutableList<T>,
): MutableStateFlow<MutableList<T>> {
    return MutableStateFlow(item)
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

fun String.textField(): TextFieldValue {
    return TextFieldValue(this, TextRange(length))
}

